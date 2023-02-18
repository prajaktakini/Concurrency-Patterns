package com.example.concurrency.multithreading.patterns.producerconsumer.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueWithMutex<T> {
    private T[] array;

    private Lock lock = new ReentrantLock();
    private int size;
    private int head;
    private int tail;
    private int capacity;

    public BlockingQueueWithMutex(int capacity) {
        array = (T[]) new Object[capacity];
        this.capacity = capacity;
        size = 0;
        head = 0;
        tail = 0;
    }

    public void enqueue(T item) throws InterruptedException {
        lock.lock();
        while (size == capacity) {
            // Release the mutex to give other threads
            lock.unlock();

            // Reacquire the mutex before checking the
            // condition
            lock.lock();
        }

        if (tail == capacity) {
            tail = 0;
        }

        array[tail] = item;
        tail++;
        size++;

        lock.unlock();
    }

    public T dequeue() {
        T item = null;

        lock.lock();

        while (size == 0) {
            lock.unlock();

            lock.lock();
        }

        if (head == capacity) {
            head = 0;
        }

        T element = array[head];
        head++;
        size--;

        lock.unlock();
        return element;
    }


}
