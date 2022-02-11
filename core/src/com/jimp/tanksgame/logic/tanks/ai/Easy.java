package com.jimp.tanksgame.logic.tanks.ai;

import com.badlogic.gdx.math.Vector2;
import com.jimp.tanksgame.logic.cells.Cell;
import com.jimp.tanksgame.logic.cells.Colony;
import com.jimp.tanksgame.logic.tanks.Tank;
import com.jimp.tanksgame.logic.utils.GameConfiguration;
import com.jimp.tanksgame.logic.utils.GameTimer;

import java.util.Map;

import static com.jimp.tanksgame.logic.tanks.ai.AI.CurrentState.*;

public class Easy extends AI {

    private final GameTimer idleTimer = new GameTimer(0.5f);

    public Easy(Tank tank) {
        super(tank);
    }

    @Override
    public CurrentState updateAndReturnState(float deltaTime, Map<Integer, Colony> colonies, Tank tank) {
        setTank(tank);
        // Idle if there are no eligible targets
        if (colonies == null || colonies.isEmpty()) {
            return IDLE;
        }
        chooseTarget(colonies);
        updateDirectionToTarget();
        if (!isInDesiredPosition())
            setState(MOVING);
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
                if (isInDesiredPosition())
                    setState(IDLE);
                else
                    tank.move(deltaTime, false);
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

    private void chooseTarget(Map<Integer, Colony> colonies) {
        if (getCurrentTargetCell() == null || getCurrentTargetColony() == null) {
            findAndSetNewTargetColony(colonies);
            findAndSetNewTargetCell();
        } else
            updateCurrentCellAndTarget(colonies);
    }

    private void updateCurrentCellAndTarget(Map<Integer, Colony> colonies) {
        Colony newTargetColony = colonies.get(getCurrentTargetColony().getId());

        if (newTargetColony == null || newTargetColony.isAlreadyDead() || newTargetColony.colonyOutsideOfGameBoard()) {
            findAndSetNewTargetColony(colonies);
            findAndSetNewTargetCell();
        } else {
            setCurrentTargetColony(newTargetColony);
            Cell newTargetCell = getCurrentTargetColony().getCells().get(getCurrentTargetCell().getId());
            if (newTargetCell == null || !newTargetCell.isAlive() || !newTargetCell.isOnBoard())
                findAndSetNewTargetCell();
            else
                setCurrentTargetCell(newTargetCell);
        }
    }

    private void findAndSetNewTargetColony(Map<Integer, Colony> colonies) {
        Vector2 distanceToClosestColony = new Vector2(10000, 10000); //very long vector
        Vector2 comparisonVector = new Vector2();

        for (Colony colony : colonies.values()) {
            Cell centralCell = colony.getCentralCell();
            comparisonVector = centralCell.getCellRectangle().getCenter(comparisonVector);
            if (comparisonVector.len2() < distanceToClosestColony.len2()) {
                distanceToClosestColony = comparisonVector;
                setCurrentTargetColony(colony);
            }
        }
    }

    private boolean isInDesiredPosition() {
        return getTank().getCenterY() < GameConfiguration.GAME_BOARD.getY() + getTank().getPlayerBody().getHeight() / 2f + 1f;
    }

    private void findAndSetNewTargetCell() {
        Vector2 distanceToClosestCell = new Vector2(10000, 10000); //very long vector
        Vector2 comparisonVector = new Vector2();

        for (Cell cell : getCurrentTargetColony().getCells().values()) {
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
