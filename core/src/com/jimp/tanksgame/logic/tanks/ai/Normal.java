package com.jimp.tanksgame.logic.tanks.ai;

import com.jimp.tanksgame.logic.cells.Colony;
import com.jimp.tanksgame.logic.tanks.Tank;

import java.util.List;

public class Normal extends AI {

    public Normal(Tank tank) {
        super(tank);
    }

    @Override
    public CurrentState updateAndReturnState(float deltaTime, List<Colony> colonies, Tank tank) {
        //here is the "brain" part, chooses what to do depending on different factors - will be further improved
        return CurrentState.IDLE;
    }

    @Override
    protected void chooseTarget() {
        //decide which target to choose
        //update the target
        //shoot if it is visible
    }
}
