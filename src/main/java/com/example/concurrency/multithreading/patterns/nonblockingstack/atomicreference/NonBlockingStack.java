package com.example.concurrency.multithreading.patterns.nonblockingstack.atomicreference;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class NonBlockingStack<T> {
    private AtomicReference<StackNode<T>> top = new AtomicReference<>();
    private AtomicInteger count = new AtomicInteger(0);

    public int getSize() {
        return count.get();
    }

    public void push(T item) {
        StackNode<T> oldHead;
        StackNode<T> newHead;

        do {
            oldHead = top.get();
            newHead = new StackNode<>(item);
            newHead.setNext(oldHead);
        } while (!top.compareAndSet(oldHead, newHead));

        count.incrementAndGet();
    }

    public T pop() {
        StackNode<T> oldTop;
        StackNode<T> newTop;

        do {
            oldTop = top.get();
            if (oldTop == null) return null;
            newTop = oldTop.getNext();
        } while (!top.compareAndSet(oldTop, newTop));
        count.decrementAndGet();
        return oldTop.getItem();
    }
}
