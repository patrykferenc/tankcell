package com.jimp.tanksgame.graphics.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jimp.tanksgame.TanksGame;
import com.jimp.tanksgame.graphics.screens.MySettingsScreen;

public class GraphicSettings extends MySettingsScreen {

    public GraphicSettings(final TanksGame game) {
        super(game);
        setupUI();
    }

    @Override
    public void setupUI() {
        Table uiTable = new Table();
        uiTable.setFillParent(true);

        TextButton back = new TextButton("Back to settings", getUiSkin());

        uiTable.row().width(200);
        uiTable.add(back).height(100).width(200).padBottom(10);

        getUiStage().addActor(uiTable);
        Gdx.input.setInputProcessor(getUiStage());
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().setScreen(new SettingsScreen(getMyGame()));
                dispose();
            }
        });
    }
}
