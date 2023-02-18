package com.example.concurrency.multithreading.patterns.semaphore;

public class CountingSemaphore {

    int usedPermits = 0;
    int maxPermits;

    public CountingSemaphore(final int maxPermits) {
        this.maxPermits = maxPermits;
    }

    public synchronized void acquire() throws InterruptedException {
        // while is used to handle spurious wake-ups
        while (usedPermits == maxPermits) {
            wait();
        }

        // We can alternate below sequence as no other thread can execute this block until this thread comes out of acquire
        usedPermits++;
        notify();
    }

    public synchronized void release() throws InterruptedException {
        // while is used to handle spurious wake-ups
        while (usedPermits == 0) {
            wait();
        }
        // We can alternate below sequence as no other thread can execute this block until this thread comes out of release
        usedPermits--;
        notify();
    }
}
