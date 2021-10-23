package com.jimp.tanksgame.graphics.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jimp.tanksgame.TanksGame;
import com.jimp.tanksgame.graphics.screens.MySettingsScreen;

public class SettingsScreen extends MySettingsScreen {

    public SettingsScreen(final TanksGame game) {
        super(game);
        setupUI();
    }

    @Override
    public void setupUI() {
        Table uiTable = new Table();
        uiTable.setFillParent(true);

        //Label title = new Label("Choose settings you want to use", getUiSkin());
        TextButton game = new TextButton("Game settings", getUiSkin());
        TextButton controls = new TextButton("Key mapping", getUiSkin());
        TextButton graphics = new TextButton("Graphic settings", getUiSkin());
        TextButton back = new TextButton("Back to the menu", getUiSkin());

        //uiTable.add(title).center();
        uiTable.row().width(200);
        uiTable.add(game).height(100).width(200).padBottom(10);
        uiTable.row();
        uiTable.add(controls).height(100).width(200).padBottom(10);
        uiTable.row();
        uiTable.add(graphics).height(100).width(200).padBottom(10);
        uiTable.row();
        uiTable.add(back).height(100).width(200).padBottom(10);


        getUiStage().addActor(uiTable);
        Gdx.input.setInputProcessor(getUiStage());
        game.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().setScreen(new GameSettings(getMyGame()));
                dispose();
            }
        });
        controls.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().setScreen(new ControlsSettings(getMyGame()));
                dispose();
            }
        });
        graphics.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().setScreen(new GraphicSettings(getMyGame()));
                dispose();
            }
        });
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().setScreen(new MainMenuScreen(getMyGame()));
                dispose();
            }
        });
    }
}
