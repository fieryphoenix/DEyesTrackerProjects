/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.video;

import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import java.util.concurrent.RunnableFuture;
import org.opencv.core.Mat;

/**
 *
 * @author Fieryphoenix
 */
public interface IFrameCapture extends RunnableFuture<Void> {

    void start() throws DEyesTrackerException;

    boolean open() throws DEyesTrackerException;

    void stop();

    Mat getNextFrame();

    Mat getLatestFrame();

}
