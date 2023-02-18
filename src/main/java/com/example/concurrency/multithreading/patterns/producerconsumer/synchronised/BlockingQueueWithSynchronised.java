package com.example.concurrency.multithreading.patterns.producerconsumer.synchronised;

public class BlockingQueueWithSynchronised<T> {
    private T[] array;
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    private int capacity;
    private Object lock = new Object();

    public BlockingQueueWithSynchronised(final int capacity) {
        array = (T[]) new Object[capacity];
        this.capacity = capacity;
    }

    public void enqueue(T item) throws InterruptedException {
        synchronized (lock) {
            while (size == capacity) {
                lock.wait();
            }

            if (tail == capacity) {
                tail = 0;
            }

            array[tail] = item;
            tail++;
            size++;

            lock.notifyAll();
        }
    }

    public T dequeue() throws InterruptedException {
        T item = null;
        synchronized (lock) {
            while (size == 0) {
                lock.wait();
            }

            if (head == capacity)
                head = 0;

            item = array[head];
            array[head] = null;
            head++;
            size--;

            lock.notifyAll();
        }

        return item;
    }
}
