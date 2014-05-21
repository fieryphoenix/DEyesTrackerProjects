/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.video.sampler;

import by.zuyeu.deyestracker.core.detection.detector.EyesDetector;
import by.zuyeu.deyestracker.core.detection.detector.FaceDetector;
import by.zuyeu.deyestracker.core.detection.model.DetectFaceSample;
import by.zuyeu.deyestracker.core.detection.task.DetectEyesTask;
import by.zuyeu.deyestracker.core.detection.task.DetectFaceTask;
import by.zuyeu.deyestracker.core.detection.task.DetectPupilsTask;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import by.zuyeu.deyestracker.core.util.CVCoreUtils;
import by.zuyeu.deyestracker.core.util.OpenCVLibraryLoader;
import by.zuyeu.deyestracker.core.util.TaskUtils;
import by.zuyeu.deyestracker.core.util.comparator.RectXComparator;
import by.zuyeu.deyestracker.core.video.capture.CameraFrameCapture;
import by.zuyeu.deyestracker.core.video.capture.IFrameCapture;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class FaceInfoSampler implements ISampler {

    private static final Logger LOG = LoggerFactory.getLogger(FaceInfoSampler.class);

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final RectXComparator comparator = new RectXComparator();

    private IFrameCapture capture;
    private FaceDetector faceDetector;
    private EyesDetector leftEyeDetector;
    private EyesDetector rightEyeDetector;

    public FaceInfoSampler() throws DEyesTrackerException {
        // Load the native library.
        OpenCVLibraryLoader.loadCoreIfNeed();
        initCamera();
        initDetectors();
    }

    public FaceInfoSampler(final IFrameCapture capture) throws DEyesTrackerException {
        this.capture = capture;
        initDetectors();
    }

    protected final void initCamera() throws DEyesTrackerException {
        LOG.trace("initCamera - start;");

        // Read the video stream
        capture = new CameraFrameCapture();
        capture.open();
        new Thread(capture).start();

        LOG.trace("initCamera - end;");
    }

    protected final void initDetectors() throws DEyesTrackerException {
        LOG.trace("initDetectors - start;");

        faceDetector = new FaceDetector();
        leftEyeDetector = new EyesDetector(EyesDetector.EyesDetectType.LEFT);
        rightEyeDetector = new EyesDetector(EyesDetector.EyesDetectType.RIGHT);

        LOG.trace("initDetectors - end;");
    }

    @Override
    public void close() {
        if (capture != null) {
            capture.stop();
        }
    }

    @Override
    public DetectFaceSample makeSample() throws DEyesTrackerException {
        LOG.info("makeSample() - start;");

        final DetectFaceSample sample = new DetectFaceSample();

        final Mat webcamImage = receiveLastWebcamFrame();

        //TODO: think about improving performance
        final Rect mainFace = getMainFace(webcamImage);
        Rect[] eyes = null;

        if (mainFace != null) {
            sample.setFace(mainFace);
            eyes = getEyesRegions(mainFace, webcamImage);
        }
        if (mainFace != null && eyes != null && eyes.length > 0) {
            final int faceCenter = mainFace.x + mainFace.width / 2;

            final Rect leftEye = Arrays.stream(eyes).max(comparator).get();
            if (leftEye.x > faceCenter) {
                sample.setLeftEye(leftEye);
            }
            final Rect rightEye = Arrays.stream(eyes).min(comparator).get();
            if (rightEye.x < faceCenter) {
                sample.setRightEye(rightEye);
            }

            // start detect pupils
            sample.setLeftPupil(findPupil(sample.getLeftEye(), webcamImage));
            sample.setRightPupil(findPupil(sample.getRightEye(), webcamImage));
        }
        if (sample.getLeftPupil() != null || sample.getRightPupil() != null) {
            pupilsStabilization();
        }

        LOG.info("makeSample() - end: sample = {}", sample);
        return sample;
    }

    private Mat receiveLastWebcamFrame() {
        Mat webcamImage;
        do {
            webcamImage = capture.getNextFrame();
        } while (webcamImage == null);
        return webcamImage;
    }

    private Rect getMainFace(Mat webcamImage) {
        LOG.trace("getMainFace() - start;");
        Rect mainFace = null;
        final FutureTask<Rect> detectFaceTask = TaskUtils.wrapFutureAnd(new DetectFaceTask(faceDetector, webcamImage), executorService);
        try {
            mainFace = detectFaceTask.get();
        } catch (InterruptedException | ExecutionException ex) {
            LOG.error("getMainFace", ex);
        }
        LOG.trace("getMainFace() - end;");
        return mainFace;
    }

    private Point findPupil(Rect eye, Mat webcamImage) {
        LOG.trace("findPupil - start;");

        Point pupil = null;
        if (eye != null) {
            Rect pupilRegion = shrinkEyeRegionForPupil(eye);
            final Mat eyeRegion = CVCoreUtils.selectSubmatByRect(pupilRegion, webcamImage);
            final FutureTask<Point> detectPupilTask = TaskUtils.wrapFutureAnd(new DetectPupilsTask(eyeRegion), executorService
            );
            try {
                pupil = detectPupilTask.get();
            } catch (InterruptedException | ExecutionException ex) {
                LOG.error("findPupil", ex);
            }
            if (pupil != null) {
                pupil = CVCoreUtils.fixPointFromSubmat(pupil, pupilRegion);
            }
        }

        LOG.trace("findPupil - end;");
        return pupil;
    }

    private Rect[] getEyesRegions(Rect mainFace, Mat webcamImage) {
        LOG.trace("getPupilsPoints - start;");
        Rect[] eyes = null;
        final Mat faceImage = CVCoreUtils.selectSubmatByRect(mainFace, webcamImage);
        final Mat faceImageForDetection = selectEyesRegionFromFace(faceImage);
        final FutureTask<Rect[]> detectEyesTask = TaskUtils.wrapFutureAnd(new DetectEyesTask(new EyesDetector[]{leftEyeDetector, rightEyeDetector}, faceImageForDetection), executorService
        );
        try {
            eyes = detectEyesTask.get();
        } catch (InterruptedException | ExecutionException ex) {
            LOG.error("getEyesRegions", ex);
        }
        // fix rect coordinates to match whole frame size
        if (eyes != null) {
            CVCoreUtils.fixRectTLFromSubmat(eyes, mainFace);
        }

        LOG.trace("getPupilsPoints - end: eyes = {}", (Object) eyes);
        return eyes;
    }

    private Mat selectEyesRegionFromFace(final Mat faceImage) {
        return faceImage.submat(0, faceImage.rows() / 2, 0, faceImage.cols());
    }

    private Rect shrinkEyeRegionForPupil(Rect eye) {
        Rect resized = eye.clone();
        resized.x += resized.width / 10;
        resized.y += resized.height / 10;
        resized.width -= resized.width / 10 * 2;
        resized.height -= resized.height / 10 * 2;
        return resized;
    }

    private void pupilsStabilization() {
        //TODO think about algorithm
    }

    @Override
    public IFrameCapture getCapture() {
        return capture;
    }
}
