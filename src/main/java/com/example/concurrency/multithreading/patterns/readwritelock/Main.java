package com.example.concurrency.multithreading.patterns.readwritelock;

// TODO prajakta: Add thread timeout for 2nd thread
public class Main {

    public static void main(String[] args) throws InterruptedException {
        final ReadWriteLock lock = new ReadWriteLock();

        // Writer2 acquires lock indefinitely
        Thread writer1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Attempting to acquire write lock by writer1 thread at " + System.currentTimeMillis());
                    lock.acquireWriteLock();
                    System.out.println("Write lock acquired by writer1 at " + System.currentTimeMillis());

                    // Simulates write lock being held indefinitely
                    for (; ; ) {
                        Thread.sleep(500);
                    }

                } catch (InterruptedException ex) {
                    System.out.println("Exception occurred while attempting write lock by writer1 " + ex);
                }
            }
        });


        Thread writer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Attempting to acquire write lock by writer2 thread at " + System.currentTimeMillis());
                    lock.acquireWriteLock();
                    System.out.println("Write lock acquired by writer2 at " + System.currentTimeMillis());
                } catch (InterruptedException ex) {
                    System.out.println("Exception occurred while attempting write lock by writer2 " + ex);
                }
            }
        });

        Thread reader1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.acquireReadLock();
                    System.out.println("Read lock acquired by reader1 at " + System.currentTimeMillis());
                } catch (InterruptedException ex) {
                    System.out.println("Exception occurred while attempting read lock by reader1 " + ex);
                }
            }
        });

        Thread reader2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Attempting to release read lock by reader2 at " + System.currentTimeMillis());
                lock.releaseReadLock();
                System.out.println("Read lock released by reader2 at " + System.currentTimeMillis());
            }
        });

        reader1.start(); // Reader to acquire first read lock
        writer1.start(); // Will hold write lock indefinitely

        Thread.sleep(3000);

        reader2.start(); // Reader to release read lock
        Thread.sleep(1000);

        writer2.start(); // This thread will timeout as write lock is indefinitely held by writer1. We may need to add timeout

        reader1.join();
        reader2.join();
        writer2.join();
    }
}
