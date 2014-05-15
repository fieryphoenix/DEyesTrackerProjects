/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.event;

import java.util.Objects;

/**
 *
 * @author Fieryphoenix
 */
public class CoreEvent implements DEyeTrackEvent {

    public static enum Action {

        START_DETECTION, HOLD_DETECTION;
    }

    private final Action action;

    public CoreEvent(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public Class<? extends DEyeTrackEvent> getType() {
        return getClass();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.action);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CoreEvent other = (CoreEvent) obj;
        if (this.action != other.action) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CoreEvent{" + "action=" + action + '}';
    }
}