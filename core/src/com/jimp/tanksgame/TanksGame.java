package com.jimp.tanksgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jimp.tanksgame.graphics.MainMenuScreen;
import com.jimp.tanksgame.logic.utils.GameSettingsConfigurator;


public class TanksGame extends Game {
    private Batch myBatch;
    private BitmapFont myFont;
    private GameSettingsConfigurator myConfigurator;
    private GameMode mode;

    public void create() {
        myBatch = new SpriteBatch();
        myFont = new BitmapFont();
        myFont.getData().setScale(2f);
        myConfigurator = new GameSettingsConfigurator();
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

    public GameSettingsConfigurator getMyConfigurator() {
        return myConfigurator;
    }

    public void setMyConfigurator(GameSettingsConfigurator myConfigurator) {
        this.myConfigurator = myConfigurator;
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