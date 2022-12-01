package com.example.concurrency.multithreading.patterns.deferred.callback;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        DeferredCallbackExecutor.runLateThenEarlyCallback();
    }
}
