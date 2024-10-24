package com.badlogic.angrybirds.Screens;

import com.badlogic.angrybirds.AngryBirds;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WinScreen implements Screen {
    AngryBirds game;
    Texture winBG;
    Texture winMenu;
    private OrthographicCamera camera;
    private Viewport viewport;

    public WinScreen(AngryBirds game) {
        this.game = game;
        winBG = new Texture("backgrounds/playBG.jpg");
        winMenu = new Texture("winScreen.png");
        camera = new OrthographicCamera();
        viewport = new FitViewport(AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT, camera);
        camera.setToOrtho(false, AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
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
        game.batch.draw(winBG, 0, 0, AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        game.batch.draw(winMenu, AngryBirds.V_WIDTH / 2 - winMenu.getWidth() / 2, AngryBirds.V_HEIGHT / 2 - winMenu.getHeight() / 2);
        game.batch.end();
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
        winBG.dispose();
    }
}
