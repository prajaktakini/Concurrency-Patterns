package com.example.concurrency.multithreading.patterns.producerconsumer.semaphore;

public class CountingSemaphore {
    private int usedPermits = 0; // Denotes number of permits given out
    int maxCount;

    public CountingSemaphore(final int maxCount, int initialCapacity) {
        this.maxCount = maxCount;
        this.usedPermits = maxCount - initialCapacity; // For producer, initial capacity is maxCount whereas for consumer it is 0
    }

    public synchronized void acquire() throws InterruptedException {
        while (usedPermits == maxCount) {
            wait();
        }

        notify();
        usedPermits++;
    }

    public synchronized void release() throws InterruptedException {
        while (usedPermits == 0) {
            wait();
        }

        usedPermits--;
        notify();
    }
}
