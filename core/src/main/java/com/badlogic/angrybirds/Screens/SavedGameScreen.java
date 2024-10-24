package com.badlogic.angrybirds.Screens;

import com.badlogic.angrybirds.AngryBirds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SavedGameScreen implements Screen {
    private AngryBirds game;
    private Texture playBG;
    private Stage stage;
    private Skin skin;
    private Viewport viewport;

    public SavedGameScreen(AngryBirds game) {
        this.game = game;
        playBG = new Texture("backgrounds/playBG.jpg");
        viewport = new FitViewport(AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label titleLabel = new Label("Saved Games", skin, "big");
        table.add(titleLabel).padBottom(50);
        table.row();

        for (int i = 1; i <= 2; i++) {
            TextButton gameButton = new TextButton("Game " + i, skin);
            table.add(gameButton).padBottom(20);
            table.row();
        }

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0f, 1);
        game.batch.begin();
        game.batch.draw(playBG, 0, 0, AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        game.batch.setColor(0, 0, 0, 0.5f);
        game.batch.draw(playBG, 0, 0, AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        game.batch.setColor(1, 1, 1, 1);
        game.batch.end();

        stage.act();
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
        playBG.dispose();
        stage.dispose();
        skin.dispose();
    }
}
