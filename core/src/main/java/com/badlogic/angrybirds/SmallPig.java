package com.badlogic.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SmallPig extends Pig {
    public SmallPig(float x, float y) {
        super(new Texture(Gdx.files.internal("gameObjects/pigs/smallPig.png")), x, y);
    }
}
