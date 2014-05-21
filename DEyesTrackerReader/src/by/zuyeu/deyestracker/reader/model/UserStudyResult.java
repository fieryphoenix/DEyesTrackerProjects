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
public class UserStudyResult implements Serializable {

    private User user;
    private StudyResult2 studyResult2;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StudyResult2 getStudyResult2() {
        return studyResult2;
    }

    public void setStudyResult2(StudyResult2 studyResult2) {
        this.studyResult2 = studyResult2;
    }
}
