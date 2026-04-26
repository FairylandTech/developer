/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2025-08-23 16:54:24 UTC+08:00
 ****************************************************/
package org.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Beau Dean
 */
public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableImpl callable = new CallableImpl();
        FutureTask<Integer> integerFutureTask = new FutureTask<>(callable);
        
        Thread thread = new Thread(integerFutureTask);
        
        thread.start();
        
        Integer i = integerFutureTask.get();
        System.out.println(i);
    }
}
