/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.learning;

import by.zuyeu.deyestracker.core.eda.event.StudyProcessEvent;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerExceptionCode;
import by.zuyeu.deyestracker.core.detection.model.DetectFaceSample;
import by.zuyeu.deyestracker.core.detection.model.StudyResult;
import by.zuyeu.deyestracker.core.eda.router.IRouter;
import by.zuyeu.deyestracker.core.video.sampler.ISampler;
import by.zuyeu.deyestracker.core.util.CVCoreUtils;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class TeacherWithUser extends Task<StudyResult> {

    private static final Logger LOG = LoggerFactory.getLogger(TeacherWithUser.class);

    private static final int MIN_COMPLETE_SAMPLES_NUMBER = 10;

    private final IRouter router;
    private final ISampler sampler;

    public TeacherWithUser(final IRouter router, final ISampler sampler) throws DEyesTrackerException {
        if (router == null || sampler == null) {
            throw new DEyesTrackerException(DEyesTrackerExceptionCode.STUDY_FAILURE);
        }
        this.router = router;
        this.sampler = sampler;
    }

    @Override
    public StudyResult call() throws Exception {

        final DetectFaceSample tl = getStudyResult(StudyProcessEvent.Region.TOP_LEFT);
        final DetectFaceSample tr = getStudyResult(StudyProcessEvent.Region.TOP_RIGHT);
        final DetectFaceSample bl = getStudyResult(StudyProcessEvent.Region.BOTTOM_LEFT);
        final DetectFaceSample br = getStudyResult(StudyProcessEvent.Region.BOTTOM_RIGHT);

        StudyResult studyResult = new StudyResult(tl, bl, tr, br);
        router.sendEvent(new StudyProcessEvent(StudyProcessEvent.Region.NONE));
        return studyResult;
    }

    private DetectFaceSample getStudyResult(StudyProcessEvent.Region region) throws DEyesTrackerException {
        router.sendEvent(new StudyProcessEvent(region));
        final List<DetectFaceSample> samples = new ArrayList<>(MIN_COMPLETE_SAMPLES_NUMBER);
        do {
            final DetectFaceSample sample = sampler.makeSample();
            if (sample.isComplete()) {
                samples.add(sample);
            }
        } while (samples.size() < MIN_COMPLETE_SAMPLES_NUMBER);
        // in sum operation all elements are complete - no need to check NULL
        final DetectFaceSample accumulator = samples.stream().reduce((s1, s2) -> {
            final DetectFaceSample res = new DetectFaceSample();
            res.setFace(CVCoreUtils.sumRect(s1.getFace(), s2.getFace()));
            res.setLeftEye(CVCoreUtils.sumRect(s1.getLeftEye(), s2.getLeftEye()));
            res.setRightEye(CVCoreUtils.sumRect(s1.getRightEye(), s2.getRightEye()));
            res.setLeftPupil(CVCoreUtils.sumPoints(s1.getLeftPupil(), s2.getLeftPupil()));
            res.setRightPupil(CVCoreUtils.sumPoints(s1.getRightPupil(), s2.getRightPupil()));
            LOG.debug("reduce step = {}", res);
            return res;
        }).get();
        LOG.debug("acc total = {}", accumulator);
        accumulator.setFace(CVCoreUtils.divideRect(accumulator.getFace(), MIN_COMPLETE_SAMPLES_NUMBER));
        accumulator.setLeftEye(CVCoreUtils.divideRect(accumulator.getLeftEye(), MIN_COMPLETE_SAMPLES_NUMBER));
        accumulator.setRightEye(CVCoreUtils.divideRect(accumulator.getRightEye(), MIN_COMPLETE_SAMPLES_NUMBER));
        accumulator.setLeftPupil(CVCoreUtils.dividePoint(accumulator.getLeftPupil(), MIN_COMPLETE_SAMPLES_NUMBER));
        accumulator.setRightPupil(CVCoreUtils.dividePoint(accumulator.getRightPupil(), MIN_COMPLETE_SAMPLES_NUMBER));
        return accumulator;
    }

}
