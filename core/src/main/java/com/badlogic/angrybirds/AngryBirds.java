package com.badlogic.angrybirds;

import com.badlogic.angrybirds.Screens.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AngryBirds extends Game {
    public static final int V_WIDTH = 1280;
    public static final int V_HEIGHT = 720;
    public static final float PPM = 60;

    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        setScreen(new SplashScreen(this));
    }

    @Override
    public void render() {
        super.render();

        // hard coded key presses to switch screens
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            setScreen(new WinScreen(this));
        }else if(Gdx.input.isKeyPressed(Input.Keys.P)){
            setScreen(new PlayScreen(this, new Level(1)));
        }else if(Gdx.input.isKeyPressed(Input.Keys.M)) {
            setScreen(new MainMenuScreen(this));
        }else if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            setScreen(new PauseScreen(this, new PauseScreen(this, new PlayScreen(this, new Level(1)))));
        }else if(Gdx.input.isKeyPressed(Input.Keys.L)){
            setScreen(new LevelScreen(this));
        }else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            setScreen(new SplashScreen(this));
        }else if(Gdx.input.isKeyPressed(Input.Keys.V)){
            setScreen(new SavedGameScreen(this));

        }
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}
