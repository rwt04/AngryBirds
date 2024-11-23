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

public class PlayScreen implements Screen {
    public final static float GROUND_Y_PIXELS = AngryBirds.V_HEIGHT * 0.137777f;

    private final float TIME_STEP = 1 / 144f;
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
    private Bird currentBird;
    private boolean isBirdOnCatapult = false;
    private boolean isDragging = false;
    private Vector2 initialTouch = new Vector2();
    private Vector2 maxDragDistance = new Vector2(100, 100); // Adjust as needed

    private float delayTime = 0f;
    private final float BIRD_SETUP_DELAY = 4f; // Adjust this value to set the delay time in seconds

    public PlayScreen(AngryBirds game, Level level) {
        this.game = game;
        this.level = level;
        playBG = new Texture("backgrounds/playBG.jpg");
        gamecam = new OrthographicCamera(AngryBirds.V_WIDTH / AngryBirds.PPM, AngryBirds.V_HEIGHT / AngryBirds.PPM);
        gamePort = new FitViewport(AngryBirds.V_WIDTH / AngryBirds.PPM, AngryBirds.V_HEIGHT / AngryBirds.PPM, gamecam);
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

        // Set up the first bird immediately
        setupNextBird();
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

        // Decrement delay time and set up the next bird if delay has elapsed
        if (delayTime > 0) {
            delayTime -= delta;
            if (delayTime <= 0) {
                setupNextBird();
            }
        }

        handleInput();
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

    private void handleInput() {
        if (currentBird != null && !currentBird.isLaunched()) {
            if (Gdx.input.isTouched()) {
                if (!isDragging) {
                    initialTouch.set(Gdx.input.getX(), Gdx.input.getY());
                    isDragging = true;

                    // Disable collision with catapult while dragging
                    Filter filter = currentBird.getBody().getFixtureList().first().getFilterData();
                    filter.maskBits = Constants.MASK_BIRD & ~Constants.CATEGORY_CATAPULT;
                    currentBird.getBody().getFixtureList().first().setFilterData(filter);
                } else {
                    Vector2 currentTouch = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                    Vector2 dragDistance = currentTouch.sub(initialTouch);
                    dragDistance.x = -dragDistance.x; // Invert the x-axis movement

                    // Scale the drag distance
                    float dragScaleFactor = 0.05f; // Adjust this value to scale the speed
                    dragDistance.scl(dragScaleFactor);

                    // Limit the drag distance
                    float maxDragLength = 2.5f; // Adjust this value to set the maximum drag length
                    if (dragDistance.len() > maxDragLength) {
                        dragDistance.setLength(maxDragLength);
                    }

                    currentBird.getBody().setTransform(new Vector2(level.getCatapult().getX() / AngryBirds.PPM,
                        level.getCatapult().getTexture().getHeight() / AngryBirds.PPM).sub(dragDistance), 0);
                }
            } else if (isDragging) {
                isDragging = false;
                launchBird(currentBird);
                isBirdOnCatapult = false;
                delayTime = BIRD_SETUP_DELAY; // Start the delay timer
            }
        }
    }


    private void setupNextBird() {
        if (!isBirdOnCatapult && delayTime <= 0 && !level.getBirds().isEmpty()) {
            currentBird = level.getBirds().remove(0);
            currentBird.getBody().setTransform(new Vector2(level.getCatapult().getX() / AngryBirds.PPM,
                level.getCatapult().getTexture().getHeight() / AngryBirds.PPM), 0);
            isBirdOnCatapult = true;
        }
    }

    private void launchBird(Bird bird) {
        if (!bird.isLaunched()) {
            Vector2 launchForce = new Vector2(15f, 15f); // Adjust the force as needed
            bird.getBody().applyLinearImpulse(launchForce, bird.getBody().getWorldCenter(), true);
            bird.setLaunched(true);

            // Enable collision with catapult after release
            Filter filter = bird.getBody().getFixtureList().first().getFilterData();
            filter.maskBits = Constants.MASK_BIRD;
            bird.getBody().getFixtureList().first().setFilterData(filter);
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

        FixtureDef groundFixture = new FixtureDef();
        groundFixture.shape = groundShape;
        groundFixture.friction = 0.5f;
        groundFixture.restitution = 0.6f;

        world.createBody(groundBodyDef).createFixture(groundFixture);

        groundShape.dispose();
    }
}
