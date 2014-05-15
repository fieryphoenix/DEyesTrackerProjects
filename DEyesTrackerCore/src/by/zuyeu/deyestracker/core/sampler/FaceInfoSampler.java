/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.sampler;

import by.zuyeu.deyestracker.core.detection.detector.EyesDetector;
import by.zuyeu.deyestracker.core.detection.detector.FaceDetector;
import by.zuyeu.deyestracker.core.detection.task.DetectEyesTask;
import by.zuyeu.deyestracker.core.detection.task.DetectFaceTask;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import by.zuyeu.deyestracker.core.model.DetectFaceSample;
import by.zuyeu.deyestracker.core.util.MatUtils;
import by.zuyeu.deyestracker.core.util.TaskUtils;
import by.zuyeu.deyestracker.core.video.CameraFrameCapture;
import by.zuyeu.deyestracker.core.video.IFrameCapture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class FaceInfoSampler {

    private static final Logger LOG = LoggerFactory.getLogger(FaceInfoSampler.class);

    final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private IFrameCapture capture;
    private FaceDetector faceDetector;
    private EyesDetector leftEyeDetector;
    private EyesDetector rightEyeDetector;

    public FaceInfoSampler() throws DEyesTrackerException {
        // Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        initDetectors();
        initCamera();
    }

    private void initCamera() throws DEyesTrackerException {
        LOG.info("initCamera - start;");

        // Read the video stream
        capture = new CameraFrameCapture();
        capture.open();
        new Thread(capture).start();
        LOG.info("initCamera - end;");
    }

    public void initDetectors() throws DEyesTrackerException {
        faceDetector = new FaceDetector();
        leftEyeDetector = new EyesDetector(EyesDetector.EyesDetectType.LEFT);
        rightEyeDetector = new EyesDetector(EyesDetector.EyesDetectType.RIGHT);
    }

    public DetectFaceSample makeSample() throws DEyesTrackerException {
        LOG.info("makeSample() - start;");
        DetectFaceSample sample = new DetectFaceSample();

        Mat webcamImage;
        do {
            webcamImage = capture.getNextFrame();
        } while (webcamImage == null);

        //TODO think about improving performance
        final FutureTask<Rect> detectFaceTask = TaskUtils.wrapFutureAnd(new DetectFaceTask(faceDetector, webcamImage), executorService);
        Rect mainFace = null;
        try {
            mainFace = detectFaceTask.get();
        } catch (InterruptedException | ExecutionException ex) {
            LOG.error(ex.getMessage());
        }
        if (mainFace != null) {
            final Mat faceImage = MatUtils.selectSubmatByRect(mainFace, webcamImage);
            Mat faceImageForDetection = selectEyesRegionFromFace(faceImage);
            final FutureTask<Rect[]> detectEyesTask = TaskUtils.wrapFutureAnd(new DetectEyesTask(new EyesDetector[]{leftEyeDetector, rightEyeDetector}, faceImageForDetection), executorService
            );
        }

        LOG.info("makeSample() - end: sample = {}", sample);
        return sample;
    }

    private static Mat selectEyesRegionFromFace(final Mat faceImage) {
        return faceImage.submat(0, faceImage.rows() / 2, 0, faceImage.cols());
    }
}
