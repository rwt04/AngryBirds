package com.badlogic.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public class Catapult extends GameObject {

    Body body;

    public Catapult(float x, float y) {
        super(new Texture(Gdx.files.internal("gameObjects/catapult.png")), x, y);
    }

    public void createBody(World world, float scaleX, float scaleY){
        // body definition
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody; // Assuming catapult is static
        bdef.position.set(getX() / AngryBirds.PPM, (getY() + (getTexture().getHeight()*scaleY) / 2) / AngryBirds.PPM);
        System.out.println("Catapult position: " + getX() / AngryBirds.PPM + " " + getY() / AngryBirds.PPM);

        // shape definition
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getTexture().getWidth() * scaleX) / 2 / AngryBirds.PPM, (getTexture().getHeight() * scaleY) / 2 / AngryBirds.PPM);

        // fixture definition
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 5.0f;
        fdef.friction = 0.5f;
        fdef.restitution = 0.1f;

        // create body
        body = world.createBody(bdef);
        body.createFixture(fdef);

        Sprite sprite = new Sprite(getTexture());
        sprite.setSize(getTexture().getWidth() * scaleX, getTexture().getHeight() * scaleY);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        body.setUserData(sprite);

        shape.dispose();
    }

    public void destroyBody(World world){
        world.destroyBody(body);
    }

    public Body getBody(){
        return body;
    }
}
