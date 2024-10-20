package com.badlogic.angrybirds.Screens;

import com.badlogic.angrybirds.AngryBirds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen {
    private AngryBirds game;
    Texture menuBG;
    OrthographicCamera camera;
    Viewport viewport;
    private boolean isTouched = false;

    public MainMenuScreen(AngryBirds game) {
        this.game = game;
        menuBG = new Texture("menuBG.jpg");
        camera = new OrthographicCamera();
        viewport = new FitViewport(AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT, camera);
        camera.setToOrtho(false, AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        isTouched = false;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        game.batch.draw(menuBG, 0, 0, AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);

        game.batch.end();

        if (Gdx.input.isTouched()) {
            isTouched = true;
        } else if (isTouched) {
            game.setScreen(new PlayScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
