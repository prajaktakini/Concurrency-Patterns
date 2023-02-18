package com.example.concurrency.multithreading.patterns.asynctosync.sync;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        SynchronousExecutor executor = new SynchronousExecutor();
        executor.asynchronousExecution(() -> {
            System.out.println("I am callback. I am done");
        });

        System.out.println("Main thread exiting");
    }
}
