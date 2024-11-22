package com.badlogic.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;

// todo: add body properties of different blocks
//  density, friction, restitution
//  similarly with pigs and birds
//  add more specific block attributes
//  like health, damage, etc.

public class Block extends GameObject {
    private float orientation;
    private Body body;
    private float density;

    public Block(Texture texture, float x, float y, float orientation) {
        super(texture, x, y);
        this.orientation = orientation;
        this.density = density;
    }

    public float getOrientation() {
        return orientation;
    }

    public void createBody(World world, float scaleX, float scaleY) {
        // body definition
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        float adjustedY = getY() / AngryBirds.PPM;

        // Adjust y-coordinate if the block is vertical
        if (orientation == 90f) {
            adjustedY += (getTexture().getWidth() * scaleX) / 2 / AngryBirds.PPM;
        }

        bdef.position.set(getX() / AngryBirds.PPM, adjustedY);
        bdef.angle = MathUtils.degreesToRadians * getOrientation(); // Set the angle for the body

        // shape definition
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getTexture().getWidth() * scaleX) / 2 / AngryBirds.PPM, (getTexture().getHeight() * scaleY) / 2 / AngryBirds.PPM);

        // fixture definition
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 3f;
        fdef.friction = 0.5f;
        fdef.restitution = 0.1f;

        // create body
        body = world.createBody(bdef);
        body.createFixture(fdef);

        Sprite sprite = new Sprite(getTexture());
        sprite.setSize(getTexture().getWidth() * scaleX, getTexture().getHeight() * scaleY);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setRotation(getOrientation()); // Set rotation for the sprite
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
