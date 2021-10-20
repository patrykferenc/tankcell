package com.jimp.tanksgame.graphics;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.jimp.tanksgame.TanksGame;

public class GameSettings extends MySettingsScreen {

    public GameSettings(final TanksGame game) {
        super(game);
        setupUI();
    }

    @Override
    public void setupUI() {
        Table uiTable = new Table();
        uiTable.setFillParent(true);
        getUiStage().addActor(uiTable);
    }
}
