package com.example.concurrency.multithreading.patterns.nonblockingstack.synchronised;

public class StackNode<T> {
    private T item;

    private StackNode<T> next;

    public StackNode(final T item) {
        this.item = item;
    }

    public StackNode<T> getNext() {
        return next;
    }

    public void setNext(StackNode<T> stackNode) {
        this.next = stackNode;
    }

    public T getItem() {
        return item;
    }
}
