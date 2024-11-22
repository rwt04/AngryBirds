package com.badlogic.angrybirds;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class DrawBody {
    private World world;
    private Box2DDebugRenderer b2dr;

    public DrawBody(World world, Box2DDebugRenderer b2dr) {
        this.world = world;
        this.b2dr = b2dr;
    }

    public Body drawCircle(GameObject go, float scaleX, float scaleY) {
        // body definition
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(go.getX() / AngryBirds.PPM, go.getY() / AngryBirds.PPM);

        // shape
        CircleShape shape = new CircleShape();
        float radius = ((go.getTexture().getWidth() * scaleX) + (go.getTexture().getHeight() * scaleY)) / 4 / AngryBirds.PPM;
        shape.setRadius(radius);

        // fixture definition
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 5.0f;
        fdef.friction = 0.5f;
        fdef.restitution = 0.1f;

        // create body
        Body body = world.createBody(bdef);
        body.createFixture(fdef);

        Sprite sprite = new Sprite(go.getTexture());
        sprite.setSize(go.getTexture().getWidth() * scaleX, go.getTexture().getHeight() * scaleY);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        body.setUserData(sprite);

        shape.dispose();
        return body;
    }

    public Body drawCatapult(GameObject go , float scaleX, float scaleY){
        // body definition
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody; // Assuming catapult is static
        bdef.position.set(go.getX() / AngryBirds.PPM, (go.getY() + (go.getTexture().getHeight()*scaleY) / 2) / AngryBirds.PPM);
        System.out.println("Catapult position: " + go.getX() / AngryBirds.PPM + " " + go.getY() / AngryBirds.PPM);

        // shape definition
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((go.getTexture().getWidth() * scaleX) / 2 / AngryBirds.PPM, (go.getTexture().getHeight() * scaleY) / 2 / AngryBirds.PPM);

        // fixture definition
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 5.0f;
        fdef.friction = 0.5f;
        fdef.restitution = 0.1f;

        // create body
        Body body = world.createBody(bdef);
        body.createFixture(fdef);

        Sprite sprite = new Sprite(go.getTexture());
        sprite.setSize(go.getTexture().getWidth() * scaleX, go.getTexture().getHeight() * scaleY);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        body.setUserData(sprite);

        shape.dispose();
        return body;
    }

    public Body drawBlock(Block block, float scaleX, float scaleY) {
        // body definition
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        float adjustedY = block.getY() / AngryBirds.PPM;

        // Adjust y-coordinate if the block is vertical
        if (block.getOrientation() == 90f) {
            adjustedY += (block.getTexture().getWidth() * scaleX) / 2 / AngryBirds.PPM;
        }

        bdef.position.set(block.getX() / AngryBirds.PPM, adjustedY);
        bdef.angle = MathUtils.degreesToRadians * block.getOrientation(); // Set the angle for the body

        // shape definition
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((block.getTexture().getWidth() * scaleX) / 2 / AngryBirds.PPM, (block.getTexture().getHeight() * scaleY) / 2 / AngryBirds.PPM);

        // fixture definition
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 5.0f;
        fdef.friction = 0.5f;
        fdef.restitution = 0.1f;

        // create body
        Body body = world.createBody(bdef);
        body.createFixture(fdef);

        Sprite sprite = new Sprite(block.getTexture());
        sprite.setSize(block.getTexture().getWidth() * scaleX, block.getTexture().getHeight() * scaleY);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setRotation(block.getOrientation()); // Set rotation for the sprite
        body.setUserData(sprite);

        shape.dispose();
        return body;
    }

//    public void renderCircle(OrthographicCamera gamecam){
//        gamecam.position.set(mybox.getPosition().x, mybox.getPosition().y, 0);
//        gamecam.update();
//        b2dr.render(world, gamecam.combined);
//    }
}
