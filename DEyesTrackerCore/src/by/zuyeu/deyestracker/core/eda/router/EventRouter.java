/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.eda.router;

import by.zuyeu.deyestracker.core.eda.event.DEyeTrackEvent;
import by.zuyeu.deyestracker.core.eda.event.handler.DEyesTrackerHandler;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class EventRouter implements IRouter {

    private static final Logger LOG = LoggerFactory.getLogger(EventRouter.class);

    private final Map<Class<? extends DEyeTrackEvent>, List<DEyesTrackerHandler>> handlers;

    public EventRouter() {
        handlers = new ConcurrentHashMap<>();
    }

    @Override
    public void registerHandler(final Class<? extends DEyeTrackEvent> event, final DEyesTrackerHandler handler) {
        LOG.info("registerHandler() - start: event = {}, handler = {}", event, handler);

        handlers.putIfAbsent(event, new LinkedList<>());
        final List<DEyesTrackerHandler> classHandlers = handlers.get(event);
        classHandlers.add(handler);

        LOG.info("registerHandler() - end;");
    }

    @Override
    public void deleteHandler(final Class<? extends DEyeTrackEvent> event, final DEyesTrackerHandler handler) {
        LOG.info("deleteHandler() - start: event = {}, handler = {}", event, handler);

        if (handlers.containsKey(event)) {
            final List<DEyesTrackerHandler> classHandlers = handlers.get(event);
            classHandlers.remove(handler);
        }

        LOG.info("deleteHandler() - end;");
    }

    @Override
    public void sendEvent(DEyeTrackEvent event) {
        LOG.info("sendEvent() - start: event = {}", event);

        if (handlers.containsKey(event.getType())) {
            final List<DEyesTrackerHandler> classHandlers = handlers.get(event.getType());
            synchronized (classHandlers) {
                classHandlers.stream().forEach(c
                        -> c.handle(event)
                );
            }
        }

        LOG.info("sendEvent() - end;");
    }
}
