package com.jimp.tanksgame.logic.cells;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.jimp.tanksgame.graphics.Resources;
import com.jimp.tanksgame.logic.Drawable;

import java.util.concurrent.ThreadLocalRandom;

import static com.jimp.tanksgame.logic.utils.GameConfiguration.GAME_BOARD;

public class Cell implements Drawable {

    private static final int MAX_CELL_VALUE = 9;
    private static final int MIN_CELL_VALUE = 1;

    private final int id;

    private final int startingValue;
    private final Rectangle cellRectangle;
    private int currentValue;
    private Level currentLevel;
    private Sprite cellSprite;
    private Sprite numberSprite;

    public Cell(float x, float y, float cellSize, int id) {
        this.id = id;
        cellRectangle = new Rectangle();
        cellRectangle.width = cellSize;
        cellRectangle.height = cellSize;
        cellRectangle.x = x;
        cellRectangle.y = y;
        startingValue = ThreadLocalRandom.current().nextInt(MIN_CELL_VALUE, MAX_CELL_VALUE);
        currentValue = startingValue;
        setCurrentLevel();
        chooseBodySprite();
        chooseStartingPointsSprite();
    }

    private void setCurrentLevel() {
        if (currentValue >= 7)
            currentLevel = Level.GREEN;
        else if (currentValue >= 4)
            currentLevel = Level.YELLOW;
        else
            currentLevel = Level.RED;
    }

    public Rectangle getCellRectangle() {
        return cellRectangle;
    }

    public int getStartingValue() {
        return startingValue;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void increaseCurrentValue() {
        currentValue = (currentValue < MAX_CELL_VALUE && currentValue != 0) ? currentValue + 1 : currentValue;
        setCurrentLevel();
    }

    public void decreaseCurrentValue() {
        currentValue = (currentValue != 0) ? currentValue - 1 : currentValue;
        setCurrentLevel();
    }

    public boolean isOnBoard() {
        return cellRectangle.overlaps(GAME_BOARD);
    }

    public boolean isAlive() {
        return (currentValue != 0);
    }

    public void move(float deltaTime, float velocity) {
        float newY = cellRectangle.getY() - velocity * deltaTime;
        if (newY < (GAME_BOARD.getY() - 3 * cellRectangle.height)) {
            currentValue = 0;
            setCurrentLevel();
        }
        cellRectangle.setY(newY);
    }

    private void chooseBodySprite() {
        switch (currentLevel) {
            case GREEN:
                cellSprite = new Sprite(Resources.getInstance().getCellGreen());
                break;
            case YELLOW:
                cellSprite = new Sprite(Resources.getInstance().getCellYellow());
                break;
            case RED:
                cellSprite = new Sprite(Resources.getInstance().getCellRed());
                break;
        }
    }

    private void chooseStartingPointsSprite() {
        switch (startingValue) {
            case 1:
                numberSprite = new Sprite(Resources.getInstance().getNumbers().get(1));
                break;
            case 2:
                numberSprite = new Sprite(Resources.getInstance().getNumbers().get(2));
                break;
            case 3:
                numberSprite = new Sprite(Resources.getInstance().getNumbers().get(3));
                break;
            case 4:
                numberSprite = new Sprite(Resources.getInstance().getNumbers().get(4));
                break;
            case 5:
                numberSprite = new Sprite(Resources.getInstance().getNumbers().get(5));
                break;
            case 6:
                numberSprite = new Sprite(Resources.getInstance().getNumbers().get(6));
                break;
            case 7:
                numberSprite = new Sprite(Resources.getInstance().getNumbers().get(7));
                break;
            case 8:
                numberSprite = new Sprite(Resources.getInstance().getNumbers().get(8));
                break;
            case 9:
                numberSprite = new Sprite(Resources.getInstance().getNumbers().get(9));
                break;
            default:
                break;
        }
    }

    @Override
    public void update() {
        chooseBodySprite();
        cellSprite.setSize(cellRectangle.width, cellRectangle.height);
        cellSprite.setPosition(cellRectangle.x, cellRectangle.y);
        numberSprite.setSize(cellRectangle.width, cellRectangle.height);
        numberSprite.setPosition(cellRectangle.x, cellRectangle.y);
    }

    @Override
    public void draw(Batch batch) {
        cellSprite.draw(batch);
        numberSprite.draw(batch);
    }

    public int getId() {
        return id;
    }

    public enum Level {
        GREEN,
        YELLOW,
        RED
    }
}
