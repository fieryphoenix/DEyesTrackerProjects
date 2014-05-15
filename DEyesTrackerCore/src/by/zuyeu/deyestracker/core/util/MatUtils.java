/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.util;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

/**
 *
 * @author Fieryphoenix
 */
public class MatUtils {

    public static Mat selectSubmatByRect(Rect rect, Mat image) {
        double colScale = 1.0 * image.cols() / image.width();
        int colStart = (int) (1.0 * rect.x * colScale);
        int colEnd = (int) (1.0 * (rect.x + rect.width) * colScale);
        double rowScale = 1.0 * image.rows() / image.height();
        int rowStart = (int) (1.0 * rect.y * rowScale);
        int rowEnd = (int) (1.0 * (rect.y + rect.height) * rowScale);

        return image.submat(rowStart, rowEnd, colStart, colEnd);
    }
}
