package com.badlogic.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

// class for game objects like birds, pigs, blocks, etc.
public class GameObject {
    private Texture texture;
    private float x, y;// 1
//    public static final short CATEGORY_CATAPULT = 0x0002; // 2
//    public static final short CATEGORY_DEFAULT = 0x0004; // 4 (for other objects)

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
