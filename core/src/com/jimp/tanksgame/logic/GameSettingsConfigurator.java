package com.jimp.tanksgame.logic;

public class GameSettingsConfigurator {

    private final float bulletVelocity;
    private final float deltaBulletVelocity;
    private final float cellVelocity;
    private final float deltaCellVelocity;
    private final float bulletRadius;
    private final float deltaBulletRadius;
    private final float cellEdge;
    private final float deltaCellEdge;
    private final float timeToDecreaseSizeAndSpeed;
    private final float timeToIncreaseCellValues;
    private final float timeOfPlay;
    private final int maxBullets;

    //those are default values (for now)
    public GameSettingsConfigurator() {
        bulletVelocity = 800f;
        deltaBulletVelocity = 50f;
        cellVelocity = 80f;
        deltaCellVelocity = 2f;
        bulletRadius = 20f;
        deltaBulletRadius = 1f;
        cellEdge = 100f;
        deltaCellEdge = 4f;
        timeToDecreaseSizeAndSpeed = 10f;
        timeToIncreaseCellValues = 5f;
        timeOfPlay = 180f;
        maxBullets = 10;
    }

    public float getBulletVelocity() {
        return bulletVelocity;
    }

    public float getDeltaBulletVelocity() {
        return deltaBulletVelocity;
    }

    public float getCellVelocity() {
        return cellVelocity;
    }

    public float getDeltaCellVelocity() {
        return deltaCellVelocity;
    }

    public float getBulletRadius() {
        return bulletRadius;
    }

    public float getDeltaBulletRadius() {
        return deltaBulletRadius;
    }

    public float getCellEdge() {
        return cellEdge;
    }

    public float getDeltaCellEdge() {
        return deltaCellEdge;
    }

    public float getTimeToDecreaseSizeAndSpeed() {
        return timeToDecreaseSizeAndSpeed;
    }

    public float getTimeToIncreaseCellValues() {
        return timeToIncreaseCellValues;
    }

    public float getTimeOfPlay() {
        return timeOfPlay;
    }

    public int getMaxBullets() {
        return maxBullets;
    }
}
