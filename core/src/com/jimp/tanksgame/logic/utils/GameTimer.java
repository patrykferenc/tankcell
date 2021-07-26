package com.jimp.tanksgame.logic.utils;

public class GameTimer {

    private final float delay;
    private float timeSinceLastEvent;
    private boolean justPassed;

    public GameTimer(float delay) {
        this.delay = delay;
        this.timeSinceLastEvent = 0f;
        justPassed = true;
    }

    public boolean update(float deltaTime) {
        timeSinceLastEvent += deltaTime;
        if (timeSinceLastEvent >= delay) {
            timeSinceLastEvent = 0f;
            justPassed = true;
            return true;
        } else {
            justPassed = false;
            return false;
        }
    }

    public float getRemainingTime() {
        return delay - timeSinceLastEvent;
    }

    public void reset() {
        timeSinceLastEvent = delay;
    }

    public boolean isJustPassed() {
        return justPassed;
    }
}
