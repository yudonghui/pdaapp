package com.rfid.pdaapp.views;

/**
 * Created by ydh on 2022/1/4
 */
public class ResultPoint {

    private final float x;
    private final float y;

    public ResultPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public final float getX() {
        return x;
    }

    public final float getY() {
        return y;
    }

    @Override
    public final boolean equals(Object other) {
        if (other instanceof ResultPoint) {
            ResultPoint otherPoint = (ResultPoint) other;
            return x == otherPoint.x && y == otherPoint.y;
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return 31 * Float.floatToIntBits(x) + Float.floatToIntBits(y);
    }

    @Override
    public final String toString() {
        return "(" + x + ',' + y + ')';
    }

    /**
     * Orders an array of three ResultPoints in an order [A,B,C] such that AB is less than AC
     * and BC is less than AC, and the angle between BC and BA is less than 180 degrees.
     *
     * @param patterns array of three {@code ResultPoint} to order
     */
    public static void orderBestPatterns(ResultPoint[] patterns) {

        // Find distances between pattern centers
        float zeroOneDistance = distance(patterns[0], patterns[1]);
        float oneTwoDistance = distance(patterns[1], patterns[2]);
        float zeroTwoDistance = distance(patterns[0], patterns[2]);

        ResultPoint pointA;
        ResultPoint pointB;
        ResultPoint pointC;
        // Assume one closest to other two is B; A and C will just be guesses at first
        if (oneTwoDistance >= zeroOneDistance && oneTwoDistance >= zeroTwoDistance) {
            pointB = patterns[0];
            pointA = patterns[1];
            pointC = patterns[2];
        } else if (zeroTwoDistance >= oneTwoDistance && zeroTwoDistance >= zeroOneDistance) {
            pointB = patterns[1];
            pointA = patterns[0];
            pointC = patterns[2];
        } else {
            pointB = patterns[2];
            pointA = patterns[0];
            pointC = patterns[1];
        }

        // Use cross product to figure out whether A and C are correct or flipped.
        // This asks whether BC x BA has a positive z component, which is the arrangement
        // we want for A, B, C. If it's negative, then we've got it flipped around and
        // should swap A and C.
        if (crossProductZ(pointA, pointB, pointC) < 0.0f) {
            ResultPoint temp = pointA;
            pointA = pointC;
            pointC = temp;
        }

        patterns[0] = pointA;
        patterns[1] = pointB;
        patterns[2] = pointC;
    }

    /**
     * @param pattern1 first pattern
     * @param pattern2 second pattern
     * @return distance between two points
     */
    public static float distance(ResultPoint pattern1, ResultPoint pattern2) {
        return distance(pattern1.x, pattern1.y, pattern2.x, pattern2.y);
    }
    public static float distance(float aX, float aY, float bX, float bY) {
        float xDiff = aX - bX;
        float yDiff = aY - bY;
        return (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
    /**
     * Returns the z component of the cross product between vectors BC and BA.
     */
    private static float crossProductZ(ResultPoint pointA,
                                       ResultPoint pointB,
                                       ResultPoint pointC) {
        float bX = pointB.x;
        float bY = pointB.y;
        return ((pointC.x - bX) * (pointA.y - bY)) - ((pointC.y - bY) * (pointA.x - bX));
    }
}
