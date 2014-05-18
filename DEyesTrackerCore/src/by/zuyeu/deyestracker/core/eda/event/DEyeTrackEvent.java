/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.eda.event;

/**
 *
 * @author Fieryphoenix
 * @param <T>
 */
public interface DEyeTrackEvent<T extends DEyeTrackEvent> {

    public Class<T> getType();
}
