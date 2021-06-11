package com.jimp.tanksgame.logic.utils;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollisionDetectorTest {

    @Test
    void bulletOverlapsCell_noCollision_true() {
        Circle bullet = new Circle(90, 90, 10);
        Rectangle cell = new Rectangle(100, 100, 128, 128);

        assertFalse(CollisionDetector.bulletOverlapsCell(bullet, cell));
    }

    @Test
    void bulletOverlapsCell_collisionInside_true() {

        Circle bullet = new Circle(100, 100, 100);
        Rectangle cell = new Rectangle(100, 100, 200, 200);
        cell.setCenter(100, 100);

        assertTrue(CollisionDetector.bulletOverlapsCell(bullet, cell));
    }

    @Test
    void bulletOverlapsCell_collisionNormal_true() {
        Circle bullet = new Circle(90, 90, 15);
        Rectangle cell = new Rectangle(100, 100, 128, 128);

        assertTrue(CollisionDetector.bulletOverlapsCell(bullet, cell));
    }

    @Test
    void bulletOverlapsCell_noCollisionEdge_false() {
        Circle bullet = new Circle(0, 0, 20);
        Rectangle cell = new Rectangle(20, 0, 128, 128);

        assertFalse(CollisionDetector.bulletOverlapsCell(bullet, cell));
    }

    @Test
    void cellsOverlap_noCollisionEdge_false() {
        Rectangle cell1 = new Rectangle(0, 0, 20, 20);
        Rectangle cell2 = new Rectangle(20, 0, 128, 128);

        assertFalse(CollisionDetector.cellsOverlap(cell1, cell2));

    }

    @Test
    void cellsOverlap_oneInsideAnother_true() {
        Rectangle cell1 = new Rectangle(0, 0, 40, 40);
        Rectangle cell2 = new Rectangle(20, 0, 20, 20);

        assertTrue(CollisionDetector.cellsOverlap(cell1, cell2));
    }


    @Test
    void bulletOverlapsBombEdge_fromSide_false() {
        Circle bullet = new Circle(128, 128, 21);
        Rectangle bomb = new Rectangle(20, 0, 128, 128);

        assertFalse(CollisionDetector.bulletOverlapsBombEdge(bullet, bomb));
    }

    @Test
    void bulletOverlapsBombEdge_fromUp_true() {
        Circle bullet = new Circle(130, 130, 22);
        Rectangle bomb = new Rectangle(20, 0, 128, 128);

        assertTrue(CollisionDetector.bulletOverlapsBombEdge(bullet, bomb));
    }
}