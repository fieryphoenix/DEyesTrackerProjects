/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.processor;

import java.util.Arrays;
import java.util.Optional;
import org.opencv.core.Rect;

/**
 *
 * @author Fieryphoenix
 */
public class RightEyeProcessor implements IProcessor<Rect> {

    @Override
    public Rect[] process(Rect[] input) {
        if (input.length < 2) {
            return input;
        }
        final Optional<Rect> leftEye = Arrays.stream(input).min((Rect o1, Rect o2) -> {
            if (o1.x > o2.x) {
                return 1;
            } else if (o1.x < o2.x) {
                return -1;
            } else {
                return 0;
            }
        });
        return new Rect[]{leftEye.get()};
    }

}
