package com.badlogic.angrybirds.Screens;

import com.badlogic.angrybirds.AngryBirds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


public class WinScreen implements Screen {
    AngryBirds game;
    Texture winBG;
    Texture winMenu;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private int score;

    public WinScreen(AngryBirds game, int score) {
        this.game = game;
        this.score = score;
        winBG = new Texture("backgrounds/playBG.jpg");
        winMenu = new Texture("winPic.png");
        camera = new OrthographicCamera();
        viewport = new FitViewport(AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT, camera);
        camera.setToOrtho(false, AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        stage = new Stage(viewport, game.batch);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("skin/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40; // Set the desired font size
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose(); // Dispose the generator after use

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        Label scoreLabel = new Label("Score: " + score, labelStyle);

        Texture homeUpTexture = new Texture("buttons/homeup.png");
        Texture homeDownTexture = new Texture("buttons/homedown.png");
        Texture restartUpTexture = new Texture("buttons/restartup.png");
        Texture restartDownTexture = new Texture("buttons/restartdown.png");

        // Create an ImageButton using these images
        ImageButton.ImageButtonStyle style1 = new ImageButton.ImageButtonStyle();
        style1.up = new TextureRegionDrawable(new TextureRegion(homeUpTexture));
        style1.down = new TextureRegionDrawable(new TextureRegion(homeDownTexture));
        ImageButton homebutton = new ImageButton(style1);

        ImageButton.ImageButtonStyle style2 = new ImageButton.ImageButtonStyle();
        style2.up = new TextureRegionDrawable(new TextureRegion(restartUpTexture));
        style2.down = new TextureRegionDrawable(new TextureRegion(restartDownTexture));
        ImageButton restartbutton = new ImageButton(style2);

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        table.add(scoreLabel).expandX().padTop(10);
        table.row();
        table.center();
        table.add(homebutton).size(75,75);
        table.add(restartbutton).size(75,75);
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
        game.batch.draw(winBG, 0, 0, AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        game.batch.draw(winMenu, AngryBirds.V_WIDTH / 2 - winMenu.getWidth() / 2, AngryBirds.V_HEIGHT / 2 + winMenu.getHeight() / 8);
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
        winBG.dispose();
        winMenu.dispose();
        stage.dispose();
    }
}
