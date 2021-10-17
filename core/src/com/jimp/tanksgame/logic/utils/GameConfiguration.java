package com.jimp.tanksgame.logic.utils;

import com.badlogic.gdx.Input;
import com.jimp.tanksgame.graphics.ScreenProperties;

public final class GameConfiguration {
    public static final int GAME_BOARD_WIDTH = 1600;
    public static final int GAME_BOARD_HEIGHT = 900;
    public static final float GAME_BOARD_LEFT_EDGE = (ScreenProperties.WIDTH - GAME_BOARD_WIDTH) / 2.0f;
    public static final float GAME_BOARD_RIGHT_EDGE = GAME_BOARD_LEFT_EDGE + GAME_BOARD_WIDTH;
    public static final float GAME_BOARD_LOWER_EDGE = (ScreenProperties.HEIGHT - GAME_BOARD_HEIGHT) / 2.0f;
    public static final float GAME_BOARD_UPPER_EDGE = GAME_BOARD_LOWER_EDGE + GAME_BOARD_HEIGHT;
    public static final float PLAYER_SPACE = 150f;

    public static final float BOMB_SIZE = 80f;
    public static final float BOMB_X = GAME_BOARD_LEFT_EDGE + (GAME_BOARD_WIDTH / 2f) - (BOMB_SIZE / 2f);
    public static final float BOMB_Y = GAME_BOARD_LOWER_EDGE;

    public static final float TURRET_LENGTH = 64f;
    public static final int LEFT_PLAYER_UP = Input.Keys.W;
    public static final int LEFT_PLAYER_DOWN = Input.Keys.S;
    public static final int LEFT_TURRET_UP = Input.Keys.A;
    public static final int LEFT_TURRET_DOWN = Input.Keys.D;
    public static final int LEFT_PLAYER_SHOOT = Input.Keys.SPACE;

    public static final int RIGHT_PLAYER_UP = Input.Keys.UP;
    public static final int RIGHT_PLAYER_DOWN = Input.Keys.DOWN;
    public static final int RIGHT_TURRET_UP = Input.Keys.LEFT;
    public static final int RIGHT_TURRET_DOWN = Input.Keys.RIGHT;
    public static final int RIGHT_PLAYER_SHOOT = Input.Keys.NUMPAD_0;

    public static final int MIN_CELLS_IN_COLONY = 1;
    public static final int MAX_CELLS_IN_COLONY = 5;

    private GameConfiguration() {
    }
}
