package com.jimp.tanksgame.logic.tanks.ai;

import com.jimp.tanksgame.logic.cells.Colony;

import java.util.List;

public class Easy extends AI {

    @Override
    public CurrentState updateAndReturnState(float deltaTime, List<Colony> colonies, float turretRotation, float bodyY) {
        return CurrentState.IDLE;
    }
}
