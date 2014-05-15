/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.tracker;

import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import by.zuyeu.deyestracker.core.model.DetectFaceInfoModel;
import by.zuyeu.deyestracker.core.model.StudyResult;
import by.zuyeu.deyestracker.core.router.EventRouter;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class ScreenPointTracker {

    private static final Logger logger = LoggerFactory.getLogger(ScreenPointTracker.class);

    private EventRouter router;
    private CircularFifoQueue<DetectFaceInfoModel> detectItems;
    private StudyResult studyResult;

    public ScreenPointTracker() throws DEyesTrackerException {
        startStuding();
    }

    public ScreenPointTracker(final StudyResult studyResult) throws DEyesTrackerException {
        this.studyResult = studyResult;
    }

    private void startStuding() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
