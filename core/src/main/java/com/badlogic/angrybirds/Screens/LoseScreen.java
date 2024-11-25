package com.badlogic.angrybirds.Screens;

import com.badlogic.angrybirds.AngryBirds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoseScreen implements Screen {
    AngryBirds game;
    Texture loseBG;
    Texture loseMenu;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private int score;

    public LoseScreen(AngryBirds game, int score) {
        this.game = game;
        this.score = score;
        loseBG = new Texture("backgrounds/playBG.jpg");
        loseMenu = new Texture("loseScreen.png");
        camera = new OrthographicCamera();
        viewport = new FitViewport(AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT, camera);
        camera.setToOrtho(false, AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        stage = new Stage(viewport, game.batch);

        Label scoreLabel = new Label("Score: " + score, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Table table = new Table();
        table.center();
        table.setFillParent(true);
        table.add(scoreLabel).expandX().padTop(10);
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(loseBG, 0, 0, AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        game.batch.draw(loseMenu, AngryBirds.V_WIDTH / 2 - loseMenu.getWidth() / 2, AngryBirds.V_HEIGHT / 2 - loseMenu.getHeight() / 2);
        game.batch.end();

        stage.act(delta);
        stage.draw();
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
        loseBG.dispose();
        loseMenu.dispose();
        stage.dispose();
    }
}
