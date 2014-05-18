/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.eda.event;

import java.util.Objects;

/**
 *
 * @author Fieryphoenix
 */
public class StudyProcessEvent implements DEyeTrackEvent<StudyProcessEvent> {

    public static enum Region {

        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, NONE;
    }

    private final Region region;

    public StudyProcessEvent(StudyProcessEvent.Region region) {
        this.region = region;
    }

    @Override
    public Class<StudyProcessEvent> getType() {
        return (Class<StudyProcessEvent>) getClass();
    }

    public Region getRegion() {
        return region;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.region);
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
        final StudyProcessEvent other = (StudyProcessEvent) obj;
        if (this.region != other.region) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StudyProcessEvent{" + "region=" + region + '}';
    }

}
