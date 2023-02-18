package com.example.concurrency.multithreading.patterns.asynctosync.sync;


public class Executor {
    public void asynchronousExecution(Callback callback) throws InterruptedException {
        Thread t = new Thread(() -> {
            // Do some useful work
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            callback.done();
        });

        t.start();
    }
}
