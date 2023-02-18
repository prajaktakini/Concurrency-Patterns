package com.example.concurrency.multithreading.patterns.asynctosync.sync;

public class SynchronousExecutor extends Executor {

    @Override
    public void asynchronousExecution(final Callback callback) throws InterruptedException {
        Object signal = new Object();
        final boolean[] isDone = new boolean[1]; // We can instead use non-final boolean variable as well. But array is make
        // sure we can still define final

        // Define another call, Wrap given callback in this method
        Callback cb = new Callback() {
            @Override
            public void done() {
                callback.done();

                synchronized (signal) {
                    signal.notify();
                    isDone[0] = true;
                }
            }
        };

        // Call the asynchronous executor
        super.asynchronousExecution(cb);

        synchronized (signal) {
            while (!isDone[0]) {
                signal.wait();
            }
        }
    }
}
