/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.repository;

import by.zuyeu.deyestracker.reader.model.StudyResult2;
import by.zuyeu.deyestracker.reader.model.User;

/**
 *
 * @author Fieryphoenix
 */
public interface TeachResultDAO {

    boolean saveTechingResult(User student, StudyResult2 studyResult2);

    StudyResult2 findStudyResultByUser(User user);
}
