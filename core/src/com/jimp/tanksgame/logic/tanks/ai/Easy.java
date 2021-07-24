package com.jimp.tanksgame.logic.tanks.ai;

import com.jimp.tanksgame.logic.cells.Colony;
import com.jimp.tanksgame.logic.tanks.Tank;

import java.util.List;

public class Easy extends AI {

    public Easy(Tank tank) {
        super(tank);
    }

    @Override
    public CurrentState updateAndReturnState(float deltaTime, List<Colony> colonies, Tank tank) {
        return CurrentState.IDLE;
    }

    @Override
    protected void chooseTarget() {

    }
}
