package com.jimp.tanksgame.logic.tanks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.jimp.tanksgame.graphics.Resources;
import com.jimp.tanksgame.logic.Drawable;
import com.jimp.tanksgame.logic.bullets.Bullet;

import java.util.ArrayList;
import java.util.List;

import static com.jimp.tanksgame.logic.tanks.Tank.PlayerProperties.LEFT;
import static com.jimp.tanksgame.logic.tanks.Tank.PlayerProperties.RIGHT;
import static com.jimp.tanksgame.logic.utils.GameConfiguration.*;

public abstract class Tank implements Drawable {

    protected static final float MOVEMENT_SPEED = 300.0f;
    protected static final float TURRET_ROTATION_SPEED = 45.0f;

    private final Player.PlayerProperties whichPlayer;
    private final Rectangle playerBody;
    private final List<Bullet> shotBullets;
    private float turretRotation;
    private int score;
    private Sprite bodySprite;
    private Sprite turretSprite;

    protected Tank(Player.PlayerProperties which) {
        whichPlayer = which;
        playerBody = new Rectangle();
        playerBody.height = PLAYER_SPACE;
        playerBody.width = PLAYER_SPACE;
        if (whichPlayer.equals(LEFT))
            playerBody.x = GAME_BOARD.getX() + (PLAYER_SPACE - 128f) / 2f;
        else
            playerBody.x = GAME_BOARD.getX() + GAME_BOARD.getWidth() - PLAYER_SPACE + (PLAYER_SPACE - 128f) / 2f;
        playerBody.y = (GAME_BOARD.getY() + GAME_BOARD.getHeight() / 2.0f) - (PLAYER_SPACE / 2.0f);
        turretRotation = whichPlayer.equals(LEFT) ? 0.0f : 180.0f;
        shotBullets = new ArrayList<>();
        score = 0;
        chooseSprites();
    }

    public PlayerProperties getWhichPlayer() {
        return whichPlayer;
    }

    public Rectangle getPlayerBody() {
        return playerBody;
    }

    public float getTurretRotation() {
        return turretRotation;
    }

    public void setTurretRotation(float turretRotation) {
        this.turretRotation = turretRotation;
    }

    public float getCenterX() {
        return playerBody.getX() + playerBody.getWidth() / 2f;
    }

    public float getCenterY() {
        return playerBody.getY() + playerBody.getHeight() / 2f;
    }

    public int getScore() {
        return score;
    }

    public void increaseScoreBy(int pointsToAdd) {
        this.score += pointsToAdd;
    }

    public List<Bullet> getShotBullets() {
        return shotBullets;
    }

    public float getTurretX() {
        return getCenterX() + TURRET_LENGTH * (float) Math.cos(Math.toRadians(turretRotation));
    }

    public float getTurretY() {
        return getCenterY() + TURRET_LENGTH * (float) Math.sin(Math.toRadians(turretRotation));
    }

    private void chooseSprites() {
        if (whichPlayer == LEFT) {
            bodySprite = new Sprite(Resources.getInstance().getLeftTankBody());
            turretSprite = new Sprite(Resources.getInstance().getLeftTankTurret());
        } else if (whichPlayer == RIGHT) {
            bodySprite = new Sprite(Resources.getInstance().getRightTankBody());
            bodySprite.setRotation(180f);
            turretSprite = new Sprite(Resources.getInstance().getRightTankTurret());
        }
    }

    @Override
    public void update() {
        bodySprite.setPosition(playerBody.x, playerBody.y);
        turretSprite.setRotation(turretRotation);
        turretSprite.setPosition(playerBody.x, playerBody.y);
    }

    @Override
    public void draw(Batch batch) {
        bodySprite.draw(batch);
        turretSprite.draw(batch);
    }

    public void shootBullet(float bulletVelocity, float bulletSize, int maxBullets) {
        if (getShotBullets().size() < maxBullets)
            getShotBullets().add(new Bullet(getTurretX(), getTurretY(), bulletSize, bulletVelocity, getTurretRotation()));
    }

    //True means up, false means down
    public void move(float deltaTime, boolean direction) {
        if (direction) {
            getPlayerBody().y += MOVEMENT_SPEED * deltaTime;
        } else {
            getPlayerBody().y -= MOVEMENT_SPEED * deltaTime;
        }
        //If outside of the gameboard, stop the movement.
        if (getPlayerBody().y < GAME_BOARD.getY())
            getPlayerBody().y = GAME_BOARD.getY();
        if (getPlayerBody().y > GAME_BOARD.getY() + GAME_BOARD.getHeight() - PLAYER_SPACE)
            getPlayerBody().y = GAME_BOARD.getY() + GAME_BOARD.getHeight() - PLAYER_SPACE;
    }

    //True means up, false means down
    public void moveTurret(float deltaTime, boolean direction) {
        float degree;
        if (direction) {
            degree = getTurretRotation() + TURRET_ROTATION_SPEED * deltaTime;
        } else {
            degree = getTurretRotation() - TURRET_ROTATION_SPEED * deltaTime;
        }

        setTurretRotation(degree);

        if (getWhichPlayer().equals(LEFT)) {
            if (getTurretRotation() > 60.0f)
                setTurretRotation(60.0f);
            else if (getTurretRotation() < -60.0f)
                setTurretRotation(-60.0f);
        } else if (getWhichPlayer().equals(RIGHT)) {
            if (getTurretRotation() > 240.0f)
                setTurretRotation(240.0f);
            else if (getTurretRotation() < 120.0f)
                setTurretRotation(120.0f);
        }
    }

    public enum PlayerProperties {
        LEFT, RIGHT
    }

}
