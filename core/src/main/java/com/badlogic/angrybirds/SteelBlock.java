package com.badlogic.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SteelBlock extends Block {
    public SteelBlock(float x, float y) {
        super(new Texture(Gdx.files.internal("gameObjects/blocks/steelBlock.png")), x, y);
    }
}
