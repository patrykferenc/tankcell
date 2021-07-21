package com.jimp.tanksgame.logic.cells;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.jimp.tanksgame.logic.utils.GameConfiguration.MAX_CELLS_IN_COLONY;
import static com.jimp.tanksgame.logic.utils.GameConfiguration.MIN_CELLS_IN_COLONY;

public class Colony {

    private static final int[][] ADJACENT_CELLS = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
    private final List<Cell> cells;
    private int totalStartingValue;
    private boolean isAlreadyDead;

    public Colony(float cellSize, float centerX, float centerY) {
        cells = new ArrayList<>(MAX_CELLS_IN_COLONY);
        cells.add(new Cell(centerX, centerY, cellSize));

        int numStartingCells = ThreadLocalRandom.current().nextInt(MIN_CELLS_IN_COLONY, MAX_CELLS_IN_COLONY);
        while (cells.size() < numStartingCells) {
            for (int i = 0; (i < 8) && (cells.size() < numStartingCells); i++) {
                if (ThreadLocalRandom.current().nextBoolean()) {
                    float newX = ADJACENT_CELLS[i][1] * cellSize + centerX;
                    float newY = ADJACENT_CELLS[i][0] * cellSize + centerY;

                    cells.add(new Cell(newX, newY, cellSize));
                }
            }
        }
        totalStartingValue = 0;
        for (Cell cell : cells) totalStartingValue += cell.getStartingValue();
        isAlreadyDead = false;
    }

    public boolean allCellsAreDead() {
        return cells.stream().noneMatch(cell -> cell.getCurrentValue() != 0);
    }

    public boolean justKilled() {
        return allCellsAreDead() && !isAlreadyDead();
    }

    public boolean isAlreadyDead() {
        return isAlreadyDead;
    }

    public void setAlreadyDead() {
        isAlreadyDead = true;
    }

    public void increaseColonyValue() {
        for (Cell cell : cells) cell.increaseCurrentValue();
    }

    public int getTotalStartingValue() {
        return totalStartingValue;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void move(float deltaTime, float velocity) {
        for (Cell cell : cells) cell.move(deltaTime, velocity);
    }
}
