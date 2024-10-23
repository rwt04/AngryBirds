package com.badlogic.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MediumPig extends Pig {
    public MediumPig(float x, float y) {
        super(new Texture(Gdx.files.internal("gameObjects/pigs/mediumPig.png")), x, y);
    }
}
