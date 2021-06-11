package com.jimp.tanksgame.logic;

public class GameTimer {

    private final float delay;
    private float timeSinceLastEvent;

    public GameTimer(float delay) {
        this.delay = delay;
        this.timeSinceLastEvent = 0f;
    }

    public boolean update(float deltaTime) {
        timeSinceLastEvent += deltaTime;
        if (timeSinceLastEvent >= delay) {
            timeSinceLastEvent = 0f;
            return true;
        } else
            return false;
    }

    public float getRemainingTime() {
        return delay - timeSinceLastEvent;
    }

    public void reset() {
        timeSinceLastEvent = delay;
    }

}
