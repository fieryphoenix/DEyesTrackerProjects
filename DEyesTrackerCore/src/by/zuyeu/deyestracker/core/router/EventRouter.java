/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.router;

import by.zuyeu.deyestracker.core.event.DEyeTrackEvent;
import by.zuyeu.deyestracker.core.event.handler.DEyesTrackerHandler;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Fieryphoenix
 */
public class EventRouter implements IRouter {

    private final Map<Class<? extends DEyeTrackEvent>, List<DEyesTrackerHandler>> handlers;

    public EventRouter() {
        handlers = new ConcurrentHashMap<>();
    }

    @Override
    public void registerHandler(final Class<? extends DEyeTrackEvent> event, final DEyesTrackerHandler handler) {
        if (!handlers.containsKey(event)) {
            handlers.put(event, new LinkedList<>());
        }
        List<DEyesTrackerHandler> classHandlers = handlers.get(event);
        classHandlers.add(handler);
    }

    @Override
    public void deleteHandler(final Class<? extends DEyeTrackEvent> event, final DEyesTrackerHandler handler) {
        if (handlers.containsKey(event)) {
            final List<DEyesTrackerHandler> classHandlers = handlers.get(event);
            classHandlers.remove(handler);
        }
    }

    @Override
    public void sendEvent(DEyeTrackEvent event) {
        if (handlers.containsKey(event.getType())) {
            final List<DEyesTrackerHandler> classHandlers = handlers.get(event.getType());
            classHandlers.stream().parallel().forEach(c -> {
                c.handle(event);
            });
        }
    }
}
