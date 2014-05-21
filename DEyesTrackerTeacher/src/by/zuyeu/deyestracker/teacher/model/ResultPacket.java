/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.teacher.model;

import by.zuyeu.deyestracker.core.detection.model.StudyResult;
import java.io.Serializable;

/**
 *
 * @author Fieryphoenix
 */
public class ResultPacket implements Serializable {

    private StudyResult studyResult;
    private boolean isEmpty;

    public ResultPacket() {
        isEmpty = true;
    }

    public StudyResult getStudyResult() {
        return studyResult;
    }

    public void setStudyResult(StudyResult studyResult) {
        this.studyResult = studyResult;
    }

    public boolean isIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    @Override
    public String toString() {
        return "ResultPacket{" + "studyResult=" + studyResult + ", isEmpty=" + isEmpty + '}';
    }

}
