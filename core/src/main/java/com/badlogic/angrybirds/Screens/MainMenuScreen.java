// MainMenuScreen.java
package com.badlogic.angrybirds.Screens;

import com.badlogic.angrybirds.AngryBirds;
import com.badlogic.angrybirds.Level;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.nio.file.Files;
import java.nio.file.Paths;

public class MainMenuScreen implements Screen {
    private AngryBirds game;
    Texture menuBG;
    OrthographicCamera camera;
    Viewport viewport;

    private Stage stage;
    private Skin skin;
    private Table table;
    private TextButton playButton, exitButton, loadButton;
    private Label messageLabel;

    public MainMenuScreen(AngryBirds game) {
        this.game = game;
        menuBG = new Texture("backgrounds/menuBG.jpg");
        camera = new OrthographicCamera();
        viewport = new FitViewport(AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT, camera);
        camera.setToOrtho(false, AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        stage = new Stage(viewport, game.batch);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        playButton = new TextButton("New Game", skin, "default");
        loadButton = new TextButton("Load Game", skin, "default");
        exitButton = new TextButton("Exit", skin, "default");
        messageLabel = new Label("", skin, "big");
        messageLabel.setColor(1, 0, 0, 1);
        messageLabel.setWidth(400f);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
                dispose();
            }
        });

        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!Files.exists(Paths.get("save.json"))){
                    messageLabel.setText("No saved games found");
                    return;
                }
                PlayScreen playScreen = new PlayScreen(game, new Level(1), true);
                playScreen.loadGame("save.json");
                game.setScreen(playScreen);
                dispose();
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table = new Table();
        table.center();
        table.setFillParent(true);

        float buttonWidth = 400f;

        table.add(playButton).width(buttonWidth).padBottom(20);
        table.row();
        table.add(loadButton).width(buttonWidth).padBottom(20);
        table.row();
        table.add(exitButton).width(buttonWidth);
        table.row();
        table.add(messageLabel).padTop(20);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(menuBG, 0, 0, AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
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
    }
}
