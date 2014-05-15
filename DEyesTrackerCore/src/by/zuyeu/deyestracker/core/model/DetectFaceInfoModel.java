/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.model;

import org.opencv.core.Point;
import org.opencv.core.Rect;

/**
 *
 * @author Fieryphoenix
 */
public class DetectFaceInfoModel {

    private Point leftPupil;
    private Point rightPupil;
    private Rect leftEye;
    private Rect rightEye;
    private Rect face;

    public DetectFaceInfoModel() {
    }

    public Point getLeftPupil() {
        return leftPupil;
    }

    public void setLeftPupil(Point leftPupil) {
        this.leftPupil = leftPupil;
    }

    public Point getRightPupil() {
        return rightPupil;
    }

    public void setRightPupil(Point rightPupil) {
        this.rightPupil = rightPupil;
    }

    public Rect getLeftEye() {
        return leftEye;
    }

    public void setLeftEye(Rect leftEye) {
        this.leftEye = leftEye;
    }

    public Rect getRightEye() {
        return rightEye;
    }

    public void setRightEye(Rect rightEye) {
        this.rightEye = rightEye;
    }

    public Rect getFace() {
        return face;
    }

    public void setFace(Rect face) {
        this.face = face;
    }

    @Override
    public String toString() {
        return "DetectFaceInfoModel{" + "leftPupil=" + leftPupil + ", rightPupil=" + rightPupil + ", leftEye=" + leftEye + ", rightEye=" + rightEye + ", face=" + face + '}';
    }

}
