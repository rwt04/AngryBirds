package com.badlogic.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GlassBlock extends Block {
    public GlassBlock(float x, float y, String shapeType, float orientation) {
        super(getTexture(shapeType), x, y, orientation);
    }

    private static Texture getTexture(String shapeType) {
        switch (shapeType) {
            case "short":
                return new Texture(Gdx.files.internal("gameObjects/blocks/glass/glassShortLog.png"));
            case "medium":
                return new Texture(Gdx.files.internal("gameObjects/blocks/glass/glassMediumLog.png"));
            case "long":
                return new Texture(Gdx.files.internal("gameObjects/blocks/glass/glassLongLog.png"));
            case "square":
                return new Texture(Gdx.files.internal("gameObjects/blocks/glass/glassSquare.png"));
            default:
                throw new IllegalArgumentException("Unknown shape type: " + shapeType);
        }
    }
}
