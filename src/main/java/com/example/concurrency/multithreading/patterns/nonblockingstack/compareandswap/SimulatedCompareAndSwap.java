package com.example.concurrency.multithreading.patterns.nonblockingstack.compareandswap;

public class SimulatedCompareAndSwap<T> {

    private T value;

    public SimulatedCompareAndSwap(final T initialValue) {
        this.value = initialValue;
    }

    synchronized T getValue() {
        return value;
    }

    synchronized T compareAndSwap(T expectedValue, T newValue) {
        if (value == null) {
            if (expectedValue == null) {
                value = newValue;
            }
            return null;
        }

        if (value.equals(expectedValue)) {
            value = newValue;
            return expectedValue;
        }

        // Return current value
        return value;
    }

    synchronized boolean compareAndSet(T expectedValue, T newValue) {
        T returnVal = compareAndSwap(expectedValue, newValue);
        if (returnVal == null && expectedValue == null) return true;
        else if (returnVal == null && expectedValue != null) return false;
        else {
            return returnVal.equals(expectedValue);
        }
    }
}
