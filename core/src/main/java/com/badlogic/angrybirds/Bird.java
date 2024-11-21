package com.badlogic.angrybirds;

import com.badlogic.gdx.graphics.Texture;

public class Bird extends GameObject {
    public Bird(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    public float getRadius() {
        return getTexture().getWidth() / 2;
    }
}
