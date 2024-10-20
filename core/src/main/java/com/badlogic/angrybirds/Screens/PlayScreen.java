package com.badlogic.angrybirds.Screens;

import com.badlogic.angrybirds.AngryBirds;
import com.badlogic.angrybirds.Scenes.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.*;

public class PlayScreen implements Screen {
    private AngryBirds game;
    Texture playBG;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    Hud hud;

    public PlayScreen(AngryBirds game) {
        this.game = game;
        playBG = new Texture("playBG.jpg");
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT, gamecam);
        hud = new Hud(game.batch);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height, true);
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
}
