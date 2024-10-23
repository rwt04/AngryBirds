package com.badlogic.angrybirds;

import com.badlogic.gdx.graphics.Texture;

// class for game objects like birds, pigs, blocks, etc.
public class GameObject {
    private Texture texture;
    private float x, y;

    public GameObject(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
