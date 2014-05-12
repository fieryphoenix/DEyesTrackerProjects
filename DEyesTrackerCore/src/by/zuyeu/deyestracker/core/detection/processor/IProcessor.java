/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.processor;

/**
 *
 * @author Fieryphoenix
 * @param <T>
 */
public interface IProcessor<T> {

    T[] process(T[] input);
}
