package com.jimp.tanksgame.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jimp.tanksgame.TanksGame;
import com.jimp.tanksgame.logic.GameSettingsConfigurator;

import java.io.File;

public class SettingsScreen implements Screen {

    private final TanksGame myGame;
    private final Skin uiSkin;
    private final Stage uiStage;
    private final TextField textField;
    private final TextureRegion background;

    public SettingsScreen(final TanksGame game) {
        this.myGame = game;
        uiSkin = new Skin(Gdx.files.internal("skin/skin.json"));
        uiStage = new Stage();
        Table uiTable = new Table();
        uiTable.setFillParent(true);

        uiStage.addActor(uiTable);

        Label title = new Label("Enter absolute path to config", uiSkin);
        TextButton back = new TextButton("Back to the menu", uiSkin);
        TextButton confirm = new TextButton("OK", uiSkin);
        textField = new TextField("path", uiSkin);
        uiTable.add(title).center();
        uiTable.row().width(200);
        uiTable.add(textField).height(50).width(150);
        uiTable.add(confirm).height(50).width(50);
        uiTable.row();
        uiTable.add(back).height(100).width(200);
        uiTable.row();

        uiStage.addActor(uiTable);
        Gdx.input.setInputProcessor(uiStage);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                myGame.setScreen(new MainMenuScreen(myGame));
                dispose();
            }
        });
        confirm.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String path = textField.getText();
                File file = new File(path);
                if (file.isFile()) {
                    game.setMyConfigurator(new GameSettingsConfigurator(path));
                }
            }
        });
        background = Resources.getInstance().getBackground();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        myGame.getMyBatch().begin();
        myGame.getMyBatch().draw(background, 0, 0, ScreenProperties.WIDTH, ScreenProperties.HEIGHT);
        myGame.getMyBatch().end();

        uiStage.act(Gdx.graphics.getDeltaTime());
        uiStage.draw();

    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

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
        uiSkin.dispose();
        uiStage.dispose();
    }
}
