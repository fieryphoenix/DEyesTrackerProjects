/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.task;

import by.zuyeu.deyestracker.core.detection.detector.FaceDetector;
import java.util.concurrent.Callable;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class DetectFaceTask implements Callable<Rect> {

    private static final Logger logger = LoggerFactory.getLogger(DetectFaceTask.class);

    private final FaceDetector detector;
    private final Mat frame;

    public DetectFaceTask(FaceDetector detector, Mat frame) {
        this.detector = detector;
        this.frame = frame;
    }

    @Override
    public Rect call() throws Exception {
        long startTime = System.nanoTime();
        Rect result = null;
        if (frame != null && !frame.empty()) {
            result = detector.detectMain(frame);
        }
        long endTime = System.nanoTime();
        logger.debug("face detected = {}", result);
        logger.debug("detection time: {} ms", (float) (endTime - startTime) / 1000000);
        return result;
    }
}
