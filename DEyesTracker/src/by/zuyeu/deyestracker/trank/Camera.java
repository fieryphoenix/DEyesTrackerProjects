/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.trank;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

/**
 *
 * @author Fieryphoenix
 */
public class Camera {

    public static void main(String[] args) throws InterruptedException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        VideoCapture camera = new VideoCapture(0);
        Thread.sleep(1000L);
        camera.open(0); //Useless
        if (!camera.isOpened()) {
            System.out.println("Camera Error");
        } else {
            System.out.println("Camera OK?");
        }
        Mat frame = new Mat();

        //camera.grab();
        //System.out.println("Frame Grabbed");
        //camera.retrieve(frame);
        //System.out.println("Frame Decoded");
        camera.read(frame);
        System.out.println("Frame Obtained");

        camera.release();

        System.out.println("Captured Frame Width " + frame.width());

        Highgui.imwrite("d:/camera.jpg", frame);
        System.out.println("OK");
    }
}
