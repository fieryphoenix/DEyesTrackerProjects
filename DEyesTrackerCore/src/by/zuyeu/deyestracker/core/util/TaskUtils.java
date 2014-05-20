/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.util;

import by.zuyeu.deyestracker.core.detection.task.IDetectTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

/**
 *
 * @author Fieryphoenix
 */
public class TaskUtils {

    private TaskUtils() {
        throw new IllegalAccessError("unconstructable class");
    }

    public static <T extends Object> FutureTask<T> wrapFutureAnd(IDetectTask<T> detectFutureTask, ExecutorService executorService) {
        final FutureTask<T> futureTask = new FutureTask<>(detectFutureTask);
        executorService.execute(futureTask);
        return futureTask;
    }
}
