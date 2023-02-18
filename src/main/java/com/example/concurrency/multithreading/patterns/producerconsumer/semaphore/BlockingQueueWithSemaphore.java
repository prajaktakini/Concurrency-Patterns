package com.example.concurrency.multithreading.patterns.producerconsumer.semaphore;

public class BlockingQueueWithSemaphore<T> {

    private T[] array;
    int size = 0;
    int capacity;
    int head = 0;
    int tail = 0;

    // Use a binary semaphore to exercise mutual exclusion in critical
    // section, however, any thread is free to signal the semaphore, not just the one that acquired it
    CountingSemaphore semLock = new CountingSemaphore(1, 1);
    CountingSemaphore semProducer;
    CountingSemaphore semConsumer;

    public BlockingQueueWithSemaphore(int capacity) {
        this.capacity = capacity;
        this.array = (T[]) new Object[capacity];

        // Set all permits as available
        this.semProducer = new CountingSemaphore(capacity, capacity); // Nothing is produced yet. Semaphore size is maxCount.

        // Set all permits are given out and no permits as available
        this.semConsumer = new CountingSemaphore(capacity, 0); // Nothing to consume. Semaphore size is 0

    }

    public void enqueue(T item) throws InterruptedException {
        semProducer.acquire();
        semLock.acquire(); // Decremented semaphore as one item is already produced

        if (tail == capacity)
            tail = 0;

        array[tail] = item;
        tail++;
        size++;

        semLock.release();
        semConsumer.release(); // Incremented semaphore for consumption
    }

    public T dequeue() throws InterruptedException {
        T item = null;
        semConsumer.acquire(); // Decremented as item is consumed
        semLock.acquire();

        if (head == capacity)
            head = 0;

        item = array[head];
        array[head] = null;
        head++;
        size--;

        semLock.release();
        semProducer.release(); // Incremented semProducer as it created one more space for prodcuer in queue

        return item;
    }

}
