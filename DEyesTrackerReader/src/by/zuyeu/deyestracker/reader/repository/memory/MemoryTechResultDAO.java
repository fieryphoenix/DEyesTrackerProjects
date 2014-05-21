/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.repository.memory;

import by.zuyeu.deyestracker.reader.model.StudyResult2;
import by.zuyeu.deyestracker.reader.model.User;
import by.zuyeu.deyestracker.reader.repository.TeachResultDAO;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class MemoryTechResultDAO implements TeachResultDAO {

    private static final Logger LOG = LoggerFactory.getLogger(MemoryTechResultDAO.class);

    private static final Map<User, StudyResult2> userTeachResults = new ConcurrentHashMap<>();

    @Override
    public boolean saveTechingResult(User student, StudyResult2 studyResult2) {
        LOG.info("saveTechingResult() - start: user = {}, studyResult2 = {}", student, studyResult2);
        userTeachResults.putIfAbsent(student, studyResult2);
        final StudyResult2 storedResult = userTeachResults.get(student);
        boolean saved = storedResult.equals(studyResult2);
        LOG.info("saveTechingResult() - end: saved = {}", saved);
        return saved;
    }

    @Override
    public StudyResult2 findStudyResultByUser(User user) {
        LOG.info("saveTechingResult() - start: user = {}", user);
        StudyResult2 result = userTeachResults.get(user);
        LOG.info("saveTechingResult() - end: result = {}", result);
        return result;
    }

}
