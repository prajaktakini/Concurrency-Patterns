package com.example.concurrency.multithreading.patterns.superman;

/**
 * Below class demonstrates Eager Initialization because the singleton is initialized irrespective of whether it is used or not.
 * Also, note that we don't need any explicit thread synchronization because it is provided for free by the JVM when it loads
 * the class
 *
 * The drawback of this approach is if the singleton object is never used then we have spent resources creating and retaining the object in memory.
 * The static member is initialized when the class is loaded. Additionally, the singleton instance can be expensive
 * to create and we may want to delay creating the object till it is actually required.
 */
public class SupermanSingletonEagerInitialisation {

    public static void main(String[] args) {
        SupermanSingletonEagerInitialisation superman = SupermanSingletonEagerInitialisation.getInstance();
        superman.fly();
    }
    private static SupermanSingletonEagerInitialisation superman = new SupermanSingletonEagerInitialisation(); // Or use static
    // block

    // Or use static block
    static {
        try {
            superman = new SupermanSingletonEagerInitialisation();
        } catch (Exception ex) {
            // Any exception here
        }
    }

    private SupermanSingletonEagerInitialisation() {
      // Should never be initialised
    }

    public static SupermanSingletonEagerInitialisation getInstance() {
        return superman;
    }

    public void fly() {
        System.out.println("Superman is flying......!");
    }


}
