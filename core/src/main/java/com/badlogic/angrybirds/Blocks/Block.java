package com.badlogic.angrybirds.Blocks;

import com.badlogic.angrybirds.AngryBirds;
import com.badlogic.angrybirds.GameObject;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;

public class Block extends GameObject {
    private float orientation;
    private Body body;
    private float density;
    protected boolean IsSquare;
    private int HP;
    private int maxHP;
    Sprite sprite;

    public Block(Texture texture, float x, float y, float orientation, int maxHP, float density) {
        super(texture, x, y);
        this.orientation = orientation;
        this.maxHP = maxHP;
        this.HP = maxHP;
        this.density = density;
    }

    public float getOrientation() {
        return orientation;
    }

    public void createBody(World world, float scaleX, float scaleY) {
        if (IsSquare){
            scaleX = scaleY = 0.5f;
        }
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
        fdef.density = density;
        fdef.friction = 0.5f;
        fdef.restitution = 0.1f;

        // create body
        body = world.createBody(bdef);
        body.createFixture(fdef);

        sprite = new Sprite(getTexture());
        sprite.setSize(getTexture().getWidth() * scaleX, getTexture().getHeight() * scaleY);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setRotation(getOrientation()); // Set rotation for the sprite
        body.setUserData(this);

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

    public void setSquare(boolean square){
        IsSquare = square;
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

    public int getDamage() {
        return 5;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public float getDensity() {
        return density;
    }
}
