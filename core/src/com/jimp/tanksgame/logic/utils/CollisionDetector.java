package com.jimp.tanksgame.logic.utils;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public final class CollisionDetector {

    private CollisionDetector() {
    }

    public static boolean bulletOverlapsBombEdge(Circle circle, Rectangle rectangle) {
        return (Intersector.overlaps(circle, rectangle) && circle.y > rectangle.y + rectangle.height);
    }
}
