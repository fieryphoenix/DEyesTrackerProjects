/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.trank;

import by.zuyeu.deyestracker.core.event.CoreEvent;
import by.zuyeu.deyestracker.core.event.DEyeTrackEvent;
import by.zuyeu.deyestracker.core.event.handler.DEyesTrackerHandler;
import by.zuyeu.deyestracker.core.router.EventRouter;

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
