package com.jimp.tanksgame.logic.tanks.ai;

import com.badlogic.gdx.math.Vector2;
import com.jimp.tanksgame.logic.cells.Cell;
import com.jimp.tanksgame.logic.cells.Colony;
import com.jimp.tanksgame.logic.tanks.Tank;
import com.jimp.tanksgame.logic.utils.GameConfiguration;
import com.jimp.tanksgame.logic.utils.GameTimer;

import java.util.List;

import static com.jimp.tanksgame.logic.tanks.ai.AI.CurrentState.*;
import static com.jimp.tanksgame.logic.utils.GameConfiguration.GAME_BOARD;

public class Easy extends AI {

    public static final float ANGLE_TOLERANCE = 25f;
    private final GameTimer idleTimer = new GameTimer(0.5f);

    public Easy(Tank tank) {
        super(tank);
    }

    @Override
    public CurrentState updateAndReturnState(float deltaTime, List<Colony> colonies, Tank tank) {
        setTank(tank);
        if (colonies == null || colonies.isEmpty()) {
            return IDLE;
        }
        chooseTarget(colonies);
        updateDirectionToTarget();
        switch (getState()) {
            case IDLE:
                if (idleTimer.update(deltaTime)) setState(AIMING);
                break;
            case AIMING:
            case SHOT:
                if (isOnTarget()) {
                    setState(SHOT);
                } else
                    setState(AIMING);
                break;
            case MOVING:
                //not implemented
                break;
        }
        return getState();
    }

    private void updateDirectionToTarget() {
        float angle = (float) Math.toDegrees(Math.atan2(getCurrentTargetCell().getCellRectangle().getY() - getTank().getCenterY(),
                getCurrentTargetCell().getCellRectangle().getX() - getTank().getCenterX()));
        if (angle < 0) {
            angle += 360;
        }
        setDirectionToTarget(angle);
    }

    private void chooseTarget(List<Colony> colonies) {
        if (getCurrentTargetCell() == null || getCurrentTargetColony() == null) {
            findAndSetNewTargetColony(colonies);
            findAndSetNewTargetCell();
        } else
            updateCurrentCellAndTarget(colonies);
    }

    private void updateCurrentCellAndTarget(List<Colony> colonies) {
        boolean colonyStillSame = false;
        for (Colony colonyToCheck : colonies) {
            if (colonyToCheck.getId() == getCurrentTargetColony().getId()) {
                colonyStillSame = true;
                setCurrentTargetColony(colonyToCheck);
                break;
            }
        }
        if (!colonyStillSame || targetOutOfRange() || bombOnTheWay()) {
            //System.out.println("Shieeeeet");
            findAndSetNewTargetColony(colonies);
            findAndSetNewTargetCell();
            return;
        }
        for (Cell cellToCheck : getCurrentTargetColony().getCells()) {
            if (cellToCheck.isAlive() && cellToCheck.getId() == getCurrentTargetCell().getId()) {
                setCurrentTargetCell(cellToCheck);
            } else if (!cellToCheck.isAlive() && cellToCheck.getId() == getCurrentTargetCell().getId()) {
                findAndSetNewTargetCell();
                return;
            }
        }

    }

    private boolean bombOnTheWay() {
        float angle = (float) Math.toDegrees(Math.atan2(GameConfiguration.BOMB_Y - getTank().getCenterY(),
                GameConfiguration.BOMB_X - getTank().getCenterX()));
        if (angle < 0) {
            angle += 360;
        }
        return ((angle >= getDirectionToTarget().angleDeg() - ANGLE_TOLERANCE) &&
                (angle <= getDirectionToTarget().angleDeg() + ANGLE_TOLERANCE));
    }

    private boolean targetOutOfRange() {
        float colonyY = getCurrentTargetColony().getCentralCell().getCellRectangle().getY();
        return colonyY < GAME_BOARD.getY() + 100;
    }

    private void findAndSetNewTargetColony(List<Colony> colonies) {
        Vector2 distanceToClosestColony = new Vector2(10000, 10000); //very long vector
        Vector2 comparisonVector = new Vector2();
        for (Colony colony : colonies) {
            comparisonVector = colony.getCentralCell().getCellRectangle().getCenter(comparisonVector);
            if (comparisonVector.len2() < distanceToClosestColony.len2()) {
                distanceToClosestColony = comparisonVector;
                setCurrentTargetColony(colony);
            }
        }
    }

    private void findAndSetNewTargetCell() {
        Vector2 distanceToClosestCell = new Vector2(10000, 10000); //very long vector
        Vector2 comparisonVector = new Vector2();
        for (Cell cell : getCurrentTargetColony().getCells()) {
            if (cell.isAlive()) {
                comparisonVector = cell.getCellRectangle().getCenter(comparisonVector);
                if (comparisonVector.len2() < distanceToClosestCell.len2()) {
                    distanceToClosestCell = comparisonVector;
                    setCurrentTargetCell(cell);
                }
            }
        }
    }
}
