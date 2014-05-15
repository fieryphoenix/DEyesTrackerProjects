/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.comparator;

import java.util.Comparator;
import org.opencv.core.Rect;

/**
 *
 * @author Fieryphoenix
 */
public class RectXComparator implements Comparator<Rect> {

    @Override
    public int compare(Rect o1, Rect o2) {
        if (o1.x > o2.x) {
            return 1;
        } else if (o1.x < o2.x) {
            return -1;
        } else {
            return 0;
        }
    }

}
