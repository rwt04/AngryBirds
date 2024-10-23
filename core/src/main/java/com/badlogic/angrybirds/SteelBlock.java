package com.badlogic.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SteelBlock extends Block {
    public SteelBlock(float x, float y, String shapeType) {
        super(getTexture(shapeType), x, y);
    }

    private static Texture getTexture(String shapeType) {
        switch (shapeType) {
            case "short":
                return new Texture(Gdx.files.internal("gameObjects/blocks/steel/steelShortLog.png"));
            case "medium":
                return new Texture(Gdx.files.internal("gameObjects/blocks/steel/steelMediumLog.png"));
            case "long":
                return new Texture(Gdx.files.internal("gameObjects/blocks/steel/steelLongLog.png"));
            case "square":
                return new Texture(Gdx.files.internal("gameObjects/blocks/steel/steelSquare.png"));
            default:
                throw new IllegalArgumentException("Unknown shape type: " + shapeType);
        }
    }
}
