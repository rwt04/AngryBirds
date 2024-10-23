package com.badlogic.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BigPig extends Pig {
    public BigPig(float x, float y) {
        super(new Texture(Gdx.files.internal("gameObjects/pigs/bigPig.png")), x, y);
    }
}
