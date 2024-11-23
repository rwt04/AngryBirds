package com.badlogic.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class YellowBird extends Bird {
    public YellowBird(float x, float y) {
        super(new Texture(Gdx.files.internal("gameObjects/birds/yellowBird.png")), x, y);
        setDamage(47);
    }
}
