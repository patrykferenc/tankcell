package com.jimp.tanksgame.logic.cells;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.jimp.tanksgame.graphics.Resources;
import com.jimp.tanksgame.logic.Drawable;

import static com.jimp.tanksgame.logic.utils.GameConfiguration.*;

public class Bomb implements Drawable {

    private final Rectangle bombRectangle;
    private final Sprite sprite;

    public Bomb() {
        bombRectangle = new Rectangle();
        bombRectangle.setSize(BOMB_SIZE);
        bombRectangle.setPosition(BOMB_X, BOMB_Y);
        sprite = new Sprite(Resources.getInstance().getBomb());
        sprite.setSize(BOMB_SIZE, BOMB_SIZE);
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
