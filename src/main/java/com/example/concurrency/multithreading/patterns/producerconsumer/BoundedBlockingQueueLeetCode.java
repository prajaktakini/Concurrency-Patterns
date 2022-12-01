package com.example.concurrency.multithreading.patterns.producerconsumer;

// https://leetcode.com/problems/design-bounded-blocking-queue/discuss/380140/Java-ReentrantLock-%2B-Condition-Solution
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

class BoundedBlockingQueueLeetCode {
    private ReentrantLock lock = new ReentrantLock();
    private Condition full = lock.newCondition();
    private Condition empty = lock.newCondition();
    private int[] queue;
    private int tail = 0;
    private int head = 0;
    private int size = 0;

    public BoundedBlockingQueueLeetCode(int capacity) {
        queue = new int[capacity];
    }

    public void enqueue(int element) throws InterruptedException {
        lock.lock();
        try {
            while(size == queue.length) {
                // buffer is full
                full.await();
            }
            queue[tail++] = element;
            if (tail == queue.length)
                tail = 0;
            //tail %= queue.length;
            size++;
            empty.signal();
        } finally {
            lock.unlock();
        }
    }

    public int dequeue() throws InterruptedException {
        lock.lock();
        try {
            while(size == 0) {
                // queue is empty
                empty.await();
            }
            int res = queue[head++];
            if (head == queue.length)
                head = 0;
            //head %= queue.length;
            size--;
            full.signal();
            return res;
        } finally {
            lock.unlock();
        }
    }

    public int size() throws InterruptedException {
        lock.lock();
        try {
            return this.size;
        } finally {
            lock.unlock();
        }
    }
}

