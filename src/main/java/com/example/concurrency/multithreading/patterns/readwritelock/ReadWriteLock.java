package com.example.concurrency.multithreading.patterns.readwritelock;

/**
 * Before we allow a reader to enter the critical section,
 * we need to make sure that there's no writer in progress. It is ok to have other readers in the critical section since they aren't making any modifications
 * Before we allow a writer to enter the critical section,
 * we need to make sure that there's no reader or writer in the critical section.
 *
 * Note: All the methods are synchronized on the ReadWriteLock object itself.
 */
public class ReadWriteLock {

    // Keeps track of if any of the writer is writing in critical section
    private boolean isWriteLocked = false;

    // Keeps track of how many readers are currently reading
    private int readers = 0;

    public synchronized void acquireReadLock() throws InterruptedException {

        // If writer is writing currently, don't allow reading. Wait until writer releases the lock
        // while for handling spurious wake-ups
        while (isWriteLocked) {
            wait();
        }

        readers++;

    }

    public synchronized void releaseReadLock() {
        readers--;
        notify(); // This is to notify the thread waiting to acquire write lock
    }

    /**
     * 1. Check if other writer has acquired the lock, if yes, then wait
     * 2. Check if any other reader is not reading at the same time, if yes, then wait
     */
    public synchronized void acquireWriteLock() throws InterruptedException {

        while (isWriteLocked || readers > 0) {
            wait();
        }

        isWriteLocked = true;

    }

    public synchronized void releaseWriteLock() {
        isWriteLocked = false;
        notify();
    }

}
