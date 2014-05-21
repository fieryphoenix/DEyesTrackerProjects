/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.learning;

import by.zuyeu.deyestracker.core.detection.model.DetectFaceSample;
import by.zuyeu.deyestracker.core.eda.event.StudyProcessEvent;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;

/**
 *
 * @author Fieryphoenix
 */
public interface ITeacher {

    DetectFaceSample getStudyResult(StudyProcessEvent.StudyRegion region) throws DEyesTrackerException;

}
