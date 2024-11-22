package com.badlogic.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

// todo: add more specific bird attributes
//  hitpoints, damage, etc.

public class Bird extends GameObject {

    Body body;
    private boolean launched = false;

    public Bird(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    public void createBody(World world,float scaleX, float scaleY) {
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

    public boolean isLaunched() {
        return launched;
    }

    public void setLaunched(boolean launched) {
        this.launched = launched;
    }
}
