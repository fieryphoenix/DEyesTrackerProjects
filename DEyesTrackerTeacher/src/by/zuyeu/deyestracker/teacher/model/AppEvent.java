/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.teacher.model;

import by.zuyeu.deyestracker.core.eda.event.DEyeTrackEvent;

/**
 *
 * @author Fieryphoenix
 */
public class AppEvent implements DEyeTrackEvent<AppEvent> {

    public static enum Action {

        START, END, ERROR;
    }
    private final Action action;
    private Object packet;

    public AppEvent(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public Object getPacket() {
        return packet;
    }

    public void setPacket(Object packet) {
        this.packet = packet;
    }

    @Override
    public Class<AppEvent> getType() {
        return (Class<AppEvent>) getClass();
    }

    @Override
    public String toString() {
        return "AppEvent{" + "action=" + action + ", packet=" + packet + '}';
    }
}
