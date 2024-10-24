package com.badlogic.angrybirds;

import com.badlogic.gdx.graphics.Texture;

public class Block extends GameObject {
    private float orientation;
    public Block(Texture texture, float x, float y, float orientation) {
        super(texture, x, y);
        this.orientation = orientation;
    }

    public float getOrientation() {
        return orientation;
    }
}
