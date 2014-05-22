/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.eda.event;

import java.util.Objects;
import org.opencv.core.Point;

/**
 *
 * @author Fieryphoenix
 */
public class MoveEvent implements DEyeTrackEvent<MoveEvent> {

    private final Point oldPosition;
    private final Point newPosition;
    private final Point viewCenter;

    public MoveEvent(Point oldPosition, Point newPosition, Point viewCenter) {
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
        this.viewCenter = viewCenter;
    }

    public Point getOldPosition() {
        return oldPosition;
    }

    public Point getNewPosition() {
        return newPosition;
    }

    public Point getViewCenter() {
        return viewCenter;
    }

    @Override
    public Class<MoveEvent> getType() {
        return (Class<MoveEvent>) getClass();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.oldPosition);
        hash = 37 * hash + Objects.hashCode(this.newPosition);
        hash = 37 * hash + Objects.hashCode(this.viewCenter);
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
        if (!Objects.equals(this.viewCenter, other.viewCenter)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MoveEvent{" + "oldPosition=" + oldPosition + ", newPosition=" + newPosition + ", viewCenter=" + viewCenter + '}';
    }
}
