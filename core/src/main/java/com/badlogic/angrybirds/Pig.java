package com.badlogic.angrybirds;

import com.badlogic.gdx.graphics.Texture;

public class Pig extends GameObject {
    public Pig(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    public float getRadius() {
        return getTexture().getWidth() / 2;
    }
}
