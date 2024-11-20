package com.badlogic.angrybirds.Screens;

import com.badlogic.angrybirds.*;
import com.badlogic.angrybirds.Scenes.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    public PlayScreen(AngryBirds game, Level level) {
        this.game = game;
        this.level = level;
        playBG = new Texture("backgrounds/playBG.jpg");
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT, gamecam);
        hud = new Hud(game.batch, game);
        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        createGround();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(hud.stage);
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
        world.step(1/60f, 6, 2);
        b2dr.render(world, gamecam.combined);
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

    public void createGround(){
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(0, 10));
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(gamecam.viewportWidth, 10.0f);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();
    }
}
