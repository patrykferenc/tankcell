package com.jimp.tanksgame.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jimp.tanksgame.TanksGame;
import com.jimp.tanksgame.logic.GameSettingsConfigurator;

import java.io.File;

public class SettingsScreen extends MyScreen {

    public SettingsScreen(final TanksGame game) {
        super(game);
        setupUI();
    }

    @Override
    public void setupUI() {
        Table uiTable = new Table();
        uiTable.setFillParent(true);
        getUiStage().addActor(uiTable);

        Label title = new Label("Enter absolute path to config", getUiSkin());
        TextButton back = new TextButton("Back to the menu", getUiSkin());
        TextButton confirm = new TextButton("OK", getUiSkin());
        TextField textField = new TextField("path", getUiSkin());
        uiTable.add(title).center();
        uiTable.row().width(200);
        uiTable.add(textField).height(50).width(150);
        uiTable.add(confirm).height(50).width(50);
        uiTable.row();
        uiTable.add(back).height(100).width(200);
        uiTable.row();

        getUiStage().addActor(uiTable);
        Gdx.input.setInputProcessor(getUiStage());
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().setScreen(new MainMenuScreen(getMyGame()));
                dispose();
            }
        });
        confirm.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String path = textField.getText();
                File file = new File(path);
                if (file.isFile()) {
                    getMyGame().setMyConfigurator(new GameSettingsConfigurator(path));
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getMyGame().getMyBatch().begin();
        getMyGame().getMyBatch().draw(getBackground(), 0, 0, ScreenProperties.WIDTH, ScreenProperties.HEIGHT);
        getMyGame().getMyBatch().end();

        getUiStage().act(Gdx.graphics.getDeltaTime());
        getUiStage().draw();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        getUiStage().getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        disposeScreen();
    }
}
