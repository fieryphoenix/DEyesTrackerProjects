/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.video;

import by.zuyeu.deyestracker.exception.DEyesTrackerException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class CameraFrameCapture implements IFrameCapture {

    private static final int DEFAULT_FRAMES_COUNT = 30;
    private static final int DEFAULT_DEVICE = 0;

    private static final Logger logger = LoggerFactory.getLogger(CameraFrameCapture.class);

    private volatile boolean isCanceled;

    private final VideoCapture capture;
    private final CircularFifoQueue<Mat> frames;

    public CameraFrameCapture() {
        frames = new CircularFifoQueue<>(DEFAULT_FRAMES_COUNT);
        capture = new VideoCapture(DEFAULT_DEVICE);
        isCanceled = false;
    }

    @Override
    public void start() throws DEyesTrackerException {

        if (capture.isOpened()) {
            Mat webcam_image = new Mat();
            while (!isCanceled) {
                capture.read(webcam_image);
            }
        } else {
            throw new DEyesTrackerException("capture init failure");
        }
    }

    @Override
    public void stop() {
        frames.clear();
        capture.release();
    }

    @Override
    public Mat getNextFrame() {
        return frames.poll();
    }

    @Override
    public void run() {
        try {
            start();
        } catch (DEyesTrackerException ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        stop();
        return true;
    }

    @Override
    public boolean isCancelled() {
        return isCanceled;
    }

    @Override
    public boolean isDone() {
        return !isCanceled;
    }

    @Override
    public Void get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Void get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
