package com.example.concurrency.multithreading.patterns.asynctosync.async;

/**
 * This is an actual interview question asked at Netflix.
 *
 * Imagine we have an Executor class that performs some useful task asynchronously
 * via the method asynchronousExecution(). In addition, the method accepts a callback object which implements the Callback
 * interface. the object’s done() gets invoked when the asynchronous execution is done.
 * The definition for the involved classes check async package:
 * Note: Solution is added to sync package
 */
public class Main {
    public static void main(String[] args) {
        Executor executor = new Executor();
        executor.asynchronousExecution(() -> {
            System.out.println("I am callback. I am done ");
        });

        System.out.println("Main thread exiting");
    }
}
