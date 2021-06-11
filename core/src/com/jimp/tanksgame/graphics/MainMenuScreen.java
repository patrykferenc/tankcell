package com.jimp.tanksgame.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jimp.tanksgame.TanksGame;

public class MainMenuScreen implements Screen {

    private final TanksGame myGame;

    private final Skin uiSkin;
    private final Stage uiStage;

    private final TextureRegion background;
    private final Sprite title;

    public MainMenuScreen(final TanksGame game) {
        this.myGame = game;

        uiSkin = new Skin(Gdx.files.internal("skin/skin.json"));
        background = Resources.getInstance().getBackground();
        title = new Sprite(Resources.getInstance().getTitle());
        title.setSize(640, 96);
        title.setCenter(ScreenProperties.WIDTH / 2f, 800f);

        uiStage = new Stage();
        Table uiTable = new Table();
        uiTable.setFillParent(true);

        uiStage.addActor(uiTable);

        TextButton newGame = new TextButton("New game", uiSkin);
        TextButton settings = new TextButton("Settings", uiSkin);
        TextButton quit = new TextButton("Quit", uiSkin);
        uiTable.add(newGame).height(100).width(200).center();
        uiTable.row();
        uiTable.add(settings).height(100).width(200).center();
        uiTable.row();
        uiTable.add(quit).height(100).width(200).center();

        uiStage.addActor(uiTable);
        Gdx.input.setInputProcessor(uiStage);

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                myGame.setScreen(new GameScreen(myGame));
                dispose();
            }
        });
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                myGame.setScreen(new SettingsScreen(myGame));
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

        myGame.getMyBatch().begin();
        myGame.getMyBatch().draw(background, 0, 0, ScreenProperties.WIDTH, ScreenProperties.HEIGHT);
        title.draw(myGame.getMyBatch());
        myGame.getMyBatch().end();

        uiStage.act(Gdx.graphics.getDeltaTime());
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
        uiStage.dispose();
        uiSkin.dispose();
    }

}
