package com.badlogic.angrybirds;

import com.badlogic.angrybirds.Screens.MainMenuScreen;
import com.badlogic.angrybirds.Screens.PlayScreen;
import com.badlogic.angrybirds.Screens.SplashScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AngryBirds extends Game {
    public static final int V_WIDTH = 1280;
    public static final int V_HEIGHT = 720;

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
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}
