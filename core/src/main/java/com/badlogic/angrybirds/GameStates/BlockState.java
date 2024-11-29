package com.badlogic.angrybirds.GameStates;

public class BlockState {
    private float x, y;
    private float angle;
    private int hp;
    private int maxHP;
    private float angularVelocity;
    private float inertia;
    private float linearVelocityX, linearVelocityY;
    private String texture;
    private float density;

    public BlockState(float x, float y, float angle, int hp, int maxHP, float angularVelocity, float inertia, float linearVelocityX, float linearVelocityY, String texture, float density) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.hp = hp;
        this.maxHP = maxHP;
        this.angularVelocity = angularVelocity;
        this.inertia = inertia;
        this.linearVelocityX = linearVelocityX;
        this.linearVelocityY = linearVelocityY;
        this.texture = texture;
        this.density = density;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public float getAngularVelocity() {
        return angularVelocity;
    }

    public float getInertia() {
        return inertia;
    }

    public float getLinearVelocityX() {
        return linearVelocityX;
    }

    public float getLinearVelocityY() {
        return linearVelocityY;
    }

    public String getTexture() {
        return texture;
    }

    public float getDensity() {
        return density;
    }
}
