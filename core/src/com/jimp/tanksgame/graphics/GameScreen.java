package com.jimp.tanksgame.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jimp.tanksgame.TanksGame;
import com.jimp.tanksgame.logic.Drawable;
import com.jimp.tanksgame.logic.GameBoard;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;

import static com.jimp.tanksgame.graphics.ScreenProperties.HEIGHT;
import static com.jimp.tanksgame.graphics.ScreenProperties.WIDTH;
import static com.jimp.tanksgame.logic.GameBoard.GameEndState.*;
import static com.jimp.tanksgame.logic.utils.GameConfiguration.*;

class GameScreen extends MyScreen {

    private final OrthographicCamera myCamera;
    private final ShapeDrawer myShapeDrawer;
    private final GameBoard myGameBoard;

    private final List<Sprite> backgroundSprites;

    private boolean leftPlayerShot;
    private boolean rightPlayerShot;

    public GameScreen(final TanksGame game) {
        super(game);
        setupUI();

        TextureRegion whitePixelRegion = new TextureRegion(Resources.getInstance().getWhitePixelTexture());
        myShapeDrawer = new ShapeDrawer(getMyGame().getMyBatch(), whitePixelRegion);

        myCamera = new OrthographicCamera();
        myCamera.setToOrtho(false, WIDTH, HEIGHT);

        myGameBoard = new GameBoard(getMyGame().getMyConfigurator());

        backgroundSprites = new ArrayList<>((int) ((GAME_BOARD_WIDTH / PLAYER_SPACE) * (GAME_BOARD_HEIGHT / PLAYER_SPACE)));
        prepareBackground();

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int key) {
                if (key == LEFT_PLAYER_SHOOT) {
                    leftPlayerShot = true;
                }
                if (key == RIGHT_PLAYER_SHOOT) {
                    rightPlayerShot = true;
                }

                return false;
            }

            @Override
            public boolean keyUp(int key) {

                if (key == LEFT_PLAYER_SHOOT) {
                    leftPlayerShot = false;
                }
                if (key == RIGHT_PLAYER_SHOOT) {
                    rightPlayerShot = false;
                }

                return false;
            }
        });
    }

    @Override
    public void setupUI() {
        Table uiTable = new Table();
        uiTable.setFillParent(true);
        getUiStage().addActor(uiTable);


        uiTable.setDebug(true);
        getUiStage().addActor(uiTable);

        Gdx.input.setInputProcessor(getUiStage());
    }

    private void prepareBackground() {
        for (int j = 0; j < GAME_BOARD_WIDTH / PLAYER_SPACE; j++) {
            for (int i = 0; i < GAME_BOARD_HEIGHT / PLAYER_SPACE; i++) {
                Sprite backgroundTile = new Sprite(Resources.getInstance().getDirt());
                backgroundTile.setSize(PLAYER_SPACE, PLAYER_SPACE);
                backgroundTile.setPosition(GAME_BOARD_LEFT_EDGE + PLAYER_SPACE + j * PLAYER_SPACE, GAME_BOARD_LOWER_EDGE + i * PLAYER_SPACE);
                backgroundSprites.add(backgroundTile);
            }
        }
        for (int i = 0; i < GAME_BOARD_HEIGHT / PLAYER_SPACE; i++) {
            Sprite backgroundTile = new Sprite(Resources.getInstance().getGrass());
            backgroundTile.setSize(PLAYER_SPACE, PLAYER_SPACE);
            backgroundTile.setPosition(GAME_BOARD_LEFT_EDGE, GAME_BOARD_LOWER_EDGE + i * PLAYER_SPACE);
            backgroundSprites.add(backgroundTile);
        }
        for (int i = 0; i < GAME_BOARD_HEIGHT / PLAYER_SPACE; i++) {
            Sprite backgroundTile = new Sprite(Resources.getInstance().getGrass());
            backgroundTile.setSize(PLAYER_SPACE, PLAYER_SPACE);
            backgroundTile.setPosition(GAME_BOARD_RIGHT_EDGE - PLAYER_SPACE, GAME_BOARD_LOWER_EDGE + i * PLAYER_SPACE);
            backgroundSprites.add(backgroundTile);
        }
    }

    @Override
    public void render(float delta) {
        float lastFrameRenderTime = Gdx.graphics.getDeltaTime();

        processPlayerBodyMovement();
        processPlayerTurretMovement();

        switch (myGameBoard.updateGame(lastFrameRenderTime, leftPlayerShot, rightPlayerShot)) {
            case LEFT_WON_TIME:
                getMyGame().setScreen(new EndGameScreen(getMyGame(), LEFT_WON_TIME));
                //takeScreenshot();
                dispose();
                break;
            case RIGHT_WON_TIME:
                getMyGame().setScreen(new EndGameScreen(getMyGame(), RIGHT_WON_TIME));
                //takeScreenshot();
                dispose();
                break;
            case LEFT_WON_BOMB:
                getMyGame().setScreen(new EndGameScreen(getMyGame(), LEFT_WON_BOMB));
                //takeScreenshot();
                dispose();
                break;
            case RIGHT_WON_BOMB:
                getMyGame().setScreen(new EndGameScreen(getMyGame(), RIGHT_WON_BOMB));
                //takeScreenshot();
                dispose();
                break;
            case DRAW:
                getMyGame().setScreen(new EndGameScreen(getMyGame(), DRAW));
                //takeScreenshot();
                dispose();
                break;
            case CONTINUE:
                break;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            getMyGame().setScreen(new MainMenuScreen(getMyGame()));
            dispose();
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        myCamera.update();
        getMyGame().getMyBatch().setProjectionMatrix(myCamera.combined);
        myShapeDrawer.update();

        List<Drawable> myDrawables = myGameBoard.getDrawables();
        for (Drawable myDrawable : myDrawables)
            myDrawable.update();

        getMyGame().getMyBatch().begin();

        drawGameBoardBackground();

        for (Drawable myDrawable : myDrawables)
            myDrawable.draw(getMyGame().getMyBatch());

        drawGameUI();

        getMyGame().getMyBatch().end();

        getUiStage().act(Gdx.graphics.getDeltaTime());
        getUiStage().draw();
    }

    private void takeScreenshot() {
        Pixmap pixmap = Pixmap.createFromFrameBuffer((int) GAME_BOARD_LEFT_EDGE, (int) GAME_BOARD_LOWER_EDGE, GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT);
        PixmapIO.writePNG(Gdx.files.external("screenshot.png"), pixmap, Deflater.DEFAULT_COMPRESSION, true);
        pixmap.dispose();
    }

    private void drawGameUI() {
        Color uiBackgroundColor = new Color(0.1f, 0.12f, 0.1f, 1f);
        myShapeDrawer.filledRectangle(0, 0, (WIDTH - GAME_BOARD_WIDTH) / 2f, HEIGHT, uiBackgroundColor);
        myShapeDrawer.filledRectangle(WIDTH - ((WIDTH - GAME_BOARD_WIDTH) / 2f), 0, (WIDTH - GAME_BOARD_WIDTH) / 2f, HEIGHT, uiBackgroundColor);
        myShapeDrawer.filledRectangle((WIDTH - GAME_BOARD_WIDTH) / 2f, 0, GAME_BOARD_WIDTH, (HEIGHT - GAME_BOARD_HEIGHT) / 2f, uiBackgroundColor);
        myShapeDrawer.filledRectangle((WIDTH - GAME_BOARD_WIDTH) / 2f, GAME_BOARD_HEIGHT + (HEIGHT - GAME_BOARD_HEIGHT) / 2f, GAME_BOARD_WIDTH, (HEIGHT - GAME_BOARD_HEIGHT) / 2f, uiBackgroundColor);
        myShapeDrawer.rectangle(GAME_BOARD_LEFT_EDGE - 3f, GAME_BOARD_LOWER_EDGE - 3f, GAME_BOARD_WIDTH + 6f, GAME_BOARD_HEIGHT + 6f, Color.DARK_GRAY, 6f);

        getMyGame().getMyFont().draw(getMyGame().getMyBatch(), String.valueOf(myGameBoard.getLeftPlayer().getShotBullets().size()), 10, 900);
        getMyGame().getMyFont().draw(getMyGame().getMyBatch(), String.valueOf(myGameBoard.getRightPlayer().getShotBullets().size()), 1800, 900);
        getMyGame().getMyFont().draw(getMyGame().getMyBatch(), "SCORE: " + myGameBoard.getLeftPlayer().getScore(), 160, 40);
        getMyGame().getMyFont().draw(getMyGame().getMyBatch(), "SCORE: " + myGameBoard.getRightPlayer().getScore(), 1600, 40);
        getMyGame().getMyFont().draw(getMyGame().getMyBatch(), myGameBoard.getRemainingMinutes() + ":" + myGameBoard.getRemainingSeconds(), 950, 40);
    }

    private void drawGameBoardBackground() {
        for (Sprite sprite : backgroundSprites) {
            sprite.draw(getMyGame().getMyBatch());
        }
    }

    private void processPlayerBodyMovement() {
        int pressedKey = -1;

        if (Gdx.input.isKeyPressed(LEFT_PLAYER_UP))
            pressedKey = LEFT_PLAYER_UP;
        if (Gdx.input.isKeyPressed(LEFT_PLAYER_DOWN))
            pressedKey = LEFT_PLAYER_DOWN;
        myGameBoard.getLeftPlayer().move(Gdx.graphics.getDeltaTime(), pressedKey);

        pressedKey = -1;
        if (Gdx.input.isKeyPressed(RIGHT_PLAYER_UP))
            pressedKey = RIGHT_PLAYER_UP;
        if (Gdx.input.isKeyPressed(RIGHT_PLAYER_DOWN))
            pressedKey = RIGHT_PLAYER_DOWN;
        myGameBoard.getRightPlayer().move(Gdx.graphics.getDeltaTime(), pressedKey);
    }

    private void processPlayerTurretMovement() {
        int pressedKey = -1;

        if (Gdx.input.isKeyPressed(LEFT_TURRET_UP))
            pressedKey = LEFT_TURRET_UP;
        if (Gdx.input.isKeyPressed(LEFT_TURRET_DOWN))
            pressedKey = LEFT_TURRET_DOWN;
        myGameBoard.getLeftPlayer().moveTurret(Gdx.graphics.getDeltaTime(), pressedKey);

        pressedKey = -1;
        if (Gdx.input.isKeyPressed(RIGHT_TURRET_UP))
            pressedKey = RIGHT_TURRET_UP;
        if (Gdx.input.isKeyPressed(RIGHT_TURRET_DOWN))
            pressedKey = RIGHT_TURRET_DOWN;
        myGameBoard.getRightPlayer().moveTurret(Gdx.graphics.getDeltaTime(), pressedKey);
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
        disposeScreen();
    }
}
