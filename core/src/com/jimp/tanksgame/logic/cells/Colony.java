package com.jimp.tanksgame.logic.cells;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import static com.jimp.tanksgame.logic.utils.GameConfiguration.*;

public class Colony {

    private static final int[][] ADJACENT_CELLS = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
    private static int numberOfColonies;
    private final Map<Integer, Cell> cells;
    private final int id;
    private int totalStartingValue;
    private boolean isAlreadyDead;
    private int numberOfCells;

    public Colony(float cellSize, float centerX, float centerY) {
        id = ++numberOfColonies;
        cells = new TreeMap<>();
        cells.put(++numberOfCells, new Cell(centerX, centerY, cellSize, numberOfCells));

        int numStartingCells = ThreadLocalRandom.current().nextInt(MIN_CELLS_IN_COLONY, MAX_CELLS_IN_COLONY);
        while (cells.size() < numStartingCells) {
            for (int i = 0; (i < 8) && (cells.size() < numStartingCells); i++) {
                if (ThreadLocalRandom.current().nextBoolean()) {
                    float newX = ADJACENT_CELLS[i][1] * cellSize + centerX;
                    float newY = ADJACENT_CELLS[i][0] * cellSize + centerY;

                    cells.put(++numberOfCells, new Cell(newX, newY, cellSize, numberOfCells));
                }
            }
        }
        totalStartingValue = 0;
        //TODO: see if can be sped up
        for (Cell cell : cells.values()) totalStartingValue += cell.getStartingValue();
        isAlreadyDead = false;
    }

    public boolean allCellsAreDead() {
        return cells.values().stream().noneMatch(cell -> cell.getCurrentValue() != 0);
    }

    public boolean canBeSafelyCleared() {
        return allCellsAreDead() || colonyOutsideOfGameBoard();
    }

    public boolean colonyOutsideOfGameBoard() {
        for (Cell cell : cells.values()) {
            if (cell.getCellRectangle().getY() > (GAME_BOARD.getY() + GAME_BOARD.getHeight())
                    || cell.getCellRectangle().overlaps(GAME_BOARD))
                return false;
        }
        return true;
    }

    public boolean justKilled() {
        return allCellsAreDead() && !isAlreadyDead();
    }

    public boolean isAlreadyDead() {
        return isAlreadyDead;
    }

    public void makeAlreadyDead() {
        isAlreadyDead = true;
    }

    public void increaseColonyValue() {
        for (Cell cell : cells.values()) cell.increaseCurrentValue();
    }

    public int getTotalStartingValue() {
        return totalStartingValue;
    }

    public Map<Integer, Cell> getCells() {
        return cells;
    }

    public Cell getCentralCell() {
        Cell centralCell = null;
        for (Cell cell : cells.values())
            if (cell.getId() == 1) {
                centralCell = cell;
            }
        return centralCell;
    }

    public void move(float deltaTime, float velocity) {
        for (Cell cell : cells.values()) cell.move(deltaTime, velocity);
    }

    public int getId() {
        return id;
    }
}
