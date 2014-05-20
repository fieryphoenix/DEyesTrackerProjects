/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.teacher.scenario;

import by.zuyeu.deyestracker.core.detection.model.StudyResult;
import by.zuyeu.deyestracker.core.detection.tracker.ScreenPointTracker;
import by.zuyeu.deyestracker.core.eda.event.StudyProcessEvent;
import by.zuyeu.deyestracker.core.eda.event.handler.DEyesTrackerHandler;
import by.zuyeu.deyestracker.core.eda.router.EventRouter;
import by.zuyeu.deyestracker.core.eda.router.IRouter;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import by.zuyeu.deyestracker.core.video.sampler.FaceInfoSampler;
import by.zuyeu.deyestracker.core.video.sampler.ISampler;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class TeachingScenario extends Task<StudyResult> {

    private static final Logger LOG = LoggerFactory.getLogger(TeachingScenario.class);

    private final BooleanProperty tl;
    private final BooleanProperty tr;
    private final BooleanProperty bl;
    private final BooleanProperty br;

    public TeachingScenario(BooleanProperty tl, BooleanProperty tr, BooleanProperty bl, BooleanProperty br) {
        this.tl = tl;
        this.tr = tr;
        this.bl = bl;
        this.br = br;
    }

    @Override
    protected StudyResult call() throws DEyesTrackerException {
        LOG.info("call - start;");
        final IRouter router = new EventRouter();
        router.registerHandler(StudyProcessEvent.class, (DEyesTrackerHandler<StudyProcessEvent>) (StudyProcessEvent event) -> {
            Platform.runLater(new PointerSwitcher(event.getRegion()));
            LOG.trace("e = " + event);
        });
        StudyResult result = null;
        try {
            final ISampler sampler = new FaceInfoSampler();
            ScreenPointTracker tracker = new ScreenPointTracker.ScreenPointTrackerBuilder().setRouter(router).setSampler(sampler).createScreenPointTracker();

            result = tracker.getStudyResult();
            tracker.stop();
        } catch (DEyesTrackerException e) {
            LOG.warn("init core elements failure", e);
            throw e;
        }
        LOG.info("call - ends: result = " + result);
        return result;
    }

    private class PointerSwitcher extends Task<Void> {

        private final StudyProcessEvent.Region region;

        public PointerSwitcher(StudyProcessEvent.Region region) {
            this.region = region;
        }

        @Override
        protected Void call() throws Exception {
            tl.set(false);
            tr.set(false);
            bl.set(false);
            br.set(false);
            switch (region) {
                case BOTTOM_LEFT:
                    bl.set(true);
                    break;
                case BOTTOM_RIGHT:
                    br.set(true);
                    break;
                case TOP_LEFT:
                    tl.set(true);
                    break;
                case TOP_RIGHT:
                    tr.set(true);
                    break;
                default:
                    throw new IllegalStateException();
            }
            return null;
        }

    }

}
