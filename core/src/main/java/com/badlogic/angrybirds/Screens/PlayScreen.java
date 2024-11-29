package com.badlogic.angrybirds.Screens;

import com.badlogic.angrybirds.*;
import com.badlogic.angrybirds.Birds.Bird;
import com.badlogic.angrybirds.Blocks.Block;
import com.badlogic.angrybirds.GameStates.*;
import com.badlogic.angrybirds.Pigs.Pig;
import com.badlogic.angrybirds.Scenes.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayScreen implements Screen {
    public final static float GROUND_Y_PIXELS = AngryBirds.V_HEIGHT * 0.137777f;

    private int score = 0;

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

    private Bird currentBird;
    private Bird onScreenLoadedBird;
    private boolean isBirdOnCatapult = false;
    private boolean isDragging = false;
    private Vector2 initialTouch = new Vector2();
    private Vector2 maxDragDistance = new Vector2(100, 100); // Adjust as needed

    private CollisionListener collisionListener;

    private boolean isGameOver = false;
    private float gameOverTime = 0;
    private float birdLaunchTime = 0;
    private boolean loadedGame = false;
    private float loadedBirdTime = 0;
    private boolean checkedForLoadedBird = false;

    public PlayScreen(AngryBirds game, Level level) {
        this.game = game;
        this.level = level;
        playBG = new Texture("backgrounds/playBG.jpg");
        gamecam = new OrthographicCamera(AngryBirds.V_WIDTH / AngryBirds.PPM, AngryBirds.V_HEIGHT / AngryBirds.PPM);
        gamePort = new FitViewport(AngryBirds.V_WIDTH / AngryBirds.PPM, AngryBirds.V_HEIGHT / AngryBirds.PPM, gamecam);
        hud = new Hud(game.batch, game, this);
        world = new World(new Vector2(0, -10f), true);
        b2dr = new Box2DDebugRenderer();
        collisionListener = new CollisionListener(hud);
        world.setContactListener(collisionListener);
        // Create ground
        createGround();
        // Create game objects
        createGameObjects();
    }

    // another constructor for loading saved game
    public PlayScreen(AngryBirds game, Level level, boolean loadGame) {
        this.game = game;
        this.level = level;
        this.loadedGame = loadGame;
        playBG = new Texture("backgrounds/playBG.jpg");
        gamecam = new OrthographicCamera(AngryBirds.V_WIDTH / AngryBirds.PPM, AngryBirds.V_HEIGHT / AngryBirds.PPM);
        gamePort = new FitViewport(AngryBirds.V_WIDTH / AngryBirds.PPM, AngryBirds.V_HEIGHT / AngryBirds.PPM, gamecam);
        hud = new Hud(game.batch, game, this);
        world = new World(new Vector2(0, -10f), true);
        b2dr = new Box2DDebugRenderer();
        collisionListener = new CollisionListener(hud);
        world.setContactListener(collisionListener);
        // Create ground
        createGround();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(hud.stage);
        // Set up the first bird immediately
        if(!loadedGame){
            setupNextBird();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        game.batch.begin();
        game.batch.draw(playBG, 0, 0, AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);

        // Draw the catapult
        Catapult catapult = level.getCatapult();
        Sprite sprite = new Sprite(catapult.getTexture());
        sprite.setSize(catapult.getTexture().getWidth() * 0.6f, catapult.getTexture().getHeight() * 0.6f);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setPosition(catapult.getX(), catapult.getY());
        sprite.draw(game.batch);

        drawGameObjects(); // Ensure this method is called to render the textures

        game.batch.end();

        hud.stage.act(delta);
        hud.stage.draw();

        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

        b2dr.render(world, gamecam.combined);


        if (currentBird != null && currentBird.isLaunched()) {
            birdLaunchTime += delta;
            if (hasBirdStopped(currentBird) || isBirdOutOfScreen(currentBird) || birdLaunchTime >= 15) {
                world.destroyBody(currentBird.getBody());
                currentBird = null;
                isBirdOnCatapult = false;
                birdLaunchTime = 0;
                setupNextBird();
            }
        }

        if(loadedGame && checkedForLoadedBird){
            loadedBirdTime += delta;
            if (hasBirdStopped(onScreenLoadedBird) || isBirdOutOfScreen(onScreenLoadedBird) || loadedBirdTime >= 10) {
                world.destroyBody(onScreenLoadedBird.getBody());
                onScreenLoadedBird = null;
                checkedForLoadedBird = false;
                loadedBirdTime = 0;
            }
        }

        List<Pig> pigsToRemove = new ArrayList<>();
        for (Pig pig : level.getPigs()) {
            if (isPigOutOfScreen(pig)) {
                world.destroyBody(pig.getBody());
                pigsToRemove.add(pig);
                hud.updateScore(pig.getMaxHP() * 50);
            }
        }
        level.getPigs().removeAll(pigsToRemove);
        pigsToRemove.clear();

        handleInput();
        handleCollisions();

        checkWinLose();

        if (isGameOver) {
            gameOverTime += delta;
            if (gameOverTime >= 3) {
                if (level.getPigs().isEmpty()) {
                    game.setScreen(new WinScreen(game, hud.getScore(), level.getCurrentLevel()));
                } else {
                    game.setScreen(new LoseScreen(game, hud.getScore(), level.getCurrentLevel()));
                }
                dispose();
            }
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
            if (body.getUserData() != null && body.getUserData() instanceof GameObject) {
                Sprite sprite = null;
                if(body.getUserData() instanceof Bird){
                    Bird bird = (Bird) body.getUserData();
                    sprite = bird.getSprite();
                } else if (body.getUserData() instanceof Block){
                    Block block = (Block) body.getUserData();
                    sprite = block.getSprite();
                } else if (body.getUserData() instanceof Pig){
                    Pig pig = (Pig) body.getUserData();
                    sprite = pig.getSprite();
                }
                sprite.setPosition(body.getPosition().x * AngryBirds.PPM - sprite.getWidth() / 2,
                    body.getPosition().y * AngryBirds.PPM - sprite.getHeight() / 2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(game.batch);
            }
        }

        // Draw birds that are not on the catapult below the ground
        float birdYPosition = GROUND_Y_PIXELS - 50; // Adjust this value to set the position below the ground
        for (Bird bird : level.getBirds()) {
            Sprite sprite = new Sprite(bird.getTexture());
            sprite.setSize(bird.getTexture().getWidth() * 0.7f, bird.getTexture().getHeight() * 0.7f);
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            sprite.setPosition(50 + level.getBirds().indexOf(bird) * 50, birdYPosition); // Adjust the x position as needed
            sprite.draw(game.batch);
        }
    }

    private void handleInput() {
        if (currentBird != null && !currentBird.isLaunched()) {
            if (Gdx.input.isTouched()) {
                Vector3 touchPos3D = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                gamecam.unproject(touchPos3D);
                Vector2 touchPos = new Vector2(touchPos3D.x, touchPos3D.y);

                if (!isDragging) {
                    // Check if the touch position is within the bird's body bounds
                    if (currentBird.getBody().getFixtureList().first().testPoint(touchPos)) {
                        initialTouch.set(Gdx.input.getX(), Gdx.input.getY());
                        isDragging = true;
                    }
                } else {
                    Vector2 currentTouch = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                    Vector2 dragDistance = currentTouch.sub(initialTouch);
                    dragDistance.x = -dragDistance.x; // Invert the x-axis movement

                    // Scale the drag distance
                    float dragScaleFactor = 0.025f; // Adjust this value to scale the speed
                    dragDistance.scl(dragScaleFactor);

                    // Limit the drag distance
                    float maxDragLength = 1.2f; // Adjust this value to set the maximum drag length
                    if (dragDistance.len() > maxDragLength) {
                        dragDistance.setLength(maxDragLength);
                    }

                    currentBird.getBody().setTransform(new Vector2(level.getCatapult().getX() / AngryBirds.PPM,
                        level.getCatapult().getTexture().getHeight() / AngryBirds.PPM).sub(dragDistance), 0);
                }
            } else if (isDragging) {
                isDragging = false;
                Vector2 launchDirection = new Vector2(level.getCatapult().getX() / AngryBirds.PPM,
                    level.getCatapult().getTexture().getHeight() / AngryBirds.PPM).sub(currentBird.getBody().getPosition());
                currentBird.getBody().setType(BodyDef.BodyType.DynamicBody); // Set to KinematicBody
                launchBird(currentBird, launchDirection);
                isBirdOnCatapult = false;
            }
        }
    }

    private void checkWinLose() {
        if (isGameOver) {
            return;
        }

        // Check for win condition
        if (level.getPigs().isEmpty()) {
            isGameOver = true;
            gameOverTime = 0;
        }

        // Check for lose condition
        if (level.getBirds().isEmpty() && currentBird == null && !level.getPigs().isEmpty()) {
            isGameOver = true;
            gameOverTime = 0;
        }
    }

    private void setupNextBird() {
        if (!isBirdOnCatapult && !level.getBirds().isEmpty()) {
            currentBird = level.getBirds().remove(0);
            currentBird.setCurrentBird(true);
            if (!currentBird.isLaunched()) {
                    currentBird.setCurrentBird(true);
                    currentBird.createBody(world, 0.7f, 0.7f); // Create the bird body
                    currentBird.getBody().setType(BodyDef.BodyType.KinematicBody); // Set to KinematicBody
                    currentBird.getBody().setTransform(new Vector2(level.getCatapult().getX() / AngryBirds.PPM + 0.5f,
                        level.getCatapult().getTexture().getHeight() / AngryBirds.PPM), 0);
                    isBirdOnCatapult = true;
                    currentBird.setBirdOnCatapult(true);

            } else {
                onScreenLoadedBird = currentBird;
                if (!level.getBirds().isEmpty()) {
                    currentBird = level.getBirds().remove(0);
                    currentBird.setCurrentBird(true);
                    currentBird.createBody(world, 0.7f, 0.7f); // Create the bird body
                    currentBird.getBody().setType(BodyDef.BodyType.KinematicBody); // Set to KinematicBody
                    currentBird.getBody().setTransform(new Vector2(level.getCatapult().getX() / AngryBirds.PPM + 0.5f,
                        level.getCatapult().getTexture().getHeight() / AngryBirds.PPM), 0);
                    isBirdOnCatapult = true;
                    currentBird.setBirdOnCatapult(true);
                }
                checkedForLoadedBird = true;
            }
        }
    }

    private void launchBird(Bird bird, Vector2 launchDirection) {
        if (!bird.isLaunched()) {
            float launchForceMagnitude = launchDirection.len() * 20f; // Adjust the force multiplier as needed
            Vector2 launchForce = launchDirection.nor().scl(launchForceMagnitude);
            bird.getBody().applyLinearImpulse(launchForce, bird.getBody().getWorldCenter(), true);
            bird.setLaunched(true);
        }
    }

    private boolean hasBirdStopped(Bird bird) {
        float velocityThreshold = 0.05f; // Adjust this value as needed
        return bird.getBody().getLinearVelocity().len() < velocityThreshold;
    }

    private boolean isBirdOutOfScreen(Bird bird) {
        float birdX = bird.getBody().getPosition().x * AngryBirds.PPM;
        float birdY = bird.getBody().getPosition().y * AngryBirds.PPM;
        return birdX < -50 || birdX > AngryBirds.V_WIDTH+50 || birdY < -50;
    }
    private boolean isPigOutOfScreen(Pig pig) {
        float pigX = pig.getBody().getPosition().x * AngryBirds.PPM;
        float pigY = pig.getBody().getPosition().y * AngryBirds.PPM;
        return pigX < -50 || pigX > AngryBirds.V_WIDTH + 50 || pigY < -50;
    }


    private void handleCollisions() {
        Array<GameObject> objectsToDestroy = collisionListener.getObjectsToDestroy();
        for (GameObject gameObject : objectsToDestroy) {
            if (gameObject instanceof Bird) {
                Bird bird = (Bird) gameObject;
                level.getBirds().remove(bird);
                world.destroyBody(bird.getBody());
            } else if (gameObject instanceof Block) {
                Block block = (Block) gameObject;
                level.getBlocks().remove(block);
                world.destroyBody(block.getBody());
            } else if (gameObject instanceof Pig) {
                Pig pig = (Pig) gameObject;
                level.getPigs().remove(pig);
                world.destroyBody(pig.getBody());
            }
        }
        objectsToDestroy.clear();
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
        groundFixture.friction = 1f;
        groundFixture.restitution = 0.3f;

//        set grounds user data to "ground";
        Body groundBody = world.createBody(groundBodyDef);
        groundBody.createFixture(groundFixture);
        groundBody.setUserData("ground");

        groundShape.dispose();
    }

    public Level getLevel() {
        return new Level(level.getCurrentLevel());
    }

    public void saveGame(String filePath) {
        worldBodies = new Array<Body>();
        world.getBodies(worldBodies);

        List<BirdState> birdStates = new ArrayList<>();
        for (Body body : worldBodies) {
            if (body.getUserData() instanceof Bird) {
                Bird bird = (Bird) body.getUserData();
                birdStates.add(new BirdState(
                    body.getPosition().x, body.getPosition().y, body.getAngle(), bird.getDamage(),
                    body.getAngularVelocity(), body.getInertia(), body.getLinearVelocity().x, body.getLinearVelocity().y,
                    bird.getTexture().toString(), bird.isLaunched(), bird.isCurrentBird(), bird.isBirdOnCatapult()
                ));
            }
        }

        // Save remaining birds that have not been launched
        for (Bird bird : level.getBirds()) {
            birdStates.add(new BirdState(
                bird.getX(), bird.getY(), 0, bird.getDamage(),
                0, 0, 0, 0, bird.getTexture().toString(), false, false, false
            ));
        }

        List<BlockState> blockStates = new ArrayList<>();
        for (Body body : worldBodies) {
            if (body.getUserData() instanceof Block) {
                Block block = (Block) body.getUserData();
                blockStates.add(new BlockState(
                    body.getPosition().x, body.getPosition().y, body.getAngle(), block.getHP(), block.getMaxHP(),
                    body.getAngularVelocity(), body.getInertia(), body.getLinearVelocity().x, body.getLinearVelocity().y,
                    block.getTexture().toString(), block.getDensity()
                ));
            }
        }

        List<PigState> pigStates = new ArrayList<>();
        for (Body body : worldBodies) {
            if (body.getUserData() instanceof Pig) {
                Pig pig = (Pig) body.getUserData();
                pigStates.add(new PigState(
                    body.getPosition().x, body.getPosition().y, body.getAngle(), pig.getHP(), pig.getMaxHP(),
                    body.getAngularVelocity(), body.getInertia(), body.getLinearVelocity().x, body.getLinearVelocity().y,
                    pig.getTexture().toString()
                ));
            }
        }

        GameState gameState = new GameState(hud.getScore(), birdStates, blockStates, pigStates);
        try {
            GameStateManager.saveGameState(gameState, filePath);
            Gdx.app.log("PlayScreen", "Game saved successfully.");
        } catch (IOException e) {
            Gdx.app.error("PlayScreen", "Failed to save game.", e);
        }
    }

    public void loadGame(String filePath) {
        try {
            GameState gameState = GameStateManager.loadGameState(filePath);
            hud.updateScore(gameState.getScore());

            // Clear existing game objects
            level.getBirds().clear();
            level.getBlocks().clear();
            level.getPigs().clear();

            // Destroy all bodies except the ground
            worldBodies = new Array<Body>();
            world.getBodies(worldBodies);
            for (Body body : worldBodies) {
                if (!"ground".equals(body.getUserData())) {
                    world.destroyBody(body);
                }
            }

            // Load birds
            List<Bird> loadedBirds = new ArrayList<>();
            for (BirdState birdState : gameState.getBirds()) {
                Bird bird = new Bird(new Texture(birdState.getTexture()), birdState.getX(), birdState.getY()); // Adjust as needed
                if (birdState.isLaunched()) {
                    bird.createBody(world, 0.7f, 0.7f);
                    bird.getBody().setTransform(birdState.getX(), birdState.getY(), birdState.getAngle());
                    bird.getBody().setLinearVelocity(birdState.getLinearVelocityX(), birdState.getLinearVelocityY());
                    bird.getBody().setAngularVelocity(birdState.getAngularVelocity());
                }

                bird.setBirdOnCatapult(birdState.isOnCatapult());
                bird.setLaunched(birdState.isLaunched());
                bird.setCurrentBird(birdState.isCurrent());
                bird.setDamage(birdState.getDamage());
                loadedBirds.add(bird);
            }
            level.setBirds(loadedBirds);

            // Load blocks
            for (BlockState blockState : gameState.getBlocks()) {
                Block block = new Block(new Texture(blockState.getTexture()), blockState.getX(), blockState.getY(), blockState.getAngle(), blockState.getMaxHP(), blockState.getDensity()); // Adjust as needed
                block.createBody(world, 1f, 1f);
                block.getBody().setTransform(blockState.getX(), blockState.getY(), blockState.getAngle());
                block.getBody().setLinearVelocity(blockState.getLinearVelocityX(), blockState.getLinearVelocityY());
                block.getBody().setAngularVelocity(blockState.getAngularVelocity());
                block.setHP(blockState.getHp());
                level.getBlocks().add(block);
            }

            // Load pigs
            for (PigState pigState : gameState.getPigs()) {
                Pig pig = new Pig(new Texture(pigState.getTexture()), pigState.getX(), pigState.getY(), pigState.getMaxHP()); // Adjust as needed
                pig.createBody(world, 0.7f, 0.7f);
                pig.getBody().setTransform(pigState.getX(), pigState.getY(), pigState.getAngle());
                pig.getBody().setLinearVelocity(pigState.getLinearVelocityX(), pigState.getLinearVelocityY());
                pig.getBody().setAngularVelocity(pigState.getAngularVelocity());
                pig.setHP(pigState.getHp());
                level.getPigs().add(pig);
            }

            // Set up the next bird on the catapult
            setupNextBird();

            Gdx.app.log("PlayScreen", "Game loaded successfully.");
        } catch (IOException e) {
            Gdx.app.error("PlayScreen", "Failed to load game.", e);
        }
    }
}
