/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.video;

import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerExceptionCode;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
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

    private static final int DEFAULT_FRAMES_COUNT = 100;
    private static final int DEFAULT_DEVICE = 0;

    private static final Logger logger = LoggerFactory.getLogger(CameraFrameCapture.class);

    private volatile boolean isCanceled;

    private final VideoCapture capture;
    private final CircularFifoQueue<Mat> frames;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public CameraFrameCapture() {
        frames = new CircularFifoQueue<>(DEFAULT_FRAMES_COUNT);
        capture = new VideoCapture(DEFAULT_DEVICE);
        isCanceled = false;
    }

    @Override
    public void start() throws DEyesTrackerException {
        open();
        if (capture.isOpened()) {
            while (!isCanceled) {
                final Mat webcam_image = new Mat();
                capture.read(webcam_image);
                if (!webcam_image.empty()) {
                    lock.writeLock().lock();
                    try {
                        frames.add(webcam_image);
                    } finally {
                        lock.writeLock().unlock();
                    }
                }
            }
        } else {
            throw new DEyesTrackerException(DEyesTrackerExceptionCode.OPEN_CAMERA_FAIL, "capture init failure");
        }
    }

    @Override
    public boolean open() throws DEyesTrackerException {
        if (!capture.isOpened()) {
            try {
                capture.open(DEFAULT_DEVICE);
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                logger.error(ex.getMessage());
                throw new DEyesTrackerException(DEyesTrackerExceptionCode.OPEN_CAMERA_FAIL, "capture init failure");
            }
        }
        return capture.isOpened();
    }

    @Override
    public void stop() {
        frames.clear();
        capture.release();
    }

    @Override
    public Mat getNextFrame() {
        Mat next = null;
        lock.readLock().lock();
        try {
            next = frames.poll();
        } finally {
            lock.readLock().unlock();
        }
        return next;
    }

    @Override
    public Mat getLatestFrame() {
        Mat last = null;
        lock.readLock().lock();
        try {
            last = frames.get(frames.size() - 1);
        } finally {
            lock.readLock().unlock();
        }
        return last;
    }

    @Override
    public void run() {
        try {
            start();
        } catch (final DEyesTrackerException ex) {
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
