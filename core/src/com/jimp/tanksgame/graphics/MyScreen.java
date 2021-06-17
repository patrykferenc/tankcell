package com.jimp.tanksgame.graphics;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jimp.tanksgame.TanksGame;

public abstract class MyScreen implements Screen {

    private final TanksGame myGame;
    private final Stage uiStage;
    private final Skin uiSkin;
    private final TextureRegion background;

    protected MyScreen(final TanksGame game) {
        myGame = game;
        uiStage = new Stage();
        uiSkin = Resources.getInstance().getUiSkin();
        background = Resources.getInstance().getBackground();
    }

    public abstract void setupUI();

    public void disposeScreen() {
        uiStage.dispose();
    }

    public TanksGame getMyGame() {
        return myGame;
    }

    public Stage getUiStage() {
        return uiStage;
    }

    public Skin getUiSkin() {
        return uiSkin;
    }

    public TextureRegion getBackground() {
        return background;
    }
}
