package com.jimp.tanksgame.logic.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Rectangle;
import com.jimp.tanksgame.graphics.ScreenProperties;

public final class GameConfiguration {
    public static final Rectangle GAME_BOARD = new Rectangle(
            (ScreenProperties.WIDTH - 1600) / 2.0f,
            (ScreenProperties.HEIGHT - 900) / 2.0f,
            1600,
            900
    );
    public static final float BOMB_SIZE = 80f;
    public static final float BOMB_X = GAME_BOARD.getX() + (GAME_BOARD.getWidth() / 2f) - (BOMB_SIZE / 2f);
    public static final float BOMB_Y = GAME_BOARD.getY();
    public static final float PLAYER_SPACE = 150f;
    public static final float TURRET_LENGTH = 64f;

    public static final int MIN_CELLS_IN_COLONY = 1;
    public static final int MAX_CELLS_IN_COLONY = 5;

    private final Preferences gameSettings = Gdx.app.getPreferences("TancellGameSettings");
    private final Preferences controls = Gdx.app.getPreferences("TankcellGameControls");

    public GameConfiguration() {
        restoreDefaultGameSettings(); //that is just for dev purposes right now
        setDefaultGameSettings();
        restoreDefaultKeys(); //that is just for dev purposes right now
        setDefaultKeys();
    }

    private void setDefaultGameSettings() {
        if (!gameSettings.contains("bulletVelocity"))
            gameSettings.putFloat("bulletVelocity", 1200f);
        if (!gameSettings.contains("deltaBulletVelocity"))
            gameSettings.putFloat("deltaBulletVelocity", 50f);
        if (!gameSettings.contains("cellVelocity"))
            gameSettings.putFloat("cellVelocity", 80f);
        if (!gameSettings.contains("deltaCellVelocity"))
            gameSettings.putFloat("deltaCellVelocity", 2f);
        if (!gameSettings.contains("bulletRadius"))
            gameSettings.putFloat("bulletRadius", 20f);
        if (!gameSettings.contains("deltaBulletRadius"))
            gameSettings.putFloat("deltaBulletRadius", 1f);
        if (!gameSettings.contains("cellEdge"))
            gameSettings.putFloat("cellEdge", 100f);
        if (!gameSettings.contains("deltaCellEdge"))
            gameSettings.putFloat("deltaCellEdge", 4f);
        if (!gameSettings.contains("timeToDecreaseSizeAndSpeed"))
            gameSettings.putFloat("timeToDecreaseSizeAndSpeed", 10f);
        if (!gameSettings.contains("timeToIncreaseCellValues"))
            gameSettings.putFloat("timeToIncreaseCellValues", 5f);
        if (!gameSettings.contains("timeOfPlay"))
            gameSettings.putFloat("timeOfPlay", 150f);
        if (!gameSettings.contains("maxBullets"))
            gameSettings.putInteger("maxBullets", 10);

        gameSettings.flush();
    }

    public void setBulletVelocity(float value) {
        gameSettings.putFloat("bulletVelocity", value);
        gameSettings.flush();
    }

    public void setBulletRadius(float value) {
        gameSettings.putFloat("bulletRadius", value);
        gameSettings.flush();
    }

    public void setCellVelocity(float value) {
        gameSettings.putFloat("cellVelocity", value);
        gameSettings.flush();
    }

    public void setCellSize(float value) {
        gameSettings.putFloat("cellEdge", value);
        gameSettings.flush();
    }

    public void setTimeOfPlay(float value) {
        gameSettings.putFloat("timeOfPlay", value);
        gameSettings.flush();
    }

    public void setGameBoardEasy() {
        gameSettings.putFloat("bulletVelocity", 1600f);
        gameSettings.putFloat("deltaBulletVelocity", 25f);
        gameSettings.putFloat("cellVelocity", 80f);
        gameSettings.putFloat("deltaCellVelocity", 2f);
        gameSettings.putFloat("bulletRadius", 26f);
        gameSettings.putFloat("deltaBulletRadius", 1f);
        gameSettings.putFloat("cellEdge", 100f);
        gameSettings.putFloat("deltaCellEdge", 4f);
        gameSettings.putFloat("timeToDecreaseSizeAndSpeed", 12f);
        gameSettings.putFloat("timeToIncreaseCellValues", 6f);
        gameSettings.putFloat("timeOfPlay", 150f);
        gameSettings.putInteger("maxBullets", 12);

        gameSettings.flush();
    }

    public void setGameBoardNormal() {
        restoreDefaultGameSettings();
    }

    public void setGameBoardHard() {
        gameSettings.putFloat("bulletVelocity", 800f);
        gameSettings.putFloat("deltaBulletVelocity", 50f);
        gameSettings.putFloat("cellVelocity", 100f);
        gameSettings.putFloat("deltaCellVelocity", 3f);
        gameSettings.putFloat("bulletRadius", 18f);
        gameSettings.putFloat("deltaBulletRadius", 1f);
        gameSettings.putFloat("cellEdge", 78f);
        gameSettings.putFloat("deltaCellEdge", 4f);
        gameSettings.putFloat("timeToDecreaseSizeAndSpeed", 9f);
        gameSettings.putFloat("timeToIncreaseCellValues", 4.5f);
        gameSettings.putFloat("timeOfPlay", 150f);
        gameSettings.putInteger("maxBullets", 8);

        gameSettings.flush();    }

    public void restoreDefaultKeys() {
        controls.clear();
        setDefaultKeys();
    }

    public void restoreDefaultGameSettings() {
        gameSettings.clear();
        setDefaultGameSettings();
    }

    private void setDefaultKeys() {
        // Left player contols.
        if (!controls.contains("LEFT_PLAYER_UP"))
            controls.putInteger("LEFT_PLAYER_UP", Input.Keys.W);
        if (!controls.contains("LEFT_PLAYER_DOWN"))
            controls.putInteger("LEFT_PLAYER_DOWN", Input.Keys.S);
        if (!controls.contains("LEFT_TURRET_UP"))
            controls.putInteger("LEFT_TURRET_UP", Input.Keys.A);
        if (!controls.contains("LEFT_TURRET_DOWN"))
            controls.putInteger("LEFT_TURRET_DOWN", Input.Keys.D);
        if (!controls.contains("LEFT_PLAYER_SHOOT"))
            controls.putInteger("LEFT_PLAYER_SHOOT", Input.Keys.SPACE);

        // Right player controls.
        if (!controls.contains("RIGHT_PLAYER_UP"))
            controls.putInteger("RIGHT_PLAYER_UP", Input.Keys.UP);
        if (!controls.contains("RIGHT_PLAYER_DOWN"))
            controls.putInteger("RIGHT_PLAYER_DOWN", Input.Keys.DOWN);
        if (!controls.contains("RIGHT_TURRET_UP"))
            controls.putInteger("RIGHT_TURRET_UP", Input.Keys.RIGHT);
        if (!controls.contains("RIGHT_TURRET_DOWN"))
            controls.putInteger("RIGHT_TURRET_DOWN", Input.Keys.LEFT);
        if (!controls.contains("RIGHT_PLAYER_SHOOT"))
            controls.putInteger("RIGHT_PLAYER_SHOOT", Input.Keys.SHIFT_RIGHT);

        controls.flush();
    }

    public Preferences getControls() {
        return controls;
    }

    public Preferences getGameSettings() {
        return gameSettings;
    }

    public enum Difficulty {
        EASY, NORMAL, HARD, ABSOLUTE_MADMAN
    }
}
