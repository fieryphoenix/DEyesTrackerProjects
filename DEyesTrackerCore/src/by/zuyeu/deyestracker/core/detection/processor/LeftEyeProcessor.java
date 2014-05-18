/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.processor;

import by.zuyeu.deyestracker.core.util.comparator.RectXComparator;
import java.util.Arrays;
import java.util.Optional;
import org.opencv.core.Rect;

/**
 *
 * @author Fieryphoenix
 */
public class LeftEyeProcessor implements IProcessor<Rect[], Rect[]> {

    private final RectXComparator comparator = new RectXComparator();

    @Override
    public Rect[] process(Rect[] input) {
        if (input.length < 2) {
            return input;
        }
        final Optional<Rect> leftEye = Arrays.stream(input).max(comparator);
        return new Rect[]{leftEye.get()};
    }
}
