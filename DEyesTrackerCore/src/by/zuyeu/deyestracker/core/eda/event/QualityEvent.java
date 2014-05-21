/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.eda.event;

/**
 *
 * @author Fieryphoenix
 */
public class QualityEvent implements DEyeTrackEvent<QualityEvent> {

    public static enum QualityType {

        GOOD, NORMAL, BAD;
    }

    private final QualityType qualityType;

    public QualityEvent(QualityType quality) {
        this.qualityType = quality;
    }

    @Override
    public Class<QualityEvent> getType() {
        return (Class<QualityEvent>) getClass();
    }

    public QualityType getQualityType() {
        return qualityType;
    }

    @Override
    public String toString() {
        return "QualityEvent{" + "qualityType=" + qualityType + '}';
    }

}
