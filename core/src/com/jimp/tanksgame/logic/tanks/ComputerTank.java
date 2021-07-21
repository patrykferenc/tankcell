package com.jimp.tanksgame.logic.tanks;

import com.jimp.tanksgame.logic.utils.GameSettingsConfigurator.Difficulty;

public class ComputerTank extends Tank {

    public ComputerTank(PlayerProperties which, Difficulty difficulty) {
        super(which);
        //create a brain for the tank
    }

    public void update(float deltaTime) {
        //the ai decides what to do...
    }

}
