package com.badlogic.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SmallPig extends Pig {

    private static final int MAX_HP = 70;

    public SmallPig(float x, float y) {
        super(new Texture(Gdx.files.internal("gameObjects/pigs/smallPig.png")), x, y, MAX_HP);
    }
}
