package com.example.concurrency.multithreading.patterns.orderprinting.countdownlatch;

/**
 * Suppose there are three threads t1, t2 and t3. t1 prints First,
 * t2 prints Second and t3 prints Third.
 */
public class Main {
    public static void main(String[] args) {
        OrderPrinting orderPrinting = new OrderPrinting();

        OrderPrintingThread t1 = new OrderPrintingThread(orderPrinting, "first");
        OrderPrintingThread t2 = new OrderPrintingThread(orderPrinting, "second");
        OrderPrintingThread t3 = new OrderPrintingThread(orderPrinting, "third");

        t2.start();
        t3.start();
        t1.start();
    }
}
