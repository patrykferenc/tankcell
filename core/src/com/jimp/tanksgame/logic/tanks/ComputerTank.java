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

    public void updateState(float deltaTime, float bulletVelocity, float bulletSize, int maxBullets, List<Colony> colonies, boolean canShoot) {
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
                //not sure about the future of that feature
                break;
        }
    }

}
