/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection;

import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class FaceDetector {

    private static final String OPEN_CV_FACE_CASCADE = "D:\\Soft\\Development\\OpenCV\\opencv-2.4.8\\sources\\data\\haarcascades\\haarcascade_frontalface_default.xml";

    private static final String OPEN_CV_EYES_CASCADE = "D:\\Soft\\Development\\OpenCV\\opencv-2.4.8\\sources\\data\\haarcascades\\haarcascade_eye.xml";

    private static final Logger logger = LoggerFactory.getLogger(FaceDetector.class);

    private CascadeClassifier faceCascade;
    private CascadeClassifier eyesCascade;

    public FaceDetector() throws DEyesTrackerException {
        this(OPEN_CV_FACE_CASCADE, OPEN_CV_EYES_CASCADE);
    }

    public FaceDetector(final String faceCascadePath, final String eyesCascadePath) throws DEyesTrackerException {
        faceCascade = new CascadeClassifier(faceCascadePath);
        if (faceCascade.empty()) {
            throw new DEyesTrackerException("Error loading face cascade");
        } else {
            logger.debug("Face classifier loooaaaaaded up");
        }
        eyesCascade = new CascadeClassifier(eyesCascadePath);
        if (eyesCascade.empty()) {
            throw new DEyesTrackerException("Error loading eyes cascade");
        } else {
            logger.debug("Eyes classifier loooaaaaaded up");
        }
    }

    public Rect[] detectFaces(final Mat inputframe) {
        return detectWithClassifier(inputframe, faceCascade);
    }

    public Rect[] detectEyes(final Mat inputframe) {
        return detectWithClassifier(inputframe, eyesCascade);
    }

    private Rect[] detectWithClassifier(final Mat inputframe, final CascadeClassifier classifier) {
        final Mat mRgba = new Mat();
        final Mat mGrey = new Mat();
        final MatOfRect detectedObjects = new MatOfRect();
        inputframe.copyTo(mRgba);
        inputframe.copyTo(mGrey);
        Imgproc.cvtColor(mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(mGrey, mGrey);
        classifier.detectMultiScale(mGrey, detectedObjects);
        return detectedObjects.toArray();
    }
}
