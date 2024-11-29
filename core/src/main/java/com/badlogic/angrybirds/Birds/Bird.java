package com.badlogic.angrybirds.Birds;

import com.badlogic.angrybirds.AngryBirds;
import com.badlogic.angrybirds.GameObject;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

// todo: add more specific bird attributes
//  hitpoints, damage, etc.

public class Bird extends GameObject {

    Body body;
    private int damage;
    private boolean launched = false;
    private boolean isCurrentBird = false;
    private boolean isBirdOnCatapult = false;
    private Sprite sprite;

    public Bird(Texture texture, float x, float y) {
        super(texture, x, y);
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

    public boolean isLaunched() {
        return launched;
    }

    public void setLaunched(boolean launched) {
        this.launched = launched;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isCurrentBird() {
        return isCurrentBird;
    }

    public void setCurrentBird(boolean currentBird) {
        isCurrentBird = currentBird;
    }

    public boolean isBirdOnCatapult() {
        return isBirdOnCatapult;
    }

    public void setBirdOnCatapult(boolean birdOnCatapult) {
        isBirdOnCatapult = birdOnCatapult;
    }
}
