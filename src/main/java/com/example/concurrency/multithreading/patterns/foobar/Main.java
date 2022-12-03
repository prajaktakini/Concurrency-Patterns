package com.example.concurrency.multithreading.patterns.foobar;

/**
 * Suppose there are two threads t1 and t2. t1 prints Foo and t2 prints Bar.
 * You are required to write a program which takes a user input n.
 * Then the two threads print Foo and Bar alternately n number of times.
 */
public class Main {
    public static void main(String[] args) {
        FooBar fooBar = new FooBar(3);

        FooBarThread t1 = new FooBarThread(fooBar, "foo");
        FooBarThread t2 = new FooBarThread(fooBar, "bar");

        t2.start();
        t1.start();
    }
}
