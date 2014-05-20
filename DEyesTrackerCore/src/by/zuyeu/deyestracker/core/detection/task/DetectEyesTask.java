/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.task;

import by.zuyeu.deyestracker.core.detection.detector.EyesDetector;
import org.apache.commons.lang3.ArrayUtils;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class DetectEyesTask implements IDetectTask<Rect[]> {

    private static final Logger LOG = LoggerFactory.getLogger(DetectEyesTask.class);

    private final EyesDetector[] detectors;
    private final Mat frame;

    public DetectEyesTask(EyesDetector detector, Mat frame) {
        this.detectors = new EyesDetector[]{detector};
        this.frame = frame;
    }

    public DetectEyesTask(EyesDetector[] detectors, Mat frame) {
        this.detectors = detectors;
        this.frame = frame;
    }

    @Override
    public Rect[] call() throws Exception {
        long startTime = System.nanoTime();
        Rect[] result = new Rect[0];
        if (frame != null && !frame.empty()) {
            for (EyesDetector detector : detectors) {
                Rect[] detectedEyes = detector.detectEyes(frame);
                result = ArrayUtils.addAll(result, detectedEyes);
            }
        }
        long endTime = System.nanoTime();
        LOG.debug("eyes detected = {}", result.length);
        LOG.debug("detection time: {} ms", (float) (endTime - startTime) / 1000000);
        return result;
    }
}
