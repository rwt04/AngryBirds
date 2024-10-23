package com.badlogic.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class RedBird extends Bird {
    public RedBird(float x, float y) {
        super(new Texture(Gdx.files.internal("gameObjects/birds/redBird.png")), x, y);
    }
}
