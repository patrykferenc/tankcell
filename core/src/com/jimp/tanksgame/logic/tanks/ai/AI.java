package com.jimp.tanksgame.logic.tanks.ai;

import com.badlogic.gdx.math.collision.Ray;
import com.jimp.tanksgame.logic.cells.Colony;

import java.awt.*;
import java.util.List;

public abstract class AI {

    private CurrentState state;

    private Rectangle currentTarget;
    private Ray ray;

    public abstract CurrentState updateAndReturnState(float deltaTime, List<Colony> colonies, float turretRotation, float bodyY);

    public int getTurretDirectionToMove() {
        //ray.set()
        return 0;
    }

    public int getBodyDirectionToMove() {
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
