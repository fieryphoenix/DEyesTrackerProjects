/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.repository.memory;

import by.zuyeu.deyestracker.reader.model.StudyResult2;
import by.zuyeu.deyestracker.reader.model.User;
import by.zuyeu.deyestracker.reader.repository.TeachResultDAO;

/**
 *
 * @author Fieryphoenix
 */
public class MemoryTechResultDAO implements TeachResultDAO {

    @Override
    public void saveDetectResult(StudyResult2 studyResult2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StudyResult2 findStudyResultByUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
