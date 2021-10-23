package com.jimp.tanksgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jimp.tanksgame.graphics.screens.menu.MainMenuScreen;
import com.jimp.tanksgame.logic.utils.GameConfiguration;


public class TanksGame extends Game {
    private Batch myBatch;
    private BitmapFont myFont;
    private GameConfiguration myConfig;
    private GameMode mode;

    public void create() {
        myBatch = new SpriteBatch();
        myFont = new BitmapFont();
        myFont.getData().setScale(2f);
        myConfig = new GameConfiguration();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        myBatch.dispose();
        myFont.dispose();
    }

    public Batch getMyBatch() {
        return myBatch;
    }

    public BitmapFont getMyFont() {
        return myFont;
    }

    public GameConfiguration getMyConfig() {
        return myConfig;
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public enum GameMode {
        SINGLEPLAYER,
        MULTIPLAYER
    }
}