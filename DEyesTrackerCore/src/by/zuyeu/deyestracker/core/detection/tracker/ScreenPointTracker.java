/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.tracker;

import by.zuyeu.deyestracker.core.eda.event.CoreEvent;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerExceptionCode;
import by.zuyeu.deyestracker.core.detection.learning.TeacherWithUser;
import by.zuyeu.deyestracker.core.detection.model.DetectFaceSample;
import by.zuyeu.deyestracker.core.detection.model.StudyResult;
import by.zuyeu.deyestracker.core.eda.router.EventRouter;
import by.zuyeu.deyestracker.core.eda.router.IRouter;
import by.zuyeu.deyestracker.core.video.sampler.FaceInfoSampler;
import by.zuyeu.deyestracker.core.video.sampler.ISampler;
import by.zuyeu.deyestracker.core.util.ExceptionToEventConverter;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class ScreenPointTracker {

    public static class ScreenPointTrackerBuilder {

        private ISampler sampler;
        private IRouter router;
        private StudyResult studyResult;

        public ScreenPointTrackerBuilder() {
        }

        public ScreenPointTrackerBuilder setSampler(ISampler sampler) {
            this.sampler = sampler;
            return this;
        }

        public ScreenPointTrackerBuilder setRouter(IRouter router) {
            this.router = router;
            return this;
        }

        public ScreenPointTrackerBuilder setStudyResult(StudyResult studyResult) {
            this.studyResult = studyResult;
            return this;
        }

        public ScreenPointTracker createScreenPointTracker() throws DEyesTrackerException {
            final ScreenPointTracker screenPointTracker = new ScreenPointTracker(true);

            if (router != null) {
                screenPointTracker.setRouter(router);
            } else {
                screenPointTracker.initRouter();
            }
            if (sampler != null) {
                screenPointTracker.setSampler(sampler);
            } else {
                screenPointTracker.openSampler();
            }
            if (studyResult != null) {
                screenPointTracker.setStudyResult(studyResult);
            } else {
                screenPointTracker.startStuding();
            }
            return screenPointTracker;
        }

    }

    private static final Logger LOG = LoggerFactory.getLogger(ScreenPointTracker.class);

    private IRouter router;
    private ISampler sampler;
    private CircularFifoQueue<DetectFaceSample> samples;
    private StudyResult studyResult;

    protected ScreenPointTracker(final boolean skipInit) throws DEyesTrackerException {
        if (!skipInit) {
            initRouter();
            openSampler();
            startStuding();
        }
    }

    public void start() throws DEyesTrackerException {
        //TODO
        LOG.warn("TEST START - OK");
    }

    public void stop() throws DEyesTrackerException {
        //TODO
        LOG.warn("TEST STOP - OK");
        sampler.close();
    }

    public IRouter getRouter() {
        return this.router;
    }

    private void startStuding() throws DEyesTrackerException {
        LOG.trace("startStuding - start;");
        TeacherWithUser teacher = new TeacherWithUser(router, sampler);
        try {
            this.studyResult = teacher.call();
        } catch (Exception ex) {
            throw new DEyesTrackerException(DEyesTrackerExceptionCode.STUDY_FAILURE, ex);
        }
        LOG.trace("startStuding - end;");
    }

    private void openSampler() throws DEyesTrackerException {
        LOG.trace("openSampler - start;");
        try {
            sampler = new FaceInfoSampler();
        } catch (final DEyesTrackerException e) {
            dispatchException(e);
            throw e;
        }
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
        publishEvent(item);
    }

    protected void publishEvent(CoreEvent.EventType item) {
        if (router != null && item != null) {
            router.sendEvent(new CoreEvent(item));
        }
    }

    private void setRouter(IRouter router) {
        this.router = router;
    }

    private void setSampler(ISampler sampler) {
        this.sampler = sampler;
    }

    private void setStudyResult(StudyResult studyResult) {
        this.studyResult = studyResult;
    }

    public ISampler getSampler() {
        return sampler;
    }

    public StudyResult getStudyResult() {
        return studyResult;
    }
}
