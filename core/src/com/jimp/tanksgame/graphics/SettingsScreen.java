package com.jimp.tanksgame.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jimp.tanksgame.TanksGame;

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

        Label title = new Label("Choose settings you want to use", getUiSkin());
        TextButton back = new TextButton("Back to the menu", getUiSkin());
        uiTable.add(title).center();
        uiTable.row().width(200);
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
