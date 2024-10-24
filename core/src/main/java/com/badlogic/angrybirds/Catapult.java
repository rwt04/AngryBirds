package com.badlogic.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Catapult extends GameObject {
    private TextureRegion textureRegion;

    public Catapult(float x, float y) {
        super(new Texture(Gdx.files.internal("gameObjects/catapult.png")), x, y);
        float scaleX = 0.6f;
        float scaleY = 0.6f;
        this.textureRegion = new TextureRegion(getTexture(), 0, 0, (int)(getTexture().getWidth() * scaleX), (int)(getTexture().getHeight() * scaleY));
    }

    public TextureRegion getScaledTexture() {
        return textureRegion;
    }
}
