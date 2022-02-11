package com.jimp.tanksgame.logic.tanks.ai;

import com.jimp.tanksgame.logic.cells.Colony;
import com.jimp.tanksgame.logic.tanks.Tank;

import java.util.Map;

public class Normal extends AI {

    public Normal(Tank tank) {
        super(tank);
    }

    @Override
    public CurrentState updateAndReturnState(float deltaTime, Map<Integer, Colony> colonies, Tank tank) {
        return CurrentState.IDLE;
    }
}
