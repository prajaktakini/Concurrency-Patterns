package com.example.concurrency.multithreading.patterns.foobar;

public class FooBarThread extends Thread {

    private FooBar fooBar;
    private String method;

    public FooBarThread(FooBar fooBar, String method) {
        this.fooBar = fooBar;
        this.method = method;
    }

    public void run() {
        if ("foo".equals(method)) {
            fooBar.printFoo();
        } else if ("bar".equals(method)) {
            fooBar.printBar();
        }
    }

}
