package com.jimp.tanksgame.logic.utils;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public final class CollisionDetector {

    private CollisionDetector() {
    }

    public static boolean bulletOverlapsCell(Circle circle, Rectangle rectangle) {
        float closestPointX = circle.x;
        float closestPintY = circle.y;

        if (circle.x < rectangle.x) {
            closestPointX = rectangle.x;
        } else if (circle.x > rectangle.x + rectangle.width) {
            closestPointX = rectangle.x + rectangle.width;
        }

        if (circle.y < rectangle.y) {
            closestPintY = rectangle.y;
        } else if (circle.y > rectangle.y + rectangle.height) {
            closestPintY = rectangle.y + rectangle.height;
        }

        closestPointX -= circle.x;
        closestPointX *= closestPointX;
        closestPintY -= circle.y;
        closestPintY *= closestPintY;

        return closestPointX + closestPintY < circle.radius * circle.radius;
    }

    public static boolean cellsOverlap(Rectangle rectangle1, Rectangle rectangle2) {
        return (rectangle1.x < (rectangle2.x + rectangle2.width)) &&
                ((rectangle1.x + rectangle1.width) > rectangle2.x) &&
                (rectangle1.y < (rectangle2.y + rectangle2.height)) &&
                ((rectangle1.y + rectangle1.height) > rectangle2.y);
    }

    public static boolean bulletOverlapsBombEdge(Circle circle, Rectangle rectangle) {
        return (bulletOverlapsCell(circle, rectangle) && circle.y > rectangle.y + rectangle.height);
    }
}
