package com.jimp.tanksgame.logic;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.jimp.tanksgame.graphics.Resources;

import static com.jimp.tanksgame.logic.utils.GameConfiguration.*;


class Bullet implements Drawable {

    private final Circle bulletBody;
    private final Sprite sprite;
    private final Vector2 direction;

    private boolean isWasted;

    public Bullet(float x, float y, float radius, float bulletVelocity, float angle) {
        bulletBody = new Circle(x, y, radius);
        direction = new Vector2(1.0f, 0.0f);
        direction.setAngleDeg(angle);
        direction.setLength(bulletVelocity);
        isWasted = false;
        sprite = new Sprite(Resources.getInstance().getBullet());
        sprite.setSize(bulletBody.radius * 2f, bulletBody.radius * 2f);
        sprite.setPosition(bulletBody.x - bulletBody.radius, bulletBody.y - bulletBody.radius);
    }

    public Circle getBulletBody() {
        return bulletBody;
    }

    public void wasteBullet() {
        isWasted = true;
    }

    public boolean isWasted() {
        return isWasted;
    }

    public void move(float deltaTime, float velocity) {
        direction.setLength(velocity);
        bulletBody.x += direction.x * deltaTime;
        bulletBody.y += direction.y * deltaTime;
        if (isOutsideGameBoard())
            wasteBullet();
    }

    private boolean isOutsideGameBoard() {
        return (bulletBody.x > GAME_BOARD_RIGHT_EDGE) || (bulletBody.x < GAME_BOARD_LEFT_EDGE) ||
                (bulletBody.y > GAME_BOARD_UPPER_EDGE) || (bulletBody.y < GAME_BOARD_LOWER_EDGE);
    }

    @Override
    public void update() {
        sprite.setSize(bulletBody.radius * 2f, bulletBody.radius * 2f);
        sprite.setPosition(bulletBody.x - bulletBody.radius, bulletBody.y - bulletBody.radius);
    }

    @Override
    public void draw(Batch batch) {
        sprite.draw(batch);
    }
}
