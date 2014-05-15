/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.detector;

import by.zuyeu.deyestracker.core.detection.processor.IProcessor;
import by.zuyeu.deyestracker.core.detection.processor.LeftEyeProcessor;
import by.zuyeu.deyestracker.core.detection.processor.RightEyeProcessor;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

/**
 *
 * @author Fieryphoenix
 */
public class EyesDetector extends BaseDetector {

    public static enum EyesDetectType {

        BOTH, LEFT, RIGHT;
    }

    private static final String OPEN_CV_EYES_CASCADE = "D:\\Soft\\Development\\OpenCV\\opencv-2.4.8\\sources\\data\\haarcascades\\haarcascade_eye_tree_eyeglasses.xml";

    private static final String OPEN_CV_LEFT_EYE_CASCADE = "D:\\Soft\\Development\\OpenCV\\opencv-2.4.8\\sources\\data\\haarcascades\\haarcascade_lefteye_2splits.xml";

    private static final String OPEN_CV_RIGHT_EYE_CASCADE = "D:\\Soft\\Development\\OpenCV\\opencv-2.4.8\\sources\\data\\haarcascades\\haarcascade_righteye_2splits.xml";

    private EyesDetectType type;
    private IProcessor<Rect[], Rect[]> postProcessor;

    protected EyesDetector(String cascadePath) throws DEyesTrackerException {
        super(cascadePath);
        type = EyesDetectType.BOTH;
        createProcessors();
    }

    public EyesDetector() throws DEyesTrackerException {
        this(EyesDetectType.BOTH);
    }

    public EyesDetector(EyesDetectType type) throws DEyesTrackerException {
        super(definePathToCascadeByType(type));
        this.type = type;
        createProcessors();
    }

    private static String definePathToCascadeByType(EyesDetectType type) {
        String pathToCascade;
        switch (type) {
            case BOTH:
                pathToCascade = OPEN_CV_EYES_CASCADE;
                break;
            case LEFT:
                pathToCascade = OPEN_CV_LEFT_EYE_CASCADE;
                break;
            case RIGHT:
                pathToCascade = OPEN_CV_RIGHT_EYE_CASCADE;
                break;
            default:
                pathToCascade = OPEN_CV_EYES_CASCADE;
                break;
        }
        return pathToCascade;
    }

    private void createProcessors() {
        switch (type) {
            case BOTH:
                //TODO
                break;
            case LEFT:
                this.postProcessor = new LeftEyeProcessor();
                break;
            case RIGHT:
                this.postProcessor = new RightEyeProcessor();
                break;
            default:
                throw new UnsupportedOperationException("no predefined processor type");
        }
    }

    public Rect[] detectEyes(final Mat inputframe) {
        //TODO - add pre and post processing
        Rect[] eyes = detectWithClassifier(inputframe);
        eyes = postDefineEyes(eyes);
        return eyes;
    }

    private Rect[] postDefineEyes(Rect[] eyes) {
        if (postProcessor != null) {
            return postProcessor.process(eyes);
        } else {
            return eyes;
        }
    }
}
