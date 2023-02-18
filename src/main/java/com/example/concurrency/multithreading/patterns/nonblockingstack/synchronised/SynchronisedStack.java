package com.example.concurrency.multithreading.patterns.nonblockingstack.synchronised;

public class SynchronisedStack<T> {
    private StackNode<T> top;

    public synchronized void push(T item) {
        if (top == null) {
            top = new StackNode<>(item);
        } else {
            StackNode<T> oldHead = top;
            StackNode<T> top = new StackNode<>(item);
            top.setNext(oldHead);
        }
    }

    public synchronized T pop() {
        if (top == null) {
            return null;
        }

        StackNode<T> oldHead = top;
        top = top.getNext();
        return oldHead.getItem();
    }
}
