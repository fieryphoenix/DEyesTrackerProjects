/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.tracker;

import by.zuyeu.deyestracker.core.detection.learning.TeacherWithUser;
import by.zuyeu.deyestracker.core.detection.model.DetectFaceSample;
import by.zuyeu.deyestracker.core.detection.model.StudyResult;
import by.zuyeu.deyestracker.core.eda.event.CoreEvent;
import by.zuyeu.deyestracker.core.eda.event.MoveEvent;
import by.zuyeu.deyestracker.core.eda.event.QualityEvent;
import by.zuyeu.deyestracker.core.eda.router.IRouter;
import by.zuyeu.deyestracker.core.eda.router.RouterFactory;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerExceptionCode;
import by.zuyeu.deyestracker.core.util.ExceptionToEventConverter;
import by.zuyeu.deyestracker.core.util.OpenCVLibraryLoader;
import by.zuyeu.deyestracker.core.video.sampler.FaceInfoSampler;
import by.zuyeu.deyestracker.core.video.sampler.ISampler;
import by.zuyeu.deyestracker.core.video.util.ScreenUtils;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.opencv.core.Point;
import org.opencv.core.Size;
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
    private static final double TRESHOLD_CENTER_VALUE = 0.5;//FIXME
    private static final double TRESHOLD_Y_VALUE = 0.5;//FIXME
    private static final double TRESHOLD_X_VALUE = 0.05;//FIXME

    private IRouter router;
    private ISampler sampler;
    private final CircularFifoQueue<DetectFaceSample> samples;
    private StudyResult studyResult;
    private boolean isStopped;
    private final Size screenSize;
    private ScreenPointBorder pointBorder;

    protected ScreenPointTracker(final boolean skipInit) throws DEyesTrackerException {
        if (!skipInit) {
            OpenCVLibraryLoader.loadCoreIfNeed();
            initRouter();
            openSampler();
            startStuding();
        }
        samples = new CircularFifoQueue<>(10);
        this.screenSize = ScreenUtils.getScreenSize();
    }

    public void start() throws DEyesTrackerException {
        isStopped = false;
        defineScreenBorder();
        while (!isStopped) {
            final DetectFaceSample nextSample = sampler.makeSample();
            trackPointChanges(nextSample);
            sendQualityReport(nextSample);
        }
        LOG.info("TEST START - OK");
    }

    private void trackPointChanges(final DetectFaceSample nextSample) {
        if (nextSample.isComplete()) {
            final DetectFaceSample lastSample = samples.peek();
            if (lastSample != null) {
                final Point nextMedian = defineMedian(nextSample);
                final Point lastMedian = defineMedian(lastSample);
                tresholdMove(nextMedian, lastMedian);
            }
            samples.add(nextSample);
        }
    }

    private void sendQualityReport(final DetectFaceSample nextSample) {
        if (nextSample.getFace() == null) {
            router.sendEvent(new QualityEvent(QualityEvent.QualityType.BAD));
        } else if (nextSample.getLeftEye() == null && nextSample.getRightEye() == null) {
            router.sendEvent(new QualityEvent(QualityEvent.QualityType.NORMAL));
        } else {
            router.sendEvent(new QualityEvent(QualityEvent.QualityType.GOOD));
        }
    }

    public void stop() throws DEyesTrackerException {
        //TODO
        isStopped = false;
        sampler.close();
        LOG.info("TEST STOP - OK");
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
        } catch (DEyesTrackerException e) {
            dispatchException(e);
            throw e;
        }
        LOG.trace("openSampler - end;");
    }

    private void initRouter() {
        LOG.trace("initRouter - start;");

        this.router = RouterFactory.getRouter(RouterFactory.RouterType.EVENT);

        LOG.trace("initRouter - end;");
    }

    private void dispatchException(final DEyesTrackerException e) {
        final DEyesTrackerExceptionCode code = e.getCode();
        final CoreEvent.EventType item = ExceptionToEventConverter.getEventFromException(code);
        publishCoreEvent(item);
    }

    protected void publishCoreEvent(CoreEvent.EventType item) {
        if (router != null && item != null) {
            router.sendEvent(new CoreEvent(item));
        }
    }

    private void publishMoveEvent(MoveEvent moveEvent) {
        if (router != null && moveEvent != null) {
            router.sendEvent(moveEvent);
        }
    }

    private void defineScreenBorder() {
        this.pointBorder = new ScreenPointBorder();
        pointBorder.setBottomY(defineMedian(studyResult.getBottomLeft(), studyResult.getBottomRight()).y);
        pointBorder.setTopY(defineMedian(studyResult.getTopLeft(), studyResult.getTopRight()).y);
        pointBorder.setLeftX(defineMedian(studyResult.getTopLeft(), studyResult.getBottomLeft()).x);
        pointBorder.setRightX(defineMedian(studyResult.getTopRight(), studyResult.getBottomRight()).x);
        LOG.debug("defineScreenBorder - end: pointBorder = {}", pointBorder);
    }

    private Point defineMedian(DetectFaceSample s1, DetectFaceSample s2) {
        final Point p1 = defineMedian(s1);
        final Point p2 = defineMedian(s2);
        LOG.debug("p1 = {}, p2 = {}", p1, p2);
        return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }

    private Point defineMedian(DetectFaceSample sample) {
        double pupilXMedian = (sample.getLeftPupil().x + sample.getRightPupil().x) / 2;
        double pupilYMedian = (sample.getLeftPupil().y + sample.getRightPupil().y) / 2;
        double eyesXMedian = (sample.getLeftEye().x + sample.getLeftEye().width / 2 + sample.getRightEye().x + sample.getRightEye().width / 2) / 2;
        double eyesYMedian = (sample.getLeftEye().y + sample.getLeftEye().height / 2 + sample.getRightEye().y + sample.getRightEye().height / 2) / 2;
        LOG.debug("pupilXMedian = {}, eyesXMedian = {}", pupilXMedian, eyesXMedian);
        LOG.debug("pupilYMedian = {}, eyesYMedian = {}", pupilYMedian, eyesYMedian);
        return new Point((pupilXMedian + eyesXMedian) / 2, (pupilYMedian + eyesYMedian) / 2);
    }

    private void tresholdMove(Point nextMedian, Point lastMedian) {
        LOG.debug("tresholdMove() - start: lastMedian = {}, nextMedian = {}", lastMedian, nextMedian);
        boolean moveX = Math.abs(nextMedian.x - lastMedian.x) > TRESHOLD_X_VALUE;
        boolean moveY = Math.abs(nextMedian.y - lastMedian.y) > TRESHOLD_Y_VALUE;
        Point center = pointBorder.getCenter();
        boolean moveXFromCenter = Math.abs(nextMedian.x - center.x) > TRESHOLD_CENTER_VALUE;
        boolean moveYFromCenter = Math.abs(nextMedian.x - center.x) > TRESHOLD_CENTER_VALUE;
        if (moveX || moveY || moveXFromCenter || moveYFromCenter) {
            scaleToBorderAndPublicMove(nextMedian, lastMedian, center);
        }
        LOG.debug("tresholdMove() - end;");
    }

    private void scaleToBorderAndPublicMove(Point nextMedian, Point lastMedian, Point center) {
        Point screenViewPoint = calculateViewPointBoundToBorders(nextMedian);
        Point oldScreenViewPoint = calculateViewPointBoundToBorders(lastMedian);
        publishMoveEvent(new MoveEvent(oldScreenViewPoint, screenViewPoint, center));
    }

    private Point calculateViewPointBoundToBorders(Point nextMedian) {
        LOG.debug("calculateViewPointBoundToBorders() - start;");
        // shift from left border
        double width = pointBorder.getWidth();
        double xShift = nextMedian.x - pointBorder.getLeftX();
        width = Math.max(width, xShift);
        double viewX = screenSize.width / width * xShift;
        //shift from top border
        double height = pointBorder.getHeight();
        double yShift = nextMedian.y - pointBorder.getTopY();
        height = Math.max(height, yShift);
        double viewY = screenSize.height / height * yShift;
        Point screenViewPoint = new Point(viewX, viewY);
        LOG.debug("calculateViewPointBoundToBorders() - end: point = {}", screenViewPoint);
        return screenViewPoint;
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
