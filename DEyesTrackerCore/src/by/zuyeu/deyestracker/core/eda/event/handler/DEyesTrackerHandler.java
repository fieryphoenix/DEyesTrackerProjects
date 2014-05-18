/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.eda.event.handler;

import by.zuyeu.deyestracker.core.eda.event.DEyeTrackEvent;

/**
 *
 * @author Fieryphoenix
 * @param <T>
 */
public interface DEyesTrackerHandler<T extends DEyeTrackEvent> {

    public abstract void handle(T event);
}
