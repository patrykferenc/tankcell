package com.jimp.tanksgame.logic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.jimp.tanksgame.graphics.Resources;

import java.util.ArrayList;
import java.util.List;

import static com.jimp.tanksgame.logic.Player.PlayerProperties.LEFT;
import static com.jimp.tanksgame.logic.Player.PlayerProperties.RIGHT;
import static com.jimp.tanksgame.logic.utils.GameConfiguration.*;

public class Player implements Drawable {

    private static final float MOVEMENT_SPEED = 300.0f;
    private static final float TURRET_ROTATION_SPEED = 45.0f;

    private final PlayerProperties whichPlayer;
    private final Rectangle playerBody;
    private final List<Bullet> shotBullets;
    private float turretRotation;
    private int score;

    private Sprite bodySprite;
    private Sprite turretSprite;

    public Player(PlayerProperties which) {
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

    public float getTurretRotation() {
        return turretRotation;
    }

    public float getCenterX() {
        return playerBody.getX() + playerBody.getWidth() / 2f;
    }

    public float getCenterY() {
        return playerBody.getY() + playerBody.getHeight() / 2f;
    }

    public void move(float deltaTime, int pressedKey) {
        if (pressedKey == LEFT_PLAYER_UP || pressedKey == RIGHT_PLAYER_UP) {
            playerBody.y += MOVEMENT_SPEED * deltaTime;
        } else if (pressedKey == LEFT_PLAYER_DOWN || pressedKey == RIGHT_PLAYER_DOWN) {
            playerBody.y -= MOVEMENT_SPEED * deltaTime;
        }
        if (playerBody.y < GAME_BOARD_LOWER_EDGE)
            playerBody.y = GAME_BOARD_LOWER_EDGE;
        if (playerBody.y > GAME_BOARD_UPPER_EDGE - PLAYER_SPACE)
            playerBody.y = GAME_BOARD_UPPER_EDGE - PLAYER_SPACE;
    }

    public void moveTurret(float deltaTime, int pressedKey) {
        if (pressedKey == LEFT_TURRET_UP || pressedKey == RIGHT_TURRET_UP) {
            turretRotation += TURRET_ROTATION_SPEED * deltaTime;
        } else if (pressedKey == LEFT_TURRET_DOWN || pressedKey == RIGHT_TURRET_DOWN) {
            turretRotation -= TURRET_ROTATION_SPEED * deltaTime;
        }
        if (whichPlayer.equals(LEFT)) {
            if (turretRotation > 60.0f)
                turretRotation = 60.0f;
            else if (turretRotation < -60.0f)
                turretRotation = -60.0f;
        } else if (whichPlayer.equals(RIGHT)) {
            if (turretRotation > 240.0f)
                turretRotation = 240.0f;
            else if (turretRotation < 120.0f)
                turretRotation = 120.0f;
        }
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

    public void shootBullet(float bulletVelocity, float bulletSize, int maxBullets) {
        if (shotBullets.size() < maxBullets)
            shotBullets.add(new Bullet(getTurretX(), getTurretY(), bulletSize, bulletVelocity, getTurretRotation()));
    }

    private float getTurretX() {
        return getCenterX() + TURRET_LENGTH * (float) Math.cos(Math.toRadians(turretRotation));
    }

    private float getTurretY() {
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
