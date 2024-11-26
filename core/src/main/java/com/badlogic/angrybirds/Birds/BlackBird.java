package com.badlogic.angrybirds.Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BlackBird extends Bird {
    public BlackBird (float x, float y){
        super(new Texture(Gdx.files.internal("gameObjects/birds/blackBird.png")), x, y);
        setDamage(80);
    }
}
