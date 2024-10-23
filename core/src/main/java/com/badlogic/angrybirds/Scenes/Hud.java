package com.badlogic.angrybirds.Scenes;

import com.badlogic.angrybirds.AngryBirds;
import com.badlogic.angrybirds.Screens.PauseScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {
    public Stage stage;
    private Viewport viewport;

    private Integer score;
    Label scoreLabel;
    private ImageButton pauseButton;

    public Hud(SpriteBatch sb, final AngryBirds game) {
        score = 0;
        viewport = new FitViewport(AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        // Load the images for the button states
        Texture pauseUpTexture = new Texture("pause.up.png");
        Texture pauseDownTexture = new Texture("pause.down.png");

        // Create an ImageButton using these images
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(pauseUpTexture));
        style.down = new TextureRegionDrawable(new TextureRegion(pauseDownTexture));
        pauseButton = new ImageButton(style);

        // Position the button at the top right of the screen
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("SCORE : %06d", score), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        table.add(pauseButton).expandX().left().padTop(10).padLeft(10);
        table.add(scoreLabel).expandX().right().padTop(10).padRight(10);

        stage.addActor(table);

        // Add listener to the pause button
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseScreen(game));
            }
        });
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
