package com.badlogic.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Catapult extends GameObject {
    public Catapult(float x, float y) {
        super(new Texture(Gdx.files.internal("gameObjects/catapult.png")), x, y);
    }
}
