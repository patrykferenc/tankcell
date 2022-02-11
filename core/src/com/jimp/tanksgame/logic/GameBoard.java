package com.jimp.tanksgame.logic;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jimp.tanksgame.TanksGame;
import com.jimp.tanksgame.logic.bullets.Bullet;
import com.jimp.tanksgame.logic.cells.Bomb;
import com.jimp.tanksgame.logic.cells.Cell;
import com.jimp.tanksgame.logic.cells.Colony;
import com.jimp.tanksgame.logic.tanks.ComputerTank;
import com.jimp.tanksgame.logic.tanks.Player;
import com.jimp.tanksgame.logic.tanks.Tank;
import com.jimp.tanksgame.logic.utils.CollisionDetector;
import com.jimp.tanksgame.logic.utils.GameTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.jimp.tanksgame.logic.GameBoard.GameEndState.*;
import static com.jimp.tanksgame.logic.tanks.Tank.PlayerProperties.LEFT;
import static com.jimp.tanksgame.logic.tanks.Tank.PlayerProperties.RIGHT;
import static com.jimp.tanksgame.logic.utils.GameConfiguration.*;

public class GameBoard {

    private final Tank leftPlayer;
    private final Tank rightPlayer;
    private final Map<Integer, Colony> colonies;
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
    private TanksGame.GameMode mode;

    public GameBoard(Preferences gameSettings, TanksGame.GameMode mode) {
        cellSize = gameSettings.getFloat("cellEdge");
        deltaCellSize = gameSettings.getFloat("deltaCellEdge");
        cellVelocity = gameSettings.getFloat("cellVelocity");
        deltaCellVelocity = gameSettings.getFloat("deltaCellVelocity");
        bulletSize = gameSettings.getFloat("bulletRadius");
        deltaBulletSize = gameSettings.getFloat("deltaBulletRadius");
        bulletVelocity = gameSettings.getFloat("bulletVelocity");
        deltaBulletVelocity = gameSettings.getFloat("deltaBulletVelocity");
        maxBullets = gameSettings.getInteger("maxBullets");

        timeSinceLastFrame = 0f;

        this.mode = mode;

        leftPlayer = new Player(LEFT);
        switch (this.mode) {
            case SINGLEPLAYER:
                rightPlayer = new ComputerTank(RIGHT, Difficulty.EASY);
                break;
            case MULTIPLAYER:
                rightPlayer = new Player(RIGHT);
                break;
            default:
                rightPlayer = new Player(RIGHT);
        }
        myBomb = new Bomb();
        colonies = new TreeMap<>();
        leftPlayerShootingTimer = new GameTimer(0.12f);
        rightPlayerShootingTimer = new GameTimer(0.12f);
        colonySpawningTimer = new GameTimer(1.2f);
        colonySpawningTimer.reset();
        sizeAndSpeedTimer = new GameTimer(gameSettings.getFloat("timeToDecreaseSizeAndSpeed"));
        valueIncrementTimer = new GameTimer(gameSettings.getFloat("timeToIncreaseCellValues"));
        gamePlayTimer = new GameTimer(gameSettings.getFloat("timeOfPlay"));
    }

    public GameEndState updateGame(float deltaTime, boolean leftPlayerShot, boolean rightPlayerShot) {
        timeSinceLastFrame = deltaTime;
        if (!gamePlayTimer.update(timeSinceLastFrame)) {
            if (mode == TanksGame.GameMode.SINGLEPLAYER) {
                ComputerTank ai = (ComputerTank) rightPlayer;
                ai.updateState(deltaTime, bulletVelocity, bulletSize, maxBullets, colonies, rightPlayerShootingTimer.isJustPassed());
            }
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
        if (leftPlayerShootingTimer.update(deltaTime) && leftPlayerShot)
            leftPlayer.shootBullet(bulletVelocity, bulletSize, maxBullets);
        if (rightPlayerShootingTimer.update(deltaTime) && rightPlayerShot)
            rightPlayer.shootBullet(bulletVelocity, bulletSize, maxBullets);
    }

    private void spawnNewColony() {
        if (colonySpawningTimer.update(timeSinceLastFrame)) {
            List<Vector2> possibleCenterCellPoints = findFreeSpaceForNewColony();
            if (possibleCenterCellPoints.size() > 1) {
                int i = ThreadLocalRandom.current().nextInt(0, possibleCenterCellPoints.size() - 1);
                Colony newColony = new Colony(cellSize, possibleCenterCellPoints.get(i).x, possibleCenterCellPoints.get(i).y);
                colonies.put(newColony.getId(), newColony);
            } else if (possibleCenterCellPoints.size() == 1) {
                Colony newColony = new Colony(cellSize, possibleCenterCellPoints.get(0).x, possibleCenterCellPoints.get(0).y);
                colonies.put(newColony.getId(), newColony);
            }
        }
    }

    private List<Vector2> findFreeSpaceForNewColony() {
        List<Vector2> possibleFreeSpacePositions = new ArrayList<>();

        float spacing = 24f; // helps to spread the colonies out a little.

        // this is an area that needs to be free for a colony to spawn.
        Rectangle testingArea = new Rectangle();
        testingArea.setSize(3 * cellSize + spacing, 3 * cellSize + spacing);

        int numberOfTests = (int) ((GAME_BOARD.getWidth() - 2 * PLAYER_SPACE) / testingArea.getWidth());
        float offset = ((GAME_BOARD.getWidth() - 2 * PLAYER_SPACE) % testingArea.getWidth()) + GAME_BOARD.getX() + PLAYER_SPACE;

        for (int i = 0; i < numberOfTests; i++) {
            testingArea.setPosition(i * testingArea.getWidth() + offset + spacing, GAME_BOARD.getY() + GAME_BOARD.getHeight());
            boolean isFree = true;
            // TODO: CHECK IF CAN BE SIMPLIFIED
            outerLoop:
            for (Colony colony : colonies.values()) {
                for (Cell cell : colony.getCells().values()) {
                    if (Intersector.overlaps(testingArea, cell.getCellRectangle())) {
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
        // TODO: CHECK IF CAN BE SPED UP
        for (Colony colonyToMove : colonies.values()) colonyToMove.move(timeSinceLastFrame, cellVelocity);
    }

    private boolean checkIfHitBomb(Tank player) {
        for (Bullet bullet : player.getShotBullets()) {
            if (CollisionDetector.bulletOverlapsBombEdge(bullet.getBulletBody(), myBomb.getBombEdge())) {
                bullet.wasteBullet();
                return true;
            }
            if (Intersector.overlaps(bullet.getBulletBody(), myBomb.getBombRectangle()))
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
        for (Colony colony : colonies.values()) colony.increaseColonyValue();
    }

    private void moveBullets() {
        movePlayerBullets(leftPlayer);
        movePlayerBullets(rightPlayer);
    }

    private void movePlayerBullets(Tank player) {
        for (Bullet bullet : player.getShotBullets()) bullet.move(timeSinceLastFrame, bulletVelocity);
    }

    private void checkPlayerHits() {
        checkHits(leftPlayer);
        checkHits(rightPlayer);
    }

    private void checkHits(Tank player) {
        for (Bullet bullet : player.getShotBullets()) {
            for (Colony colony : colonies.values()) {
                colony.getCells().values().forEach(cell -> checkIfHitAndProcess(bullet, cell));
                if (colony.justKilled()) {
                    player.increaseScoreBy(colony.getTotalStartingValue());
                    colony.makeAlreadyDead();
                }
            }
        }
    }

    private void checkIfHitAndProcess(Bullet bullet, Cell cell) {
        if (cell.isAlive() && Intersector.overlaps(bullet.getBulletBody(), cell.getCellRectangle())) {
            cell.decreaseCurrentValue();
            bullet.wasteBullet();
        }
    }

    private void cleanDeadColonies() {
        colonies.values().removeIf(Colony::canBeSafelyCleared);
    }

    private void cleanWastedBullets() {
        cleanWastedPlayerBullets(leftPlayer);
        cleanWastedPlayerBullets(rightPlayer);
    }

    private void cleanWastedPlayerBullets(Tank player) {
        player.getShotBullets().removeIf(Bullet::isWasted);
    }

    public Tank getLeftPlayer() {
        return leftPlayer;
    }

    public Tank getRightPlayer() {
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

        List<Cell> cellList = colonies.values().stream().flatMap(
                colony -> colony.getCells().values().stream()).filter(Cell::isAlive).collect(Collectors.toList()
        );
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
