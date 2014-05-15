/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.event;

/**
 *
 * @author Fieryphoenix
 */
public class StudyProcessEvent implements DEyeTrackEvent {

    public static enum Region {

        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;
    }

    private final Region region;

    public StudyProcessEvent(StudyProcessEvent.Region region) {
        this.region = region;
    }

    @Override
    public Class<? extends DEyeTrackEvent> getType() {
        return getClass();
    }

    public Region getRegion() {
        return region;
    }

}
