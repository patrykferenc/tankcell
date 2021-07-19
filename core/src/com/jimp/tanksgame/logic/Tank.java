package com.jimp.tanksgame.logic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.jimp.tanksgame.graphics.Resources;

import java.util.ArrayList;
import java.util.List;

import static com.jimp.tanksgame.logic.Tank.PlayerProperties.LEFT;
import static com.jimp.tanksgame.logic.Tank.PlayerProperties.RIGHT;
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
            playerBody.x = GAME_BOARD_LEFT_EDGE + (PLAYER_SPACE - 128f) / 2f;
        else
            playerBody.x = GAME_BOARD_RIGHT_EDGE - PLAYER_SPACE + (PLAYER_SPACE - 128f) / 2f;
        playerBody.y = (GAME_BOARD_UPPER_EDGE / 2.0f) - (PLAYER_SPACE / 2.0f);
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

    public abstract void move(float deltaTime, int pressedKey);

    public abstract void moveTurret(float deltaTime, int pressedKey);

    public int getScore() {
        return score;
    }

    public void increaseScoreBy(int pointsToAdd) {
        this.score += pointsToAdd;
    }

    public List<Bullet> getShotBullets() {
        return shotBullets;
    }

    public abstract void shootBullet(float bulletVelocity, float bulletSize, int maxBullets);

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

    public enum PlayerProperties {
        LEFT, RIGHT
    }

}
