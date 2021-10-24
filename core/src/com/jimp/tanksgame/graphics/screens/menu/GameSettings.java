package com.jimp.tanksgame.graphics.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jimp.tanksgame.TanksGame;
import com.jimp.tanksgame.graphics.screens.MySettingsScreen;

public class GameSettings extends MySettingsScreen {

    public GameSettings(final TanksGame game) {
        super(game);
        setupUI();
    }

    @Override
    public void setupUI() {
        Table uiTable = new Table();
        uiTable.setFillParent(true);

        Slider bulletSpeedSlider = new Slider(500f, 2500f, 100f, false, getUiSkin());
        bulletSpeedSlider.setValue(getMyGame().getMyConfig().getGameSettings().getFloat("bulletVelocity"));
        Label bulletSpeedLabel = new Label("Bullet speed", getUiSkin());
        Slider bulletRadiusSlider = new Slider(6f, 36f, 2f, false, getUiSkin());
        bulletRadiusSlider.setValue(getMyGame().getMyConfig().getGameSettings().getFloat("bulletRadius"));
        Label bulletRadiusLabel = new Label("Bullet radius", getUiSkin());
        Slider cellVelocitySlider = new Slider(20f, 200f, 20f, false, getUiSkin());
        cellVelocitySlider.setValue(getMyGame().getMyConfig().getGameSettings().getFloat("cellVelocity"));
        Label cellVelocityLabel = new Label("Cell speed", getUiSkin());
        Slider cellEdgeSlider = new Slider(50f, 200f, 10f, false, getUiSkin());
        cellEdgeSlider.setValue(getMyGame().getMyConfig().getGameSettings().getFloat("cellEdge"));
        Label cellEdgeLabel = new Label("Cell size", getUiSkin());


        TextButton back = new TextButton("Back to settings", getUiSkin());

        uiTable.row();
        uiTable.add(bulletSpeedLabel).padBottom(10);
        uiTable.add(bulletSpeedSlider).padBottom(10);
        uiTable.row();
        uiTable.add(cellVelocityLabel).padBottom(10);
        uiTable.add(cellVelocitySlider).padBottom(10);
        uiTable.row();
        uiTable.add(bulletRadiusLabel).padBottom(10);
        uiTable.add(bulletRadiusSlider).padBottom(10);
        uiTable.row();
        uiTable.add(cellEdgeLabel).padBottom(10);
        uiTable.add(cellEdgeSlider).padBottom(10);
        uiTable.row();
        uiTable.add(back).padBottom(10).colspan(2);

        getUiStage().addActor(uiTable);
        Gdx.input.setInputProcessor(getUiStage());

        bulletSpeedSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().getMyConfig().setBulletVelocity(bulletSpeedSlider.getValue());
            }
        });
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().setScreen(new SettingsScreen(getMyGame()));
                dispose();
            }
        });

        getUiStage().addActor(uiTable);
    }
}
