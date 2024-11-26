package com.badlogic.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class WoodBlock extends Block {
    private static final int MAX_HP = 60;

    public WoodBlock(float x, float y, String shapeType, float orientation) {
        super(getTexture(shapeType), x, y, orientation, MAX_HP);
        setSquare(shapeType.equals("square"));
    }

    private static Texture getTexture(String shapeType) {
        switch (shapeType) {
            case "short":
                return new Texture(Gdx.files.internal("gameObjects/blocks/wood/woodShortLog.png"));
            case "medium":
                return new Texture(Gdx.files.internal("gameObjects/blocks/wood/woodMediumLog.png"));
            case "long":
                return new Texture(Gdx.files.internal("gameObjects/blocks/wood/woodLongLog.png"));
            case "square":
                return new Texture(Gdx.files.internal("gameObjects/blocks/wood/woodSquare.png"));
            default:
                throw new IllegalArgumentException("Unknown shape type: " + shapeType);
        }
    }
}
