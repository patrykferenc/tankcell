package com.jimp.tanksgame.logic.tanks.ai;

import com.badlogic.gdx.math.Vector2;
import com.jimp.tanksgame.logic.cells.Cell;
import com.jimp.tanksgame.logic.cells.Colony;
import com.jimp.tanksgame.logic.tanks.Tank;

import java.util.Map;

import static com.jimp.tanksgame.logic.tanks.ai.AI.CurrentState.IDLE;

public abstract class AI {

    public static final float ANGLE_TOLERANCE = 2.5f;
    private Tank tank;
    private CurrentState state;
    private Cell currentTargetCell;
    private Colony currentTargetColony;
    private Vector2 directionToTarget;

    protected AI(Tank tank) {
        this.tank = tank;
        state = IDLE;
        directionToTarget = new Vector2(1, 0);
    }

    public abstract CurrentState updateAndReturnState(float deltaTime, Map<Integer, Colony> colonies, Tank tank);

    public boolean isOnTarget() {
        return (tank.getTurretRotation() >= directionToTarget.angleDeg() - ANGLE_TOLERANCE) &&
                (tank.getTurretRotation() <= directionToTarget.angleDeg() + ANGLE_TOLERANCE);
    }

    //Returns true if up, false if down
    public boolean getTurretDirectionToMoveTurret() {
        return directionToTarget.angleDeg() > tank.getTurretRotation() + ANGLE_TOLERANCE;
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

    public Cell getCurrentTargetCell() {
        return currentTargetCell;
    }

    public void setCurrentTargetCell(Cell currentTargetCell) {
        this.currentTargetCell = currentTargetCell;
    }

    public Colony getCurrentTargetColony() {
        return currentTargetColony;
    }

    public void setCurrentTargetColony(Colony currentTargetColony) {
        this.currentTargetColony = currentTargetColony;
    }

    public Vector2 getDirectionToTarget() {
        return directionToTarget;
    }

    public void setDirectionToTarget(float targetDegree) {
        directionToTarget.setAngleDeg(targetDegree);
    }

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public enum CurrentState {
        IDLE, AIMING, SHOT, MOVING
    }

}
