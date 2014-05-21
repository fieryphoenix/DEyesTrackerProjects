/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.model;

import java.io.Serializable;

/**
 *
 * @author Fieryphoenix
 */
public class DetectFaceSample2 implements Serializable {

    private Point2 leftPupil;
    private Point2 rightPupil;
    private Rect2 leftEye;
    private Rect2 rightEye;
    private Rect2 face;

    public Point2 getLeftPupil() {
        return leftPupil;
    }

    public void setLeftPupil(Point2 leftPupil) {
        this.leftPupil = leftPupil;
    }

    public Point2 getRightPupil() {
        return rightPupil;
    }

    public void setRightPupil(Point2 rightPupil) {
        this.rightPupil = rightPupil;
    }

    public Rect2 getLeftEye() {
        return leftEye;
    }

    public void setLeftEye(Rect2 leftEye) {
        this.leftEye = leftEye;
    }

    public Rect2 getRightEye() {
        return rightEye;
    }

    public void setRightEye(Rect2 rightEye) {
        this.rightEye = rightEye;
    }

    public Rect2 getFace() {
        return face;
    }

    public void setFace(Rect2 face) {
        this.face = face;
    }

    @Override
    public String toString() {
        return "DetectFaceSample2{" + "leftPupil=" + leftPupil + ", rightPupil=" + rightPupil + ", leftEye=" + leftEye + ", rightEye=" + rightEye + ", face=" + face + '}';
    }
}
