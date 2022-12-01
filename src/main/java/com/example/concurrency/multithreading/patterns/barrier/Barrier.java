package com.example.concurrency.multithreading.patterns.barrier;

public class Barrier {

    private int count = 0; // Keeps track of number of threads at barrier
    private int released = 0; // Keeps track of total threads that crossed barrier
    private int totalThreads;

    public Barrier(int totalThreads) {
        this.totalThreads = totalThreads;
    }

    public synchronized void await() throws InterruptedException {
        // Below while loop is to not allow other threads to cross barrier unless all the threads at the barrier(from previous
        // barrier) have crossed it
        while (count == totalThreads) {
            wait();
        }

        count++; // Increment the count as the thread has arrived at the barrier

        if (count == totalThreads) {
            // last thread has reached the barrier, wake up all waiting threads
            notifyAll();

            released = totalThreads; // Indicate how many threads can cross this barrier
        } else {
            // Wait till all threads reach barrier
            while (count < totalThreads) {
                wait();
            }
        }

        released--; // Release this thread

        if (released == 0) {
            count = 0; // As all threads have crossed the barrier, reset count variable back to 0

            // Don't forget to notify the threads waiting on line #16 from next barrier threads set
            notifyAll();
        }
    }


}
