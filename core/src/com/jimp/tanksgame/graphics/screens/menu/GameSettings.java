package com.jimp.tanksgame.graphics.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
        uiTable.debug(); //just for debug purposes right now

        Preferences gamePrefs = getMyGame().getMyConfig().getGameSettings();

        // Choose AI difficulty
        /*
            TBD
         */
        // Presets
        Label presetsLabel = new Label("Choose level difficulty", getUiSkin());
        TextButton easyLevel = new TextButton("Easy", getUiSkin());
        TextButton normalLevel = new TextButton("Normal", getUiSkin());
        TextButton hardLevel = new TextButton("Hard", getUiSkin());
        // Sliders and labels for detailed settings
        Slider bulletSpeedSlider = new Slider(500f, 2500f, 100f, false, getUiSkin());
        bulletSpeedSlider.setValue(gamePrefs.getFloat("bulletVelocity"));
        Label bulletSpeedLabel = new Label("Bullet speed", getUiSkin());
        Label bulletSpeedValue = new Label((int) gamePrefs.getFloat("bulletVelocity") + "", getUiSkin());

        Slider bulletRadiusSlider = new Slider(6f, 36f, 2f, false, getUiSkin());
        bulletRadiusSlider.setValue(gamePrefs.getFloat("bulletRadius"));
        Label bulletRadiusLabel = new Label("Bullet radius", getUiSkin());
        Label bulletRadiusValue = new Label((int) gamePrefs.getFloat("bulletRadius") + "", getUiSkin());

        Slider cellVelocitySlider = new Slider(20f, 200f, 20f, false, getUiSkin());
        cellVelocitySlider.setValue(gamePrefs.getFloat("cellVelocity"));
        Label cellVelocityLabel = new Label("Cell speed", getUiSkin());
        Label cellVelocityValue = new Label((int) gamePrefs.getFloat("cellVelocity") + "", getUiSkin());

        Slider cellEdgeSlider = new Slider(50f, 200f, 10f, false, getUiSkin());
        cellEdgeSlider.setValue(gamePrefs.getFloat("cellEdge"));
        Label cellEdgeLabel = new Label("Cell size", getUiSkin());
        Label cellEdgeValue = new Label((int) gamePrefs.getFloat("cellEdge") + "", getUiSkin());

        Slider timeOfPlaySlider = new Slider(30f, 180f, 30f, false, getUiSkin());
        timeOfPlaySlider.setValue(gamePrefs.getFloat("timeOfPlay"));
        Label timeOfPlayLabel = new Label("Time of play", getUiSkin());
        Label timeOfPlayValue = new Label((int) (gamePrefs.getFloat("timeOfPlay") / 60) + ":" + (int) (gamePrefs.getFloat("timeOfPlay") % 60), getUiSkin());

        // Return to menu button
        TextButton back = new TextButton("Back to settings", getUiSkin());

        uiTable.add(presetsLabel).pad(10).colspan(3);
        uiTable.row();
        uiTable.add(easyLevel);
        uiTable.add(normalLevel);
        uiTable.add(hardLevel);
        uiTable.row();
        uiTable.add(bulletSpeedLabel).pad(10);
        uiTable.add(bulletSpeedSlider).pad(10);
        uiTable.add(bulletSpeedValue).pad(10);
        uiTable.row();
        uiTable.add(bulletRadiusLabel).pad(10);
        uiTable.add(bulletRadiusSlider).pad(10);
        uiTable.add(bulletRadiusValue).pad(10);
        uiTable.row();
        uiTable.add(cellVelocityLabel).pad(10);
        uiTable.add(cellVelocitySlider).pad(10);
        uiTable.add(cellVelocityValue).pad(10);
        uiTable.row();
        uiTable.add(cellEdgeLabel).pad(10);
        uiTable.add(cellEdgeSlider).pad(10);
        uiTable.add(cellEdgeValue).pad(10);
        uiTable.row();
        uiTable.add(timeOfPlayLabel).pad(10);
        uiTable.add(timeOfPlaySlider).pad(10);
        uiTable.add(timeOfPlayValue).pad(10);
        uiTable.row();
        uiTable.add(back).pad(10).colspan(3).width(120).height(40);

        getUiStage().addActor(uiTable);
        Gdx.input.setInputProcessor(getUiStage());

        bulletSpeedSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().getMyConfig().setBulletVelocity(bulletSpeedSlider.getValue());
                bulletSpeedValue.setText((int) gamePrefs.getFloat("bulletVelocity") + "");
            }
        });
        bulletRadiusSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().getMyConfig().setBulletRadius(bulletRadiusSlider.getValue());
                bulletRadiusValue.setText((int) gamePrefs.getFloat("bulletRadius") + "");
            }
        });
        cellVelocitySlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().getMyConfig().setCellVelocity(cellVelocitySlider.getValue());
                cellVelocityValue.setText((int) gamePrefs.getFloat("cellVelocity") + "");
            }
        });
        cellEdgeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().getMyConfig().setCellSize(cellEdgeSlider.getValue());
                cellEdgeValue.setText((int) gamePrefs.getFloat("cellEdge") + "");
            }
        });
        timeOfPlaySlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().getMyConfig().setTimeOfPlay(timeOfPlaySlider.getValue());
                timeOfPlayValue.setText((int) (gamePrefs.getFloat("timeOfPlay") / 60) + ":" + (int) (gamePrefs.getFloat("timeOfPlay") % 60));
            }
        });
        easyLevel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().getMyConfig().setGameBoardEasy();

                bulletSpeedValue.setText((int) gamePrefs.getFloat("bulletVelocity") + "");
                bulletSpeedSlider.setValue(gamePrefs.getFloat("bulletVelocity"));
                bulletRadiusValue.setText((int) gamePrefs.getFloat("bulletRadius") + "");
                bulletRadiusSlider.setValue(gamePrefs.getFloat("bulletRadius"));
                cellEdgeValue.setText((int) gamePrefs.getFloat("cellEdge") + "");
                cellEdgeSlider.setValue(gamePrefs.getFloat("cellEdge"));
                cellVelocitySlider.setValue(gamePrefs.getFloat("cellVelocity"));
                cellVelocityValue.setText((int) gamePrefs.getFloat("cellVelocity") + "");
            }
        });
        normalLevel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().getMyConfig().setGameBoardNormal();

                bulletSpeedValue.setText((int) gamePrefs.getFloat("bulletVelocity") + "");
                bulletSpeedSlider.setValue(gamePrefs.getFloat("bulletVelocity"));
                bulletRadiusValue.setText((int) gamePrefs.getFloat("bulletRadius") + "");
                bulletRadiusSlider.setValue(gamePrefs.getFloat("bulletRadius"));
                cellEdgeValue.setText((int) gamePrefs.getFloat("cellEdge") + "");
                cellEdgeSlider.setValue(gamePrefs.getFloat("cellEdge"));
                cellVelocitySlider.setValue(gamePrefs.getFloat("cellVelocity"));
                cellVelocityValue.setText((int) gamePrefs.getFloat("cellVelocity") + "");
            }
        });
        hardLevel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().getMyConfig().setGameBoardHard();

                bulletSpeedValue.setText((int) gamePrefs.getFloat("bulletVelocity") + "");
                bulletSpeedSlider.setValue(gamePrefs.getFloat("bulletVelocity"));
                bulletRadiusValue.setText((int) gamePrefs.getFloat("bulletRadius") + "");
                bulletRadiusSlider.setValue(gamePrefs.getFloat("bulletRadius"));
                cellEdgeValue.setText((int) gamePrefs.getFloat("cellEdge") + "");
                cellEdgeSlider.setValue(gamePrefs.getFloat("cellEdge"));
                cellVelocitySlider.setValue(gamePrefs.getFloat("cellVelocity"));
                cellVelocityValue.setText((int) gamePrefs.getFloat("cellVelocity") + "");
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
