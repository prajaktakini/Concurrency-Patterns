package com.example.concurrency.multithreading.patterns.nonblockingstack.atomicreference;

import com.example.concurrency.multithreading.patterns.nonblockingstack.synchronised.SynchronisedStack;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Design a stack that doesn’t use locks or synchronized and is thread-safe.
 * You may assume that you are provided with an application-level API that mocks the
 * hardware instruction compare-and-swap, to atomically compare and swap values at a memory location.
 *
 * Below is the example of stack that uses sychronised concurrency construct
 */
public class Main {

    public static void main(String[] args) throws Exception {
        NonBlockingStack<Integer> stackIntegers = new NonBlockingStack<>();
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        int numThreads = 2;
        CyclicBarrier barrier = new CyclicBarrier(numThreads);

        Integer testValue = new Integer(51);
        try {
            for (int i = 0; i < numThreads; i++) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 1000; i++) {
                            stackIntegers.push(testValue);
                        }

                        try {
                            barrier.await();
                        } catch (InterruptedException | BrokenBarrierException ex) {
                            ex.printStackTrace();
                        }

                        for (int i = 0; i < 1000; i++) {
                            stackIntegers.pop();
                        }
                    }
                });

            }
            System.out.println("Number of elements in the stack = " + stackIntegers.getSize());


        } finally {
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.HOURS);
        }
    }
}
