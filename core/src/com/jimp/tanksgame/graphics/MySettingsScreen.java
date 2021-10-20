package com.jimp.tanksgame.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.jimp.tanksgame.TanksGame;

public abstract class MySettingsScreen extends MyScreen {

    protected MySettingsScreen(TanksGame game) {
        super(game);
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
        //intended to be empty
    }

    @Override
    public void resize(int width, int height) {
        getUiStage().getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        //intended to be empty
    }

    @Override
    public void resume() {
        //intended to be empty
    }

    @Override
    public void hide() {
        //intended to be empty
    }

    @Override
    public void dispose() {
        disposeScreen();
    }
}
