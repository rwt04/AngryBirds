package com.badlogic.angrybirds.Blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SteelBlock extends Block {
    private static final int MAX_HP = 80;
    private static final float DENSITY = 5.8f;

    public SteelBlock(float x, float y, String shapeType, float orientation) {
        super(getTexture(shapeType), x, y, orientation, MAX_HP, DENSITY);
        setSquare(shapeType.equals("square"));
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
