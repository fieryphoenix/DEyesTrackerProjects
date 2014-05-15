/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

/**
 *
 * @author Fieryphoenix
 */
public class TaskUtils {

    public static <T extends Object> FutureTask<T> wrapFutureAnd(Callable<T> detectFutureTask, ExecutorService executorService) {
        final FutureTask<T> futureTask = new FutureTask<>(detectFutureTask);
        executorService.execute(futureTask);
        return futureTask;
    }
}
