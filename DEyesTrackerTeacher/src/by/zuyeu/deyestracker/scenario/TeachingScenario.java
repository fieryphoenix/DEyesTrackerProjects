/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.scenario;

import by.zuyeu.deyestracker.core.detection.tracker.ScreenPointTracker;
import by.zuyeu.deyestracker.core.eda.event.StudyProcessEvent;
import by.zuyeu.deyestracker.core.eda.event.handler.DEyesTrackerHandler;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import by.zuyeu.deyestracker.core.detection.model.StudyResult;
import by.zuyeu.deyestracker.core.eda.router.EventRouter;
import by.zuyeu.deyestracker.core.eda.router.IRouter;
import by.zuyeu.deyestracker.core.video.sampler.FaceInfoSampler;
import by.zuyeu.deyestracker.core.video.sampler.ISampler;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;

/**
 *
 * @author Fieryphoenix
 */
public class TeachingScenario extends Task<StudyResult> {

    private final BooleanProperty tl;
    private final BooleanProperty tr;
    private final BooleanProperty bl;
    private final BooleanProperty br;

    public static void main(String[] args) {
        BooleanProperty bp = new SimpleBooleanProperty();
        TeachingScenario scenario = new TeachingScenario(bp, bp, bp, bp);
        try {
            scenario.call();
        } catch (DEyesTrackerException ex) {
            System.out.println("ex = " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public TeachingScenario(BooleanProperty tl, BooleanProperty tr, BooleanProperty bl, BooleanProperty br) {
        this.tl = tl;
        this.tr = tr;
        this.bl = bl;
        this.br = br;
    }

    @Override
    protected StudyResult call() throws DEyesTrackerException {
        System.out.println("call - start;");
        final IRouter router = new EventRouter();
        router.registerHandler(StudyProcessEvent.class, (DEyesTrackerHandler<StudyProcessEvent>) (StudyProcessEvent event) -> {
            Platform.runLater(new PointerSwitcher(event.getRegion()));
            System.out.println("e = " + event);
        });
        StudyResult result = null;
        try {
            final ISampler sampler = new FaceInfoSampler();
            ScreenPointTracker tracker = new ScreenPointTracker.ScreenPointTrackerBuilder().setRouter(router).setSampler(sampler).createScreenPointTracker();

            result = tracker.getStudyResult();
            tracker.stop();
        } catch (DEyesTrackerException e) {
            System.out.println(e.getCode() + e.getMessage());
            throw e;
        }
        System.out.println("call - ends: result = " + result);
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
            }
            return null;
        }

    }

}
