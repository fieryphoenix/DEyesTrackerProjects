/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.tracker;

import by.zuyeu.deyestracker.core.event.CoreEvent;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerExceptionCode;
import by.zuyeu.deyestracker.core.model.DetectFaceSample;
import by.zuyeu.deyestracker.core.model.StudyResult;
import by.zuyeu.deyestracker.core.router.EventRouter;
import by.zuyeu.deyestracker.core.router.IRouter;
import by.zuyeu.deyestracker.core.sampler.FaceInfoSampler;
import by.zuyeu.deyestracker.core.sampler.ISampler;
import by.zuyeu.deyestracker.core.util.ExceptionToEventConverter;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class ScreenPointTracker {

    private static final Logger LOG = LoggerFactory.getLogger(ScreenPointTracker.class);

    private IRouter router;
    private ISampler sampler;
    private CircularFifoQueue<DetectFaceSample> samples;
    private StudyResult studyResult;

    public ScreenPointTracker() throws DEyesTrackerException {
        initRouter();
        try {
            openSampler();
        } catch (final DEyesTrackerException e) {
            dispatchException(e);
            throw e;
        }
        startStuding();
    }

    public ScreenPointTracker(final StudyResult studyResult) throws DEyesTrackerException {
        this.studyResult = studyResult;
    }

    public void start() throws DEyesTrackerException {

    }

    public IRouter getRouter() {
        return this.router;
    }

    private void startStuding() {
        LOG.trace("startStuding - start;");
        //TODO: realize
        LOG.trace("startStuding - end;");
    }

    private void openSampler() throws DEyesTrackerException {
        LOG.trace("openSampler - start;");
        sampler = new FaceInfoSampler();
        LOG.trace("openSampler - end;");
    }

    private void initRouter() {
        LOG.trace("initRouter - start;");

        this.router = new EventRouter();

        LOG.trace("initRouter - end;");
    }

    private void dispatchException(final DEyesTrackerException e) {
        DEyesTrackerExceptionCode code = e.getCode();
        CoreEvent.EventType item = ExceptionToEventConverter.getEventFromException(code);
        router.sendEvent(new CoreEvent(item));
    }
}
