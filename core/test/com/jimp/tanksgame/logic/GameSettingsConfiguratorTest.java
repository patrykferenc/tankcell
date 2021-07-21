package com.jimp.tanksgame.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameSettingsConfiguratorTest {

    @Disabled("Disabled until the settings are reworked")
    @Test
    void testNormalFile() {

        File testFile = new File("D:\\Documents\\Programowanie\\JIMP2\\projekt\\tanks\\core\\test\\com\\jimp\\tanksgame\\logic\\testResources\\normal.txt");
        String absolutePathToFile = testFile.getPath();

        final GameSettingsConfigurator myTestingConfigurator = new GameSettingsConfigurator(absolutePathToFile);

        assertAll(
                () -> assertEquals(1000, myTestingConfigurator.getBulletVelocity()),
                () -> assertEquals(50, myTestingConfigurator.getDeltaBulletVelocity()),
                () -> assertEquals(60, myTestingConfigurator.getCellVelocity()),
                () -> assertEquals(4, myTestingConfigurator.getDeltaCellVelocity()),
                () -> assertEquals(20, myTestingConfigurator.getBulletRadius()),
                () -> assertEquals(1, myTestingConfigurator.getDeltaBulletRadius()),
                () -> assertEquals(124, myTestingConfigurator.getCellEdge()),
                () -> assertEquals(4, myTestingConfigurator.getDeltaCellEdge()),
                () -> assertEquals(12, myTestingConfigurator.getTimeToDecreaseSizeAndSpeed()),
                () -> assertEquals(6, myTestingConfigurator.getTimeToIncreaseCellValues()),
                () -> assertEquals(61, myTestingConfigurator.getTimeOfPlay()),
                () -> assertEquals(9, myTestingConfigurator.getMaxBullets())

        );
    }

    @Disabled("Disabled until the settings are reworked")
    @Test
    void testBrokenFile_text() {

        File testFile = new File("D:\\Documents\\Programowanie\\JIMP2\\projekt\\tanks\\core\\test\\com\\jimp\\tanksgame\\logic\\testResources\\broken_text.txt");
        String absolutePathToFile = testFile.getPath();

        final GameSettingsConfigurator myTestingConfigurator = new GameSettingsConfigurator(absolutePathToFile);

        assertAll(
                () -> assertEquals(800, myTestingConfigurator.getBulletVelocity()),
                () -> assertEquals(50, myTestingConfigurator.getDeltaBulletVelocity()),
                () -> assertEquals(60, myTestingConfigurator.getCellVelocity()),
                () -> assertEquals(4, myTestingConfigurator.getDeltaCellVelocity()),
                () -> assertEquals(20, myTestingConfigurator.getBulletRadius()),
                () -> assertEquals(1, myTestingConfigurator.getDeltaBulletRadius()),
                () -> assertEquals(124, myTestingConfigurator.getCellEdge()),
                () -> assertEquals(4, myTestingConfigurator.getDeltaCellEdge()),
                () -> assertEquals(12, myTestingConfigurator.getTimeToDecreaseSizeAndSpeed()),
                () -> assertEquals(6, myTestingConfigurator.getTimeToIncreaseCellValues()),
                () -> assertEquals(61, myTestingConfigurator.getTimeOfPlay()),
                () -> assertEquals(9, myTestingConfigurator.getMaxBullets())
        );
    }

    @Disabled("Disabled until the settings are reworked")
    @Test
    void testBrokenFile_empty() {
        File testFile = new File("D:\\Documents\\Programowanie\\JIMP2\\projekt\\tanks\\core\\test\\com\\jimp\\tanksgame\\logic\\testResources\\empty.txt");
        String absolutePathToFile = testFile.getPath();

        final GameSettingsConfigurator myTestingConfigurator = new GameSettingsConfigurator(absolutePathToFile);

        assertAll(
                () -> assertEquals(800, myTestingConfigurator.getBulletVelocity()),
                () -> assertEquals(20, myTestingConfigurator.getDeltaBulletVelocity()),
                () -> assertEquals(60, myTestingConfigurator.getCellVelocity()),
                () -> assertEquals(2, myTestingConfigurator.getDeltaCellVelocity()),
                () -> assertEquals(24, myTestingConfigurator.getBulletRadius()),
                () -> assertEquals(1, myTestingConfigurator.getDeltaBulletRadius()),
                () -> assertEquals(128, myTestingConfigurator.getCellEdge()),
                () -> assertEquals(4, myTestingConfigurator.getDeltaCellEdge()),
                () -> assertEquals(12, myTestingConfigurator.getTimeToDecreaseSizeAndSpeed()),
                () -> assertEquals(6, myTestingConfigurator.getTimeToIncreaseCellValues()),
                () -> assertEquals(180, myTestingConfigurator.getTimeOfPlay()),
                () -> assertEquals(9, myTestingConfigurator.getMaxBullets())

        );
    }

    @Disabled("Disabled until the settings are reworked")
    @Test
    void testGoodFile_minAndMaxValues() {

        File testFile = new File("D:\\Documents\\Programowanie\\JIMP2\\projekt\\tanks\\core\\test\\com\\jimp\\tanksgame\\logic\\testResources\\broken_values.txt");
        String absolutePathToFile = testFile.getPath();

        final GameSettingsConfigurator myTestingConfigurator = new GameSettingsConfigurator(absolutePathToFile);

        assertAll(
                () -> assertEquals(800, myTestingConfigurator.getBulletVelocity()),
                () -> assertEquals(20, myTestingConfigurator.getDeltaBulletVelocity()),
                () -> assertEquals(60, myTestingConfigurator.getCellVelocity()),
                () -> assertEquals(4.128, myTestingConfigurator.getDeltaCellVelocity(), 0.001),
                () -> assertEquals(64, myTestingConfigurator.getBulletRadius()),
                () -> assertEquals(2.0, myTestingConfigurator.getDeltaBulletRadius(), 0.001),
                () -> assertEquals(124, myTestingConfigurator.getCellEdge()),
                () -> assertEquals(4, myTestingConfigurator.getDeltaCellEdge()),
                () -> assertEquals(12, myTestingConfigurator.getTimeToDecreaseSizeAndSpeed()),
                () -> assertEquals(6, myTestingConfigurator.getTimeToIncreaseCellValues()),
                () -> assertEquals(180, myTestingConfigurator.getTimeOfPlay()),
                () -> assertEquals(9, myTestingConfigurator.getMaxBullets())
        );
    }

    @Disabled("Disabled until the settings are reworked")
    @Test
    void testBrokenFile_weirdLayout() {

        File testFile = new File("D:\\Documents\\Programowanie\\JIMP2\\projekt\\tanks\\core\\test\\com\\jimp\\tanksgame\\logic\\testResources\\weird_layout.txt");
        String absolutePathToFile = testFile.getPath();

        final GameSettingsConfigurator myTestingConfigurator = new GameSettingsConfigurator(absolutePathToFile);

        assertAll(
                () -> assertEquals(800, myTestingConfigurator.getBulletVelocity()),
                () -> assertEquals(60, myTestingConfigurator.getDeltaBulletVelocity()),
                () -> assertEquals(60, myTestingConfigurator.getCellVelocity()),
                () -> assertEquals(1, myTestingConfigurator.getDeltaCellVelocity(), 0.001),
                () -> assertEquals(24, myTestingConfigurator.getBulletRadius()),
                () -> assertEquals(4, myTestingConfigurator.getDeltaBulletRadius(), 0.001),
                () -> assertEquals(12, myTestingConfigurator.getCellEdge()),
                () -> assertEquals(6, myTestingConfigurator.getDeltaCellEdge()),
                () -> assertEquals(61, myTestingConfigurator.getTimeToDecreaseSizeAndSpeed()),
                () -> assertEquals(6, myTestingConfigurator.getTimeToIncreaseCellValues()),
                () -> assertEquals(180, myTestingConfigurator.getTimeOfPlay()),
                () -> assertEquals(20, myTestingConfigurator.getMaxBullets())
        );
    }
}