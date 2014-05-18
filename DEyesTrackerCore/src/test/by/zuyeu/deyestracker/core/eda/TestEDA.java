/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.by.zuyeu.deyestracker.core.eda;

import by.zuyeu.deyestracker.core.eda.event.CoreEvent;
import by.zuyeu.deyestracker.core.eda.event.DEyeTrackEvent;
import by.zuyeu.deyestracker.core.eda.event.handler.DEyesTrackerHandler;
import by.zuyeu.deyestracker.core.eda.router.EventRouter;

/**
 *
 * @author Fieryphoenix
 */
public class TestEDA {

    public static void main(String[] args) {
        EventRouter router = new EventRouter();
        router.registerHandler(CoreEvent.class, (DEyesTrackerHandler) (DEyeTrackEvent event) -> {
            System.out.println("OK!");
        });
        router.sendEvent(new CoreEvent(CoreEvent.EventType.START_DETECTION));
    }
}
