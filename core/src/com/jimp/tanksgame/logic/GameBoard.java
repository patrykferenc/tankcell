package com.jimp.tanksgame.logic;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jimp.tanksgame.logic.utils.CollisionDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.jimp.tanksgame.logic.GameBoard.GameEndState.*;
import static com.jimp.tanksgame.logic.Tank.PlayerProperties.*;
import static com.jimp.tanksgame.logic.utils.GameConfiguration.*;

public class GameBoard {

    private final Player leftPlayer;
    private final Player rightPlayer;
    private final List<Colony> colonies;
    private final Bomb myBomb;
    private final GameTimer colonySpawningTimer;
    private final GameTimer sizeAndSpeedTimer;
    private final GameTimer valueIncrementTimer;
    private final GameTimer gamePlayTimer;
    private final GameTimer leftPlayerShootingTimer;
    private final GameTimer rightPlayerShootingTimer;
    private final int maxBullets;
    private final float deltaCellSize;
    private final float deltaCellVelocity;
    private final float deltaBulletSize;
    private final float deltaBulletVelocity;
    private float cellSize;
    private float cellVelocity;
    private float bulletSize;
    private float bulletVelocity;
    private float timeSinceLastFrame;

    public GameBoard(GameSettingsConfigurator configurator) {
        cellSize = configurator.getCellEdge();
        deltaCellSize = configurator.getDeltaCellEdge();
        cellVelocity = configurator.getCellVelocity();
        deltaCellVelocity = configurator.getDeltaCellVelocity();
        bulletSize = configurator.getBulletRadius();
        deltaBulletSize = configurator.getDeltaBulletRadius();
        bulletVelocity = configurator.getBulletVelocity();
        deltaBulletVelocity = configurator.getDeltaBulletVelocity();
        maxBullets = configurator.getMaxBullets();

        timeSinceLastFrame = 0f;

        leftPlayer = new Player(LEFT);
        rightPlayer = new Player(RIGHT);
        myBomb = new Bomb();
        colonies = new ArrayList<>();
        leftPlayerShootingTimer = new GameTimer(0.15f);
        rightPlayerShootingTimer = new GameTimer(0.15f);
        colonySpawningTimer = new GameTimer(2f);
        colonySpawningTimer.reset();
        sizeAndSpeedTimer = new GameTimer(configurator.getTimeToDecreaseSizeAndSpeed());
        valueIncrementTimer = new GameTimer(configurator.getTimeToIncreaseCellValues());
        gamePlayTimer = new GameTimer(configurator.getTimeOfPlay());
    }

    public GameEndState updateGame(float deltaTime, boolean leftPlayerShot, boolean rightPlayerShot) {
        timeSinceLastFrame = deltaTime;
        if (!gamePlayTimer.update(timeSinceLastFrame)) {
            processPlayerShots(leftPlayerShot, rightPlayerShot, deltaTime);
            moveBullets();
            if (checkIfHitBomb(leftPlayer)) {
                return RIGHT_WON_BOMB;
            } else if (checkIfHitBomb(rightPlayer)) {
                return LEFT_WON_BOMB;
            }
            checkPlayerHits();
            updateValues();
            spawnNewColony();
            moveColonies();
            cleanWastedBullets();
            cleanDeadColonies();
            return CONTINUE;
        } else {
            if (leftPlayer.getScore() > rightPlayer.getScore()) {
                return LEFT_WON_TIME;
            } else if (rightPlayer.getScore() > leftPlayer.getScore()) {
                return RIGHT_WON_TIME;
            } else
                return DRAW;
        }
    }

    private void processPlayerShots(boolean leftPlayerShot, boolean rightPlayerShot, float deltaTime) {
        if (leftPlayerShot && leftPlayerShootingTimer.update(deltaTime))
            leftPlayer.shootBullet(bulletVelocity, bulletSize, maxBullets);
        if (rightPlayerShot && rightPlayerShootingTimer.update(deltaTime))
            rightPlayer.shootBullet(bulletVelocity, bulletSize, maxBullets);
    }

    private void spawnNewColony() {
        if (colonySpawningTimer.update(timeSinceLastFrame)) {
            List<Vector2> possibleCenterCellPoints = findFreeSpaceForNewColony();
            if (possibleCenterCellPoints.size() > 1) {
                int i = ThreadLocalRandom.current().nextInt(0, possibleCenterCellPoints.size() - 1);
                colonies.add(new Colony(cellSize, possibleCenterCellPoints.get(i).x, possibleCenterCellPoints.get(i).y));
            } else if (possibleCenterCellPoints.size() == 1) {
                colonies.add(new Colony(cellSize, possibleCenterCellPoints.get(0).x, possibleCenterCellPoints.get(0).y));
            }
        }
    }

    private List<Vector2> findFreeSpaceForNewColony() {
        List<Vector2> possibleFreeSpacePositions = new ArrayList<>();

        Rectangle testingArea = new Rectangle();
        testingArea.setSize(3 * cellSize, 3 * cellSize);

        int numberOfTests = (int) ((GAME_BOARD_WIDTH - 2 * PLAYER_SPACE) / testingArea.getWidth());
        float offset = ((GAME_BOARD_WIDTH - 2 * PLAYER_SPACE) % testingArea.getWidth()) + GAME_BOARD_LEFT_EDGE + PLAYER_SPACE;

        for (int i = 0; i < numberOfTests; i++) {
            testingArea.setPosition(i * testingArea.getWidth() + offset, GAME_BOARD_UPPER_EDGE);
            boolean isFree = true;
            outerLoop:
            for (Colony colony : colonies) {
                for (Cell cell : colony.getCells()) {
                    if (CollisionDetector.cellsOverlap(testingArea, cell.getCellRectangle())) {
                        isFree = false;
                        break outerLoop;
                    }
                }
            }
            if (isFree) {
                possibleFreeSpacePositions.add(new Vector2(testingArea.getX() + cellSize, testingArea.getY() + cellSize));
            }
        }
        return possibleFreeSpacePositions;
    }

    private void moveColonies() {
        for (Colony colonyToMove : colonies) colonyToMove.move(timeSinceLastFrame, cellVelocity);
    }

    private boolean checkIfHitBomb(Player player) {
        for (Bullet bullet : player.getShotBullets()) {
            if (CollisionDetector.bulletOverlapsBombEdge(bullet.getBulletBody(), myBomb.getBombEdge())) {
                bullet.wasteBullet();
                return true;
            }
            if (CollisionDetector.bulletOverlapsCell(bullet.getBulletBody(), myBomb.getBombRectangle()))
                bullet.wasteBullet();
        }
        return false;
    }

    private void updateValues() {
        if (sizeAndSpeedTimer.update(timeSinceLastFrame)) {
            bulletVelocity += deltaBulletVelocity;
            cellVelocity += deltaCellVelocity;
            cellSize = cellSize > 4f + deltaCellSize ? cellSize - deltaCellSize : cellSize;
            bulletSize = bulletSize > 4f + deltaBulletSize ? bulletSize - deltaBulletSize : bulletSize;
        }
        if (valueIncrementTimer.update(timeSinceLastFrame)) {
            incrementCellValues();
        }
    }

    private void incrementCellValues() {
        for (Colony colony : colonies) colony.increaseColonyValue();
    }

    private void moveBullets() {
        movePlayerBullets(leftPlayer);
        movePlayerBullets(rightPlayer);
    }

    private void movePlayerBullets(Player player) {
        for (Bullet bullet : player.getShotBullets()) bullet.move(timeSinceLastFrame, bulletVelocity);
    }

    private void checkPlayerHits() {
        checkHits(leftPlayer);
        checkHits(rightPlayer);
    }

    private void checkHits(Player player) {
        for (Bullet bullet : player.getShotBullets()) {
            for (Colony colony : colonies) {
                colony.getCells().forEach(cell -> checkIfHitAndProcess(bullet, cell));
                if (colony.justKilled()) {
                    player.increaseScoreBy(colony.getTotalStartingValue());
                    colony.setAlreadyDead();
                }
            }
        }
    }

    private void checkIfHitAndProcess(Bullet bullet, Cell cell) {
        if (cell.isAlive() && CollisionDetector.bulletOverlapsCell(bullet.getBulletBody(), cell.getCellRectangle())) {
            cell.decreaseCurrentValue();
            bullet.wasteBullet();
        }
    }

    private void cleanDeadColonies() {
        colonies.removeIf(Colony::allCellsAreDead);
    }

    private void cleanWastedBullets() {
        cleanWastedPlayerBullets(leftPlayer);
        cleanWastedPlayerBullets(rightPlayer);
    }

    private void cleanWastedPlayerBullets(Player player) {
        player.getShotBullets().removeIf(Bullet::isWasted);
    }

    public Player getLeftPlayer() {
        return leftPlayer;
    }

    public Player getRightPlayer() {
        return rightPlayer;
    }

    public int getRemainingMinutes() {
        return (int) (gamePlayTimer.getRemainingTime() / 60f);
    }

    public int getRemainingSeconds() {
        return (int) gamePlayTimer.getRemainingTime() % 60;
    }

    public List<Drawable> getDrawables() {
        List<Drawable> myDrawables = new ArrayList<>();

        myDrawables.add(myBomb);
        myDrawables.add(leftPlayer);
        myDrawables.add(rightPlayer);
        myDrawables.addAll(leftPlayer.getShotBullets());
        myDrawables.addAll(rightPlayer.getShotBullets());
        List<Cell> cellList = colonies.stream().flatMap(colony -> colony.getCells().stream()).filter(Cell::isAlive).collect(Collectors.toList());
        myDrawables.addAll(cellList);

        return myDrawables;
    }

    public enum GameEndState {
        LEFT_WON_TIME,
        RIGHT_WON_TIME,
        LEFT_WON_BOMB,
        RIGHT_WON_BOMB,
        DRAW,
        CONTINUE
    }
}
