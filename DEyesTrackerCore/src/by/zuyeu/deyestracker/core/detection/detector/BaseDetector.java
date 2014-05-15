/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.detector;

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
public class BaseDetector {

    private static final Logger LOG = LoggerFactory.getLogger(BaseDetector.class);
    protected CascadeClassifier cascade;

    private BaseDetector() {
    }

    public BaseDetector(final String cascadePath) throws DEyesTrackerException {
        cascade = new CascadeClassifier(cascadePath);
        if (cascade.empty()) {
            throw new DEyesTrackerException("Error loading face cascade");
        } else {
            LOG.debug("classifier loooaaaaaded up");
        }
    }

    public Rect[] detectWithClassifier(final Mat inputframe) {
        return detectWithClassifier(inputframe, cascade);
    }

    protected Rect[] detectWithClassifier(final Mat inputframe, final CascadeClassifier classifier) {
        LOG.debug("detectWithClassifier - start;");

        final Mat mRgba = new Mat();
        final Mat mGrey = new Mat();
        final MatOfRect detectedObjects = new MatOfRect();
        inputframe.copyTo(mRgba);
        inputframe.copyTo(mGrey);
        Imgproc.cvtColor(mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(mGrey, mGrey);
        classifier.detectMultiScale(mGrey, detectedObjects);

        LOG.debug("detectWithClassifier - end;");
        return detectedObjects.toArray();
    }

}
