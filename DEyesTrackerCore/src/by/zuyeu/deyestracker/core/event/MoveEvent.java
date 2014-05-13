/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.event;

import java.util.Objects;
import org.opencv.core.Point;

/**
 *
 * @author Fieryphoenix
 */
public class MoveEvent implements DEyeTrackEvent {

    private final Point oldPosition;
    private final Point newPosition;

    public MoveEvent(Point oldPosition, Point newPosition) {
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
    }

    public Point getOldPosition() {
        return oldPosition;
    }

    public Point getNewPosition() {
        return newPosition;
    }

    @Override
    public Class<? extends DEyeTrackEvent> getType() {
        return getClass();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.oldPosition);
        hash = 79 * hash + Objects.hashCode(this.newPosition);
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
        final MoveEvent other = (MoveEvent) obj;
        if (!Objects.equals(this.oldPosition, other.oldPosition)) {
            return false;
        }
        if (!Objects.equals(this.newPosition, other.newPosition)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MoveEvent{" + "oldPosition=" + oldPosition + ", newPosition=" + newPosition + '}';
    }

}
