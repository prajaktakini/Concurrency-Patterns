package com.example.concurrency.multithreading.patterns.superman;

/**
 * Another implementation of the singleton pattern is the holder or Bill Pugh's singleton.
 * The idea is to create a private nested static class that holds the static instance.
 * The nested class Holder isn't loaded when the outer class SupermanHolder is loaded. The inner static class Holder is loaded
 * only when the method getInstance() is invoked. This saves us from eagerly initializing the singleton instance.
 */
public class SupermanHolder {

    public static void main(String[] args) {
        SupermanHolder superman = SupermanHolder.getInstance();
        superman.fly();
    }

    private SupermanHolder() {
        // Should never be instantiated
    }

    private static class Holder {
        private static final SupermanHolder superman = new SupermanHolder();
    }
        public static SupermanHolder getInstance() {
            return Holder.superman;
        }

        public void fly() {
            System.out.println("Superman is flying......!");
        }
}
