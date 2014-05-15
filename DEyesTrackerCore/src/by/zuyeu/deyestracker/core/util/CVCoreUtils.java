/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.util;

import java.util.Arrays;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;

/**
 *
 * @author Fieryphoenix
 */
public class CVCoreUtils {

    public static Mat selectSubmatByRect(Rect rect, Mat image) {
        double colScale = 1.0 * image.cols() / image.width();
        int colStart = (int) (1.0 * rect.x * colScale);
        int colEnd = (int) (1.0 * (rect.x + rect.width) * colScale);
        double rowScale = 1.0 * image.rows() / image.height();
        int rowStart = (int) (1.0 * rect.y * rowScale);
        int rowEnd = (int) (1.0 * (rect.y + rect.height) * rowScale);

        return image.submat(rowStart, rowEnd, colStart, colEnd);
    }

    public static void fixRectTLFromSubmat(Rect[] eyes, Rect subRect) {
        final Point shiftPoint = subRect != null ? subRect.tl() : new Point(0, 0);
        Arrays.stream(eyes).forEach(p -> {
            p.x += shiftPoint.x;
            p.y += shiftPoint.y;
        });
    }

    public static Point fixPointFromSubmat(Point pointToFix, Rect subRect) {
        if (pointToFix == null) {
            return null;
        }
        pointToFix.x += (subRect == null) ? 0 : subRect.x;
        pointToFix.y += (subRect == null) ? 0 : subRect.y;
        return pointToFix;
    }

    public static void insertSubmatByRect(Mat subImage, Rect rect, Mat origImage) {
        double colScale = 1.0 * origImage.cols() / origImage.width();
        int colStart = (int) (1.0 * rect.x * colScale);
        double rowScale = 1.0 * origImage.rows() / origImage.height();
        int rowStart = (int) (1.0 * rect.y * rowScale);
        for (int x1 = 0, x2 = colStart; x1 < subImage.cols(); x1++, x2++) {
            for (int y1 = 0, y2 = rowStart; y1 < subImage.rows(); y1++, y2++) {
                final double[] subImgData = subImage.get(y1, x1);
                origImage.put(y2, x2, subImgData);
            }
        }
    }

    public static Point sumPoints(Point p1, Point p2) {
        if (p1 == null && p2 == null) {
            return null;
        }
        if (p1 == null && p2 != null) {
            return p2;
        }
        if (p2 == null && p1 != null) {
            return p1;
        }
        final Point sum = new Point();
        sum.x = p1.x + p2.x;
        sum.y = p1.y + p2.y;

        return sum;
    }

    public static Rect sumRect(Rect r1, Rect r2) {
        if (r1 == null && r2 == null) {
            return null;
        }
        if (r1 == null && r2 != null) {
            return r2;
        }
        if (r2 == null && r1 != null) {
            return r1;
        }
        final Rect sum = new Rect();
        sum.x = r1.x + r2.x;
        sum.y = r1.y + r2.y;
        sum.width = r1.width + r2.width;
        sum.height = r1.height + r2.height;

        return sum;
    }

    public static Point dividePoint(Point p, int divider) {
        final Point result = new Point();
        result.x = p.x / divider;
        result.y = p.y / divider;
        return result;
    }

    public static Rect divideRect(Rect p, int divider) {
        final Rect result = new Rect();
        result.x = p.x / divider;
        result.y = p.y / divider;
        result.width = p.width / divider;
        result.height = p.height / divider;
        return result;
    }
}
