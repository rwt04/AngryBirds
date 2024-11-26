package com.badlogic.angrybirds;

import com.badlogic.angrybirds.Birds.Bird;
import com.badlogic.angrybirds.Blocks.Block;
import com.badlogic.angrybirds.Pigs.Pig;
import com.badlogic.angrybirds.Scenes.Hud;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class CollisionListener implements ContactListener {
    private Hud hud;
    private Array<GameObject> objectsToDestroy;

    public CollisionListener(Hud hud) {
        this.hud = hud;
        this.objectsToDestroy = new Array<>();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA == null || fixtureB == null) {
            return;
        }

        GameObject gameObjectA = (GameObject) fixtureA.getBody().getUserData();
        GameObject gameObjectB = (GameObject) fixtureB.getBody().getUserData();

        if (gameObjectA == null || gameObjectB == null) {
            return;
        }

        if (gameObjectA instanceof Bird && gameObjectB instanceof Block) {
            handleCollision((Bird) gameObjectA, (Block) gameObjectB);
        } else if (gameObjectA instanceof Block && gameObjectB instanceof Bird) {
            handleCollision((Bird) gameObjectB, (Block) gameObjectA);
        } else if (gameObjectA instanceof Bird && gameObjectB instanceof Pig) {
            handleCollision((Bird) gameObjectA, (Pig) gameObjectB);
        } else if (gameObjectA instanceof Pig && gameObjectB instanceof Bird) {
            handleCollision((Bird) gameObjectB, (Pig) gameObjectA);
        } else if (gameObjectA instanceof Block && gameObjectB instanceof Pig) {
            handleCollision((Block) gameObjectA, (Pig) gameObjectB);
        } else if (gameObjectA instanceof Pig && gameObjectB instanceof Block) {
            handleCollision((Block) gameObjectB, (Pig) gameObjectA);
        } else if (gameObjectA instanceof Block && gameObjectB instanceof Block) {
            handleCollision((Block) gameObjectA, (Block) gameObjectB);
        }
    }

    private void handleCollision(Bird bird, Block block) {
        int damage = bird.getDamage();
        block.reduceHp(damage);
        if (block.isDestroyed()) {
            hud.updateScore(block.getMaxHP()*30); // Adjust the score value as needed
            addObjectsToDestroy(block);
        }
    }

    private void handleCollision(Bird bird, Pig pig) {
        int damage = bird.getDamage();
        pig.reduceHp(damage);
        if (pig.isDestroyed()) {
            hud.updateScore(pig.getMaxHP()*50); // Adjust the score value as needed
            addObjectsToDestroy(pig);
        }
    }

    private void handleCollision(Block block, Pig pig) {
        int damage = block.getDamage();
        pig.reduceHp(damage);
        if (pig.isDestroyed()) {
            hud.updateScore(pig.getMaxHP()*50); // Adjust the score value as needed
            addObjectsToDestroy(pig);
        }
    }

    private void handleCollision(Block blockA, Block blockB) {
        int damage = blockA.getDamage();
        blockB.reduceHp(damage);
        if (blockB.isDestroyed()) {
            hud.updateScore(blockB.getMaxHP()*30); // Adjust the score value as needed
            addObjectsToDestroy(blockB);
        }
    }

    private void addObjectsToDestroy(GameObject go) {
        objectsToDestroy.add(go);
    }

    public Array<GameObject> getObjectsToDestroy() {
        return objectsToDestroy;
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
