package com.example.concurrency.multithreading.patterns.nonblockingstack.compareandswap;

public class CASBasedStack<T> {
    private SimulatedCompareAndSwap<StackNode<T>> simulatedCAS;

    public CASBasedStack() {
        simulatedCAS = new SimulatedCompareAndSwap<>(null);
    }

    public void push(T item) {
        StackNode<T> oldHead;
        StackNode<T> newHead;

        do {
            oldHead = simulatedCAS.getValue();
            newHead = new StackNode<>(item);

            newHead.setNext(oldHead);
        } while (!simulatedCAS.compareAndSet(oldHead, newHead));
    }

    public T pop() {
        StackNode<T> returnValue;
        StackNode<T> newHead;

        do {
            returnValue = simulatedCAS.getValue();
            if (returnValue == null) return null;
            newHead = returnValue.getNext();
        } while (!simulatedCAS.compareAndSet(returnValue, newHead));
        return returnValue.getItem();
    }
}
