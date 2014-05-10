/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection;

import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

/**
 *
 * @author Fieryphoenix
 */
public class EyesDetector extends BaseDetector {

    private static final String OPEN_CV_EYES_CASCADE = "D:\\Soft\\Development\\OpenCV\\opencv-2.4.8\\sources\\data\\haarcascades\\haarcascade_eye_tree_eyeglasses.xml";

    public EyesDetector(String cascadePath) throws DEyesTrackerException {
        super(cascadePath);
    }

    public EyesDetector() throws DEyesTrackerException {
        super(OPEN_CV_EYES_CASCADE);
    }

    public Rect[] detectEyes(final Mat inputframe) {
        //TODO - add pre and post processing
        return detectWithClassifier(inputframe);
    }
}
