package com.jimp.tanksgame.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameSettingsConfigurator {

    private final float bulletVelocity;
    private final float deltaBulletVelocity;
    private final float cellVelocity;
    private final float deltaCellVelocity;
    private final float bulletRadius;
    private final float deltaBulletRadius;
    private final float cellEdge;
    private final float deltaCellEdge;
    private final float timeToDecreaseSizeAndSpeed;
    private final float timeToIncreaseCellValues;
    private final float timeOfPlay;
    private final int maxBullets;

    public GameSettingsConfigurator() {
        bulletVelocity = 800f;
        deltaBulletVelocity = 50f;
        cellVelocity = 80f;
        deltaCellVelocity = 2f;
        bulletRadius = 20f;
        deltaBulletRadius = 1f;
        cellEdge = 100f;
        deltaCellEdge = 4f;
        timeToDecreaseSizeAndSpeed = 10f;
        timeToIncreaseCellValues = 5f;
        timeOfPlay = 180f;
        maxBullets = 9;
    }

    public GameSettingsConfigurator(String pathToConfig) {
        String myFileContent = "";
        try {
            myFileContent = new String(Files.readAllBytes(Paths.get(pathToConfig)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] textValues = myFileContent.split("\\r?\\n");
        bulletVelocity = checkAndAssignValue(getTextValue(0, textValues), 100f, 10000f, 800f);
        deltaBulletVelocity = checkAndAssignValue(getTextValue(1, textValues), 0.001f, 1000f, 20f);
        cellVelocity = checkAndAssignValue(getTextValue(2, textValues), 1f, 1000f, 60f);
        deltaCellVelocity = checkAndAssignValue(getTextValue(3, textValues), 0.001f, 100f, 2f);
        bulletRadius = checkAndAssignValue(getTextValue(4, textValues), 2f, 64f, 24f);
        deltaBulletRadius = checkAndAssignValue(getTextValue(5, textValues), 0.001f, 32f, 1f);
        cellEdge = checkAndAssignValue(getTextValue(6, textValues), 2f, 200f, 128f);
        deltaCellEdge = checkAndAssignValue(getTextValue(7, textValues), 0.001f, 100f, 4f);
        timeToDecreaseSizeAndSpeed = checkAndAssignValue(getTextValue(8, textValues), 0.001f, 1000f, 12f);
        timeToIncreaseCellValues = checkAndAssignValue(getTextValue(9, textValues), 0.001f, 1000f, 6f);
        timeOfPlay = checkAndAssignValue(getTextValue(10, textValues), 2f, 2000f, 180f);
        maxBullets = checkAndAssignValue(getTextValue(11, textValues), 1, 20, 9);
    }

    private float checkAndAssignValue(String stringToConvert, float minValue, float maxValue, float defaultValue) {
        float value;
        if (stringToConvert != null) {
            try {
                value = Float.parseFloat(stringToConvert);
                value = (value < minValue || value > maxValue) ? defaultValue : value;
            } catch (NumberFormatException exception) {
                value = defaultValue;
            }
        } else
            value = defaultValue;
        return value;
    }

    private int checkAndAssignValue(String stringToConvert, int minValue, int maxValue, int defaultValue) {
        int value;
        if (stringToConvert != null) {
            try {
                value = Integer.parseInt(stringToConvert);
                value = (value < minValue || value > maxValue) ? defaultValue : value;
            } catch (NumberFormatException exception) {
                value = defaultValue;
            }
        } else
            value = defaultValue;
        return value;
    }

    private String getTextValue(int index, String[] textValues) {
        String textValue;
        try {
            textValue = textValues[index];
        } catch (IndexOutOfBoundsException exception) {
            textValue = null;
        }
        return textValue;
    }

    public float getBulletVelocity() {
        return bulletVelocity;
    }

    public float getDeltaBulletVelocity() {
        return deltaBulletVelocity;
    }

    public float getCellVelocity() {
        return cellVelocity;
    }

    public float getDeltaCellVelocity() {
        return deltaCellVelocity;
    }

    public float getBulletRadius() {
        return bulletRadius;
    }

    public float getDeltaBulletRadius() {
        return deltaBulletRadius;
    }

    public float getCellEdge() {
        return cellEdge;
    }

    public float getDeltaCellEdge() {
        return deltaCellEdge;
    }

    public float getTimeToDecreaseSizeAndSpeed() {
        return timeToDecreaseSizeAndSpeed;
    }

    public float getTimeToIncreaseCellValues() {
        return timeToIncreaseCellValues;
    }

    public float getTimeOfPlay() {
        return timeOfPlay;
    }

    public int getMaxBullets() {
        return maxBullets;
    }
}
