package com.badlogic.angrybirds.Screens;

import com.badlogic.angrybirds.AngryBirds;
import com.badlogic.angrybirds.Level;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PauseScreen implements Screen {
    private AngryBirds game;
    private Texture pauseBG;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Skin skin;

    public PauseScreen(AngryBirds game) {
        this.game = game;
        pauseBG = new Texture("backgrounds/playBG.jpg");
        camera = new OrthographicCamera();
        viewport = new FitViewport(AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT, camera);
        camera.setToOrtho(false, AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        stage = new Stage(viewport, game.batch);

        // Load skin for TextButtons
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        // Create TextButtons
        TextButton resumeButton = new TextButton("Resume", skin);
        TextButton restartButton = new TextButton("Restart", skin);
        TextButton saveExitButton = new TextButton("Save & Exit", skin);
        TextButton mainMenuButton = new TextButton("Exit To Menu", skin);

        // Position buttons using a Table
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        float buttonWidth = 500f;

        table.add(resumeButton).width(buttonWidth).padBottom(20);
        table.row();
        table.add(restartButton).width(buttonWidth).padBottom(20);
        table.row();
        table.add(saveExitButton).width(buttonWidth).padBottom(20);
        table.row();
        table.add(mainMenuButton).width(buttonWidth);

        stage.addActor(table);

        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        // restart button
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game, new Level(1)));
                dispose();
            }
        });
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
        game.batch.draw(pauseBG, 0, 0, AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        game.batch.setColor(0, 0, 0, 0.5f);
        game.batch.draw(pauseBG, 0, 0, AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        game.batch.setColor(1, 1, 1, 1);
        game.batch.end();

        // TODO remove this and implement button actions instead
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PlayScreen(game,new Level(1)));
        }

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
        pauseBG.dispose();
        stage.dispose();
        skin.dispose();
    }
}
