package com.jimp.tanksgame.logic.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.jimp.tanksgame.graphics.ScreenProperties;

public final class GameConfiguration {
    public static final int GAME_BOARD_WIDTH = 1600;
    public static final int GAME_BOARD_HEIGHT = 900;
    public static final float GAME_BOARD_LEFT_EDGE = (ScreenProperties.WIDTH - GAME_BOARD_WIDTH) / 2.0f;
    public static final float GAME_BOARD_RIGHT_EDGE = GAME_BOARD_LEFT_EDGE + GAME_BOARD_WIDTH;
    public static final float BOMB_SIZE = 80f;
    public static final float BOMB_X = GAME_BOARD_LEFT_EDGE + (GAME_BOARD_WIDTH / 2f) - (BOMB_SIZE / 2f);
    public static final float GAME_BOARD_LOWER_EDGE = (ScreenProperties.HEIGHT - GAME_BOARD_HEIGHT) / 2.0f;
    public static final float GAME_BOARD_UPPER_EDGE = GAME_BOARD_LOWER_EDGE + GAME_BOARD_HEIGHT;
    public static final float BOMB_Y = GAME_BOARD_LOWER_EDGE;
    public static final float PLAYER_SPACE = 150f;
    public static final float TURRET_LENGTH = 64f;

    public static final int MIN_CELLS_IN_COLONY = 1;
    public static final int MAX_CELLS_IN_COLONY = 5;

    private final Preferences gameSettings = Gdx.app.getPreferences("TancellGameSettings");
    private final Preferences controls = Gdx.app.getPreferences("TankcellGameControls");

    public GameConfiguration() {
        setDefaultGameSettings();
        setDefaultKeys();
    }

    private void setDefaultGameSettings() {
        gameSettings.clear(); //that is just for dev purposes right now
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
            gameSettings.putFloat("timeOfPlay", 180f);
        if (!gameSettings.contains("maxBullets"))
            gameSettings.putInteger("maxBullets", 10);

        gameSettings.flush();
    }

    private void setDefaultKeys() {
        controls.clear(); //that is just for dev purposes right now

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
