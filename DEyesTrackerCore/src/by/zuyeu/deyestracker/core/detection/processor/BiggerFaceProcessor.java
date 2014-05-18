/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.processor;

import org.opencv.core.Rect;

/**
 *
 * @author Fieryphoenix
 */
public class BiggerFaceProcessor implements IProcessor<Rect[], Rect> {

    @Override
    public Rect process(Rect[] input) {
        if (input == null || input.length == 0) {
            return null;
        }
        // default if length = 0
        int mainFaceIndex = 0;
        double maxArea = 0;
        for (int i = 0; (i < input.length) && (input.length > 1); i++) {
            final Rect rect = input[i];
            final double area = rect.area();
            if (area > maxArea) {
                maxArea = area;
                mainFaceIndex = i;
            }
        }
        return input[mainFaceIndex];
    }

}
