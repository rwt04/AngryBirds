package com.badlogic.angrybirds.Screens;

import com.badlogic.angrybirds.*;
import com.badlogic.angrybirds.Scenes.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

// todo: CATAPULT MECHANISM
//  place and launch birds

public class PlayScreen implements Screen {
    public final static float GROUND_Y_PIXELS = AngryBirds.V_HEIGHT*0.137777f;

    private final float TIME_STEP = 1/144f;
    private final int VELOCITY_ITERATIONS = 8;
    private final int POSITION_ITERATIONS = 3;

    private AngryBirds game;
    private Level level;
    Texture playBG;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    Hud hud;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Array<Body> worldBodies = new Array<Body>();

    private DrawBody drawBody;


    public PlayScreen(AngryBirds game, Level level) {
        this.game = game;
        this.level = level;
        playBG = new Texture("backgrounds/playBG.jpg");
        gamecam = new OrthographicCamera(AngryBirds.V_WIDTH/ AngryBirds.PPM, AngryBirds.V_HEIGHT/AngryBirds.PPM);
        gamePort = new FitViewport(AngryBirds.V_WIDTH/AngryBirds.PPM, AngryBirds.V_HEIGHT/AngryBirds.PPM, gamecam);
        hud = new Hud(game.batch, game);
        world = new World(new Vector2(0, -10f), true);
        b2dr = new Box2DDebugRenderer();
        drawBody = new DrawBody(world, b2dr);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(hud.stage);

        // Create ground
        createGround();

        // Create game objects
        createGameObjects();
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

        hud.stage.act(delta);
        hud.stage.draw();

        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

        b2dr.render(world, gamecam.combined);

        if (!level.getBirds().isEmpty() && Gdx.input.justTouched()) {
            Bird bird = level.getBirds().get(3);
            launchBird(bird);
        }
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

    private void createGameObjects() {
        // Create catapult
        level.getCatapult().createBody(world, 0.6f, 0.6f);

        // Create birds
        for (Bird bird : level.getBirds()) {
            bird.createBody(world, 0.7f, 0.7f);
        }

        // Create pigs
        for (Pig pig : level.getPigs()) {
            pig.createBody(world, 0.7f, 0.7f);
        }

        // Create blocks
        for (Block block : level.getBlocks()) {
            block.createBody(world, 1f, 1f);
        }
    }

    private void drawGameObjects() {
        world.getBodies(worldBodies);

        for (Body body : worldBodies) {
            if (body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition((body.getPosition().x * AngryBirds.PPM) - sprite.getWidth() / 2, (body.getPosition().y * AngryBirds.PPM) - sprite.getHeight() / 2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(game.batch);
            }
        }
    }
    private void launchBird(Bird bird) {
        if (!bird.isLaunched()) {
            // Apply an impulse to the bird's body
            Vector2 launchForce = new Vector2(30f, 33f); // Adjust the force as needed
            bird.getBody().applyLinearImpulse(launchForce, bird.getBody().getWorldCenter(), true);
            bird.setLaunched(true);
        }
    }

    private void createGround() {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(new Vector2(0, GROUND_Y_PIXELS / AngryBirds.PPM));

        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[]{
            new Vector2(0, 0),
            new Vector2(AngryBirds.V_WIDTH / AngryBirds.PPM, 0)
        });
        System.out.println("Ground position: " + groundBodyDef.position.x + " " + groundBodyDef.position.y);
        System.out.println("width: " + AngryBirds.V_WIDTH / AngryBirds.PPM + " height: " + AngryBirds.V_HEIGHT / AngryBirds.PPM); ;

        FixtureDef groundFixture = new FixtureDef();
        groundFixture.shape = groundShape;
        groundFixture.friction = 0.5f;
        groundFixture.restitution = 0.6f;

        world.createBody(groundBodyDef).createFixture(groundFixture);

        groundShape.dispose();
    }


}
