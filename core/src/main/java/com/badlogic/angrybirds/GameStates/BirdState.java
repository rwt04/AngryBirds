package com.badlogic.angrybirds.GameStates;

public class BirdState {
    private float x, y;
    private float angle;
    private int damage;
    private float angularVelocity;
    private float inertia;
    private float linearVelocityX, linearVelocityY;
    private String texture;
    private boolean launched;
    private boolean isCurrent;
    private boolean isOnCatapult;

    public BirdState(float x, float y, float angle, int damage, float angularVelocity, float inertia, float linearVelocityX, float linearVelocityY, String texture, boolean launched, boolean isCurrent, boolean isOnCatapult) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.damage = damage;
        this.angularVelocity = angularVelocity;
        this.inertia = inertia;
        this.linearVelocityX = linearVelocityX;
        this.linearVelocityY = linearVelocityY;
        this.texture = texture;
        this.launched = launched;
        this.isCurrent = isCurrent;
        this.isOnCatapult = isOnCatapult;

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

    public int getDamage() {
        return damage;
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

    public boolean isLaunched() {
        return launched;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public boolean isOnCatapult() {
        return isOnCatapult;
    }
}
