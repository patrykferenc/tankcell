package com.jimp.tanksgame.logic.tanks.ai;

import com.jimp.tanksgame.logic.cells.Colony;
import com.jimp.tanksgame.logic.tanks.Tank;

import java.util.List;
import java.util.Map;

public class Intelligent extends AI {

    public Intelligent(Tank tank) {
        super(tank);
    }

    @Override
    public CurrentState updateAndReturnState(float deltaTime, Map<Integer, Colony> colonies, Tank tank) {
        return CurrentState.IDLE;
    }

    //@Override
    protected void chooseTarget(List<Colony> colonies) {

    }
}
