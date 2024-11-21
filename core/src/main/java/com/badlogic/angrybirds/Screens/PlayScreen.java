package com.badlogic.angrybirds.Screens;

import com.badlogic.angrybirds.*;
import com.badlogic.angrybirds.Scenes.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {
    private AngryBirds game;
    private Level level;
    Texture playBG;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    Hud hud;
    private World world;
    private Box2DDebugRenderer b2dr;
    private final float TIME_STEP = 1/144f;
    private final int VELOCITY_ITERATIONS = 8;
    private final int POSITION_ITERATIONS = 3;
    private Body mybox;

    public PlayScreen(AngryBirds game, Level level) {
        this.game = game;
        this.level = level;
        playBG = new Texture("backgrounds/playBG.jpg");
        gamecam = new OrthographicCamera(AngryBirds.V_WIDTH/ AngryBirds.PPM, AngryBirds.V_HEIGHT/AngryBirds.PPM);
        gamePort = new FitViewport(AngryBirds.V_WIDTH/AngryBirds.PPM, AngryBirds.V_HEIGHT/AngryBirds.PPM, gamecam);
        hud = new Hud(game.batch, game);
        world = new World(new Vector2(0, -10f), true);
        b2dr = new Box2DDebugRenderer();
        createGround();
    }

    // TODO: remove all the body renders from this class.
    //  put in their respective object classes

    @Override
    public void show() {
        Gdx.input.setInputProcessor(hud.stage);

        // body definition
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(32,40);

        // shape definition
        CircleShape shape = new CircleShape();
        shape.setRadius(1);

        // fixture definition
        FixtureDef fdef = new FixtureDef();
        fdef.shape=shape;
        fdef.density = 5.0f;
        fdef.friction = 0.5f;
        fdef.restitution = 0.1f;

        world.createBody(bdef).createFixture(fdef);

        shape.dispose();

        // rectanglular shape
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(32, 55f);

        PolygonShape shape2 = new PolygonShape();
        shape2.setAsBox(3f, 1f);

        // fixture definition
        fdef.shape=shape2;
        fdef.density = 5.0f;
        fdef.friction = 0.5f;
        fdef.restitution = 0.3f;

        mybox = world.createBody(bdef);
        mybox.createFixture(fdef);

        shape2.dispose();

        mybox.applyAngularImpulse(100, true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        game.batch.begin();
        game.batch.draw(playBG, 0, 0, AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        drawGameObjects();
        game.batch.end();

        b2dr.render(world, gamecam.combined);

        hud.stage.act(delta);
        hud.stage.draw();

        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height, true);
        hud.resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
    }

    private void drawGameObjects() {
        Texture catapultTexture = level.getCatapult().getTexture();
        game.batch.draw(new TextureRegion(catapultTexture), level.getCatapult().getX(), level.getCatapult().getY(), catapultTexture.getWidth() / 2, catapultTexture.getHeight() / 2, catapultTexture.getWidth(), catapultTexture.getHeight(), 0.6f, 0.6f, 0);
        for (Bird bird : level.getBirds()) {
            game.batch.draw(bird.getTexture(), bird.getX(), bird.getY());
        }

        for (Block block : level.getBlocks()) {
            game.batch.draw(new TextureRegion(block.getTexture()), block.getX(), block.getY(),
                block.getTexture().getWidth() / 2, block.getTexture().getHeight() / 2,
                block.getTexture().getWidth(), block.getTexture().getHeight(),
                1, 1, block.getOrientation());
        }

        for(Pig pig : level.getPigs()) {
            game.batch.draw(new TextureRegion(pig.getTexture()), pig.getX(), pig.getY(),
                pig.getTexture().getWidth() / 2, pig.getTexture().getHeight() / 2,
                pig.getTexture().getWidth(), pig.getTexture().getHeight(),
                1, 1, 0);
        }
    }

    private void createGround() {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(new Vector2(0, 2.5f));

        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[]{new Vector2(0, 2.5f), new Vector2(AngryBirds.V_WIDTH, 2.5f)});

        FixtureDef groundFixture = new FixtureDef();
        groundFixture.shape = groundShape;
        groundFixture.friction = 0.5f;
        groundFixture.restitution = 0.6f;

        world.createBody(groundBodyDef).createFixture(groundFixture);

        groundShape.dispose();
    }
}
