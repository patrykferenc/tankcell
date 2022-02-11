package com.jimp.tanksgame.logic.tanks;

import com.jimp.tanksgame.logic.cells.Colony;
import com.jimp.tanksgame.logic.tanks.ai.*;
import com.jimp.tanksgame.logic.utils.GameConfiguration.Difficulty;

import java.util.Map;

public class ComputerTank extends Tank {

    private AI tankBrain;

    public ComputerTank(PlayerProperties which, Difficulty difficulty) {
        super(which);

        switch (difficulty) {
            case EASY:
                tankBrain = new Easy(this);
                break;
            case NORMAL:
                tankBrain = new Normal(this);
                break;
            case HARD:
                tankBrain = new Intelligent(this);
                break;
            case ABSOLUTE_MADMAN:
                tankBrain = new Mastermind(this);
                break;
        }
    }

    public void updateState(float deltaTime, float bulletVelocity, float bulletSize, int maxBullets, Map<Integer, Colony> colonies, boolean canShoot) {
        switch (tankBrain.updateAndReturnState(deltaTime, colonies, this)) {
            case IDLE:
                //wait
                break;
            case AIMING:
                moveTurret(deltaTime, tankBrain.getTurretDirectionToMoveTurret());
                break;
            case SHOT:
                if (canShoot)
                    shootBullet(bulletVelocity, bulletSize, maxBullets);
                break;
            case MOVING:
                //not sure about the future of that feature, brain decides where to move so?
                break;
        }
    }

}
