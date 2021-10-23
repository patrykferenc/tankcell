package com.jimp.tanksgame.graphics.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jimp.tanksgame.TanksGame;
import com.jimp.tanksgame.graphics.Resources;
import com.jimp.tanksgame.graphics.ScreenProperties;
import com.jimp.tanksgame.graphics.screens.menu.MainMenuScreen;
import com.jimp.tanksgame.logic.GameBoard.GameEndState;

import static com.jimp.tanksgame.graphics.ScreenProperties.HEIGHT;
import static com.jimp.tanksgame.graphics.ScreenProperties.WIDTH;

public class EndGameScreen implements Screen {

    private final TanksGame myGame;
    private final OrthographicCamera myCamera;

    private final Texture messageBoxTexture;
    private final TextureRegion background;

    private final GameEndState endState;

    public EndGameScreen(final TanksGame game, GameEndState endState) {
        this.myGame = game;
        this.endState = endState;
        messageBoxTexture = new Texture(Gdx.files.internal("ramka.png"));

        myCamera = new OrthographicCamera();
        myCamera.setToOrtho(false, WIDTH, HEIGHT);
        background = Resources.getInstance().getBackground();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        myCamera.update();
        myGame.getMyBatch().setProjectionMatrix(myCamera.combined);

        myGame.getMyBatch().begin();
        myGame.getMyBatch().draw(background, 0, 0, ScreenProperties.WIDTH, ScreenProperties.HEIGHT);
        switch (endState) {
            case LEFT_WON_TIME:
                drawMessageBox("PLAYER LEFT WINS!", 640, 360);
                break;
            case RIGHT_WON_TIME:
                drawMessageBox("PLAYER RIGHT WINS!", 640, 360);
                break;
            case LEFT_WON_BOMB:
                drawMessageBox("PLAYER RIGHT LOST!", 640, 360);
                break;
            case RIGHT_WON_BOMB:
                drawMessageBox("PLAYER LEFT LOST!", 640, 360);
                break;
            case DRAW:
                drawMessageBox("IT'S A DRAW!", 640, 360);
                break;
            default:
                drawMessageBox("Illegal State To Show!", 640, 360);
                break;
        }
        myGame.getMyFont().draw(myGame.getMyBatch(), "Press ESCAPE to return to the main menu", 700, 500);
        myGame.getMyBatch().end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            myGame.setScreen(new MainMenuScreen(myGame));
            dispose();
        }
    }

    private void drawMessageBox(String message, float x, float y) {
        Sprite myMessageBox = new Sprite(messageBoxTexture);
        myMessageBox.setPosition(x, y);
        myMessageBox.setSize(640, 360);
        myMessageBox.draw(myGame.getMyBatch());
        myGame.getMyFont().draw(myGame.getMyBatch(), message, x + myMessageBox.getWidth() / 3, y + myMessageBox.getHeight() - 60);
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

    }
}
