/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.task;

import java.util.concurrent.Callable;

/**
 *
 * @author Fieryphoenix
 * @param <T>
 */
public interface IDetectTask<T> extends Callable<T> {

}
