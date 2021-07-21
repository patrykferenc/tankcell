package com.jimp.tanksgame.logic.tanks;

import com.jimp.tanksgame.logic.bullets.Bullet;

import static com.jimp.tanksgame.logic.tanks.Tank.PlayerProperties.LEFT;
import static com.jimp.tanksgame.logic.tanks.Tank.PlayerProperties.RIGHT;
import static com.jimp.tanksgame.logic.utils.GameConfiguration.*;

public class Player extends Tank {

    public Player(PlayerProperties which) {
        super(which);
    }

    public void move(float deltaTime, int pressedKey) {
        if (pressedKey == LEFT_PLAYER_UP || pressedKey == RIGHT_PLAYER_UP) {
            getPlayerBody().y += MOVEMENT_SPEED * deltaTime;
        } else if (pressedKey == LEFT_PLAYER_DOWN || pressedKey == RIGHT_PLAYER_DOWN) {
            getPlayerBody().y -= MOVEMENT_SPEED * deltaTime;
        }
        if (getPlayerBody().y < GAME_BOARD_LOWER_EDGE)
            getPlayerBody().y = GAME_BOARD_LOWER_EDGE;
        if (getPlayerBody().y > GAME_BOARD_UPPER_EDGE - PLAYER_SPACE)
            getPlayerBody().y = GAME_BOARD_UPPER_EDGE - PLAYER_SPACE;
    }

    public void moveTurret(float deltaTime, int pressedKey) {
        if (pressedKey == LEFT_TURRET_UP || pressedKey == RIGHT_TURRET_UP) {
            float degree = getTurretRotation() + TURRET_ROTATION_SPEED * deltaTime;
            setTurretRotation(degree);
        } else if (pressedKey == LEFT_TURRET_DOWN || pressedKey == RIGHT_TURRET_DOWN) {
            float degree = getTurretRotation() - TURRET_ROTATION_SPEED * deltaTime;
            setTurretRotation(degree);
        }
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

    public void shootBullet(float bulletVelocity, float bulletSize, int maxBullets) {
        if (getShotBullets().size() < maxBullets)
            getShotBullets().add(new Bullet(getTurretX(), getTurretY(), bulletSize, bulletVelocity, getTurretRotation()));
    }
}
