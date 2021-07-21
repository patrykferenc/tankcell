package com.jimp.tanksgame.logic.utils;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollisionDetectorTest {

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