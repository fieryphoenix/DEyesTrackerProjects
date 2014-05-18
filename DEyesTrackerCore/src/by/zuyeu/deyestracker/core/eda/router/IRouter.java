/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.eda.router;

import by.zuyeu.deyestracker.core.eda.event.DEyeTrackEvent;
import by.zuyeu.deyestracker.core.eda.event.handler.DEyesTrackerHandler;

/**
 *
 * @author Fieryphoenix
 */
public interface IRouter {

    void deleteHandler(final Class<? extends DEyeTrackEvent> event, final DEyesTrackerHandler handler);

    void registerHandler(final Class<? extends DEyeTrackEvent> event, final DEyesTrackerHandler handler);

    void sendEvent(DEyeTrackEvent event);

}
