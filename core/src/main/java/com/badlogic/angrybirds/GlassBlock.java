package com.badlogic.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GlassBlock extends Block {
    public GlassBlock(float x, float y) {
        super(new Texture(Gdx.files.internal("gameObjects/blocks/glassBlock.png")), x, y);
    }
}
