package com.badlogic.angrybirds.Pigs;

import com.badlogic.angrybirds.AngryBirds;
import com.badlogic.angrybirds.GameObject;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Pig extends GameObject {

    private int HP;
    private int maxHP;
    Body body;
    Sprite sprite;

    public Pig(Texture texture, float x, float y, int maxHP) {
        super(texture, x, y);
        this.maxHP = maxHP;
        this.HP = maxHP;
    }

    public void createBody(World world, float scaleX, float scaleY) {
        // body definition
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(getX() / AngryBirds.PPM, getY() / AngryBirds.PPM);

        // shape
        CircleShape shape = new CircleShape();
        float radius = ((getTexture().getWidth() * scaleX) + (getTexture().getHeight() * scaleY)) / 4 / AngryBirds.PPM;
        shape.setRadius(radius);

        // fixture definition
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 5.0f;
        fdef.friction = 0.5f;
        fdef.restitution = 0.1f;

        // create body
        body = world.createBody(bdef);
        body.createFixture(fdef);

        sprite = new Sprite(getTexture());
        sprite.setSize(getTexture().getWidth() * scaleX, getTexture().getHeight() * scaleY);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        body.setUserData(this); // Set UserData to the sprite for rendering

        shape.dispose();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void destroyBody(World world){
        world.destroyBody(body);
    }

    public Body getBody(){
        return body;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void reduceHp(int damage) {
        HP -= damage;
    }

    public boolean isDestroyed() {
        return HP <= 0;
    }

    public int getMaxHP() {
        return maxHP;
    }
}
