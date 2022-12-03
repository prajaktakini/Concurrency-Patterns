package com.example.concurrency.multithreading.patterns.deferredcallback;

/**
 * Asynchronous programming involves being able to execute functions at a future occurrence of some event.
 * Design and implement a thread-safe class that allows registration of callback methods
 * that are executed after a user specified time interval in seconds has elapsed.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        DeferredCallbackExecutor.runLateThenEarlyCallback();
    }
}
