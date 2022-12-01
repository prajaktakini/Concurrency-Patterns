package com.example.concurrency.multithreading.patterns.deferredcallback;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        DeferredCallbackExecutor.runLateThenEarlyCallback();
    }
}
