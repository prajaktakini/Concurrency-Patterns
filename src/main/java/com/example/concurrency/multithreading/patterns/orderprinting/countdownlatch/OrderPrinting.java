package com.example.concurrency.multithreading.patterns.orderprinting.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class OrderPrinting {

    private CountDownLatch latch1;
    private CountDownLatch latch2;
    public OrderPrinting() {
        this.latch1 = new CountDownLatch(1);
        this.latch2 = new CountDownLatch(1);
    }

    public void printFirst() {
        System.out.println("First");
        latch1.countDown();
    }

    public void printSecond() throws InterruptedException {
        latch1.await(); // Wait until first is printed
        System.out.println("Second");
        latch2.countDown();

    }

    public void printThird() throws InterruptedException {
        latch2.await(); // Wait until second is printed
        System.out.println("Third");
    }
}
