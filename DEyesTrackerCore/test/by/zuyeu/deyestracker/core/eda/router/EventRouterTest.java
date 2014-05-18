/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.eda.router;

import by.zuyeu.deyestracker.core.eda.event.CoreEvent;
import by.zuyeu.deyestracker.core.eda.event.DEyeTrackEvent;
import by.zuyeu.deyestracker.core.eda.event.handler.DEyesTrackerHandler;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Fieryphoenix
 */
public class EventRouterTest {

    public EventRouterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of registerHandler method, of class EventRouter.
     */
    @Test
    public void testRegisterHandler() {
        System.out.println("registerHandler");
        Class<? extends DEyeTrackEvent> event = CoreEvent.class;
        DEyesTrackerHandler handler = (DEyesTrackerHandler<CoreEvent>) (CoreEvent event1)
                -> System.out.println("OK!");
        EventRouter instance = new EventRouter();
        instance.registerHandler(event, handler);
    }

    /**
     * Test of deleteHandler method, of class EventRouter.
     */
    @Test
    public void testDeleteHandler() {
        System.out.println("deleteHandler");
        Class<? extends DEyeTrackEvent> event = CoreEvent.class;
        DEyesTrackerHandler handler = (DEyesTrackerHandler<CoreEvent>) (CoreEvent event1)
                -> System.out.println("OK!");
        EventRouter instance = new EventRouter();
        instance.registerHandler(event, handler);
        instance.deleteHandler(event, handler);
    }

    /**
     * Test of sendEvent method, of class EventRouter.
     */
    @Test
    public void testSendEvent() {
        System.out.println("sendEvent");
        Class<? extends DEyeTrackEvent> event = CoreEvent.class;
        DEyesTrackerHandler handler = (DEyesTrackerHandler<CoreEvent>) (CoreEvent e) -> {
            System.out.println("OK! :" + e.toString());
        };
        EventRouter instance = new EventRouter();
        instance.registerHandler(event, handler);
        instance.sendEvent(new CoreEvent(CoreEvent.EventType.START_DETECTION));
    }

}
