package com.badlogic.angrybirds.Blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GlassBlock extends Block {
    private static final int MAX_HP = 40;
    private static final float DENSITY = 2.5f;

    public GlassBlock(float x, float y, String shapeType, float orientation) {
        super(getTexture(shapeType), x, y, orientation, MAX_HP, DENSITY);
        setSquare(shapeType.equals("square"));
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
