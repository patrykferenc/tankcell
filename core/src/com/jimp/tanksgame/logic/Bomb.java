package com.jimp.tanksgame.logic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.jimp.tanksgame.graphics.Resources;

import static com.jimp.tanksgame.logic.utils.GameConfiguration.*;

class Bomb implements Drawable {

    private final Rectangle bombRectangle;
    private final Sprite sprite;

    public Bomb() {
        float cellSize = 80.0f;
        bombRectangle = new Rectangle();
        bombRectangle.setSize(cellSize);
        bombRectangle.setPosition(GAME_BOARD_LEFT_EDGE + (GAME_BOARD_WIDTH / 2f) - (cellSize / 2f), GAME_BOARD_LOWER_EDGE);
        sprite = new Sprite(Resources.getInstance().getBomb());
        sprite.setSize(cellSize, cellSize);
        sprite.setPosition(bombRectangle.getX(), bombRectangle.getY());
    }

    public Rectangle getBombEdge() {
        Rectangle upperEdge = new Rectangle();
        upperEdge.setSize(bombRectangle.width, 1f);
        upperEdge.setPosition(bombRectangle.x, bombRectangle.y + bombRectangle.height);
        return upperEdge;
    }

    public Rectangle getBombRectangle() {
        return bombRectangle;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Batch batch) {
        sprite.draw(batch);
    }
}
