package com.badlogic.angrybirds.Pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MediumPig extends Pig {

    private static final int MAX_HP = 125;

    public MediumPig(float x, float y) {
        super(new Texture(Gdx.files.internal("gameObjects/pigs/mediumPig.png")), x, y, MAX_HP);
    }
}
