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
public interface DEyeTrackEvent {

    public Class<? extends DEyeTrackEvent> getType();
}
