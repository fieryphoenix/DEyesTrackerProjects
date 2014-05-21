/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.util;

import by.zuyeu.deyestracker.core.detection.model.DetectFaceSample;
import by.zuyeu.deyestracker.core.detection.model.StudyResult;
import by.zuyeu.deyestracker.reader.model.DetectFaceSample2;
import by.zuyeu.deyestracker.reader.model.Point2;
import by.zuyeu.deyestracker.reader.model.Rect2;
import by.zuyeu.deyestracker.reader.model.StudyResult2;
import org.opencv.core.Point;
import org.opencv.core.Rect;

/**
 *
 * @author Fieryphoenix
 */
public class ObjectTransformer {

    private ObjectTransformer() {
    }

    public static StudyResult2 transformStudyResultToSerializableType(StudyResult sample) {
        final StudyResult2 result2 = new StudyResult2();
        result2.setBl(transformDetectSampleToSerializableType(sample.getBottomLeft()));
        result2.setTl(transformDetectSampleToSerializableType(sample.getTopLeft()));
        result2.setBr(transformDetectSampleToSerializableType(sample.getBottomRight()));
        result2.setTr(transformDetectSampleToSerializableType(sample.getTopRight()));
        return result2;
    }

    public static DetectFaceSample2 transformDetectSampleToSerializableType(DetectFaceSample sample) {
        final DetectFaceSample2 sample2 = new DetectFaceSample2();
        sample2.setFace(transformRectToSerializableType(sample.getFace()));
        sample2.setLeftEye(transformRectToSerializableType(sample.getLeftEye()));
        sample2.setRightEye(transformRectToSerializableType(sample.getRightEye()));
        sample2.setLeftPupil(transformPointToSerializableType(sample.getLeftPupil()));
        sample2.setRightPupil(transformPointToSerializableType(sample.getRightPupil()));
        return sample2;
    }

    private static Rect2 transformRectToSerializableType(Rect rect) {
        final Rect2 rect2 = new Rect2();
        rect2.setX(rect.x);
        rect2.setY(rect.y);
        rect2.setWidth(rect.width);
        rect2.setHeight(rect.height);
        return rect2;
    }

    private static Point2 transformPointToSerializableType(Point point) {
        final Point2 point2 = new Point2();
        point2.setX((int) point.x);
        point2.setY((int) point.y);
        return point2;
    }

    public static DetectFaceSample transformDetectSample2ToCVType(DetectFaceSample2 sample2) {
        final DetectFaceSample sample = new DetectFaceSample();
        sample.setFace(transformRect2ToCVType(sample2.getFace()));
        sample.setLeftEye(transformRect2ToCVType(sample2.getLeftEye()));
        sample.setRightEye(transformRect2ToCVType(sample2.getRightEye()));
        sample.setLeftPupil(transformPoint2ToCVType(sample2.getLeftPupil()));
        sample.setRightPupil(transformPoint2ToCVType(sample2.getRightPupil()));
        return sample;
    }

    private static Rect transformRect2ToCVType(Rect2 rect2) {
        final Rect rect = new Rect();
        rect.x = rect2.getX();
        rect.y = rect2.getY();
        rect.width = rect2.getWidth();
        rect.height = rect2.getHeight();
        return rect;
    }

    private static Point transformPoint2ToCVType(Point2 point2) {
        final Point point = new Point();
        point.x = point2.getX();
        point.y = point2.getY();
        return point;
    }
}
