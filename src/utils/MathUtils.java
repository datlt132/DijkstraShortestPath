package utils;

import java.awt.*;

public class MathUtils {
    /**
     * Calculate square
     * Input: x
     * Output: x^2
     */
    public static int square(int x) {
        return x * x;
    }

    /**
     * Calculate distance of 2 points
     * Input: point A(x1, y1), point B(x2, y2)
     * Output: ((x1-x2)^2 + (y1-y2)^2)^(1/2)
     */
    public static double getLengthOfSegment(Point A, Point B) {
        return Math.sqrt(square(A.x - B.x) + square(A.y - B.y));
    }

    /**
     * Calculate area of triangle
     * Input: point A(x1, y1), point B(x2, y2), point C(x3, y3)
     * Output: double area
     * Ref: https://www.mathvn.com/2015/11/cong-thuc-tinh-dien-tich-tam-giac-theo.html
     */
    public static double getAreaOfTriangle(Point A, Point B, Point C) {
        return 0.5f * Math.abs((B.x - A.x) * (C.y - A.y) - (C.x - A.x) * (B.y - A.y));
    }

    /**
     * Calculate distance of point A to segment BC
     * Input: point A(x1, y1), point B(x2, y2), point C(x3, y3)
     * Output: double distance
     */
    public static double getDistToSegment(Point A, Point B, Point C) {
        double triangleArea = getAreaOfTriangle(A, B, C);
        double BC = getLengthOfSegment(B, C);
        return (Math.abs(triangleArea) * 2f) / BC;
    }
}
