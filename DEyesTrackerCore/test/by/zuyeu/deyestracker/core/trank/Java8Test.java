/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.trank;

import java.util.Arrays;
import org.junit.Test;
import org.opencv.core.Point;
import org.opencv.core.Rect;

/**
 *
 * @author Fieryphoenix
 */
public class Java8Test {

    public Java8Test() {
    }

    @Test
    public void goTest() {
        Rect[] t = new Rect[10];
        for (int i = 0; i < 10; i++) {
            t[i] = new Rect(new Point(i, i), new Point(i + 10, i + 10));
        }
        printArray(t);
        Arrays.stream(t).forEach(p -> {
            p.x += 100;
            p.y += 200;
        });
        printArray(t);
    }

    private static void printArray(Rect[] t) {
        Arrays.stream(t).forEach(p
                -> System.out.println(" elem = " + p));
    }
}
