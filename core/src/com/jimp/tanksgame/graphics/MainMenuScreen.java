package com.jimp.tanksgame.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jimp.tanksgame.TanksGame;

public class MainMenuScreen extends MyScreen {

    private final Sprite title;

    public MainMenuScreen(final TanksGame game) {
        super(game);
        setupUI();
        title = new Sprite(Resources.getInstance().getTitle());
        title.setSize(640, 96);
        title.setCenter(ScreenProperties.WIDTH / 2f, 800f);
    }

    @Override
    public void setupUI() {
        Table uiTable = new Table();
        uiTable.setFillParent(true);
        getUiStage().addActor(uiTable);

        TextButton newGame = new TextButton("New game", getUiSkin());
        TextButton settings = new TextButton("Settings", getUiSkin());
        TextButton quit = new TextButton("Quit", getUiSkin());
        uiTable.add(newGame).height(100).width(300).padBottom(10);
        uiTable.row();
        uiTable.add(settings).height(100).width(300).padBottom(10);
        uiTable.row();
        uiTable.add(quit).height(100).width(300).padBottom(10);
        uiTable.setDebug(true);

        getUiStage().addActor(uiTable);

        Gdx.input.setInputProcessor(getUiStage());
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().setScreen(new GameScreen(getMyGame()));
                dispose();
            }
        });
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getMyGame().setScreen(new SettingsScreen(getMyGame()));
                dispose();
            }
        });
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Resources.getInstance().dispose();
                dispose();
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getMyGame().getMyBatch().begin();
        getMyGame().getMyBatch().draw(getBackground(), 0, 0, ScreenProperties.WIDTH, ScreenProperties.HEIGHT);
        title.draw(getMyGame().getMyBatch());
        getMyGame().getMyBatch().end();

        getUiStage().act(Gdx.graphics.getDeltaTime());
        getUiStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        getUiStage().getViewport().update(width, height, true);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        disposeScreen();
    }

}
