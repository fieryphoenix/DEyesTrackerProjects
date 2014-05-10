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

    public void start() throws DEyesTrackerException;

    public void stop();

    public Mat getNextFrame();

}
