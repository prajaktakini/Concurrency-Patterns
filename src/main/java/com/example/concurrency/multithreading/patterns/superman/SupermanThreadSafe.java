package com.example.concurrency.multithreading.patterns.superman;

/**
 * Below class demonstrates Thread Safe Singleton initialisation
 * It lazily creates the singleton object. Lazy initialisation means delaying creating a resource t
 * ill the time of its first use.
 * This saves precious resources if the singleton object is never used or is expensive to create.
 */
public class SupermanThreadSafe {

    public static void main(String[] args) {
        SupermanThreadSafe superman = SupermanThreadSafe.getInstance();
        superman.fly();
    }
    private static SupermanThreadSafe superman;

    private SupermanThreadSafe() {
      // Should never be initialised
    }

    /**
     * The method is synchronized on the class object. The problem with the above approach is we are serializing access for
     * threads even after the singleton object is safely initialized the first time.
     * This slows down performance unnecessarily. The next evolution is to move the lock inside the method.
     * @return
     */
    private synchronized static SupermanThreadSafe getInstance() {
        /**
         * The con of the above solution is that every invocation of the getInstance() method
         * causes the invoking thread to synchronize, which is prohibitively more expensive
         * in terms of performance than non-synchronized snippets of code
         */
        if (superman == null) {
            superman = new SupermanThreadSafe();
        }

        return superman;
    }

    public void fly() {
        System.out.println("Superman is flying......!");
    }


}
