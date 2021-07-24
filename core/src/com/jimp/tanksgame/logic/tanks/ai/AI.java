package com.jimp.tanksgame.logic.tanks.ai;

import com.badlogic.gdx.math.Vector2;
import com.jimp.tanksgame.logic.cells.Colony;
import com.jimp.tanksgame.logic.tanks.Tank;

import java.awt.*;
import java.util.List;

import static com.jimp.tanksgame.logic.utils.GameConfiguration.*;

public abstract class AI {

    public static final float ANGLE_TOLERANCE = 2.5f;
    private CurrentState state;
    private Rectangle currentTarget;
    private Vector2 directionToTarget;
    private Tank tank;

    protected AI(Tank tank) {
        this.tank = tank;
    }

    public abstract CurrentState updateAndReturnState(float deltaTime, List<Colony> colonies, Tank tank);

    //this method should pick the target to shoot at
    protected abstract void chooseTarget();

    public boolean isOnTarget() {
        //returns whether the current turret rotation vector intersects the target
        return false;
    }

    public int getTurretDirectionToMoveTurret() {
        //this should decide where to move the turret (up or down)
        switch (tank.getWhichPlayer()) {
            case LEFT:
                if (directionToTarget.angleDeg() > tank.getTurretRotation() + ANGLE_TOLERANCE)
                    return LEFT_TURRET_UP;
                else if (directionToTarget.angleDeg() < tank.getTurretRotation() - ANGLE_TOLERANCE)
                    return LEFT_TURRET_DOWN;
                break;
            case RIGHT:
                if (directionToTarget.angleDeg() > tank.getTurretRotation() + ANGLE_TOLERANCE)
                    return RIGHT_TURRET_DOWN;
                else if (directionToTarget.angleDeg() < tank.getTurretRotation() - ANGLE_TOLERANCE)
                    return RIGHT_TURRET_UP;
                break;
        }
        //no movement
        return 0;
    }

    public int getBodyDirectionToMove() {
        //not implemented yet!
        return 0;
    }

    public CurrentState getState() {
        return state;
    }

    public void setState(CurrentState state) {
        this.state = state;
    }

    public enum CurrentState {
        IDLE, AIMING, SHOT, MOVING
    }

}
