/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.video.util;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import org.opencv.core.Size;

/**
 *
 * @author Fieryphoenix
 */
public class ScreenUtils {

    public static Size getScreenSize() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        return new Size(width, height);
    }
}
