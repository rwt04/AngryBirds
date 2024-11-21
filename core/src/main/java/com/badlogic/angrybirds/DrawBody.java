package com.badlogic.angrybirds;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class DrawBody {
    private World world;
    private Box2DDebugRenderer b2dr;
    private Body mybox;
    Sprite sprite = new Sprite();

    public DrawBody(World world, Box2DDebugRenderer b2dr, Body mybox) {
        this.world = world;
        this.b2dr = b2dr;
        this.mybox = mybox;
    }

    public Body drawCircle(GameObject go){
        // body definition
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(32,40);

        // create body
        mybox = world.createBody(bdef);
        mybox.createFixture(fdef);

        // shape
        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(0,0));
        shape.setRadius(1);

        // fixture definition
        fdef.shape=shape;
        fdef.density = 5.0f;
        fdef.friction = 0.5f;
        fdef.restitution = 0.1f;

        sprite = new Sprite(go.getTexture());
        sprite.setSize(.5f,1);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        mybox.setUserData(sprite);

        return mybox;
    }

    public Body drawRectangle(GameObject go){
        // body definition
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(32,40);

        // shape definition
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1);

        // fixture definition
        FixtureDef fdef = new FixtureDef();
        fdef.shape=shape;
        fdef.density = 5.0f;
        fdef.friction = 0.5f;
        fdef.restitution = 0.1f;

        // create body
        sprite = new Sprite(go.getTexture());
        sprite.setSize(2,2);
        mybox.setUserData(sprite);

        return mybox;
    }

    public void renderCircle(OrthographicCamera gamecam){
        gamecam.position.set(mybox.getPosition().x, mybox.getPosition().y, 0);
        gamecam.update();
        b2dr.render(world, gamecam.combined);
    }
}
