package com.jimp.tanksgame.logic.tanks;

import com.jimp.tanksgame.logic.cells.Colony;
import com.jimp.tanksgame.logic.tanks.ai.*;
import com.jimp.tanksgame.logic.utils.GameSettingsConfigurator.Difficulty;

import java.util.List;

public class ComputerTank extends Tank {

    private AI tankBrain;

    public ComputerTank(PlayerProperties which, Difficulty difficulty) {
        super(which);

        switch (difficulty) {
            case EASY:
                tankBrain = new Easy();
                break;
            case NORMAL:
                tankBrain = new Normal();
                break;
            case HARD:
                tankBrain = new Intelligent();
                break;
            case ABSOLUTE_MADMAN:
                tankBrain = new Mastermind();
                break;
        }
    }

    public void updateState(float deltaTime, float bulletVelocity, float bulletSize, int maxBullets, List<Colony> colonies) {
        switch (tankBrain.updateAndReturnState(deltaTime, colonies, getTurretRotation(), getCenterY())) {
            case IDLE:
                break;
            case AIMING:
                break;
            case SHOT:
                shootBullet(bulletVelocity, bulletSize, maxBullets);
                break;
            case MOVING:
                break;
        }
    }

}
