package com.example.concurrency.multithreading.patterns.orderprinting.waitnotify;

public class OrderPrinting {
    private int count;

    public OrderPrinting() {
        this.count = 1;
    }

    public void printFirst() {
        synchronized (this) {
            System.out.println("First");
            count++;
            this.notifyAll();
        }
    }

    public void printSecond() throws InterruptedException {
        synchronized (this) {
            while (count != 2) {
                wait(); // Wait until count equals to 2
            }

            System.out.println("Second");
            count++;
            this.notifyAll();
        }
    }

    public void printThird() throws InterruptedException {
        synchronized (this) {
            while (count != 3) {
                wait(); // Wait until count equals to 3
            }
            System.out.println("Third");
        }
    }
}
