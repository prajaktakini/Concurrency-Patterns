package com.example.concurrency.multithreading.patterns.superman;

/**
 * Requires to apply Java's singleton pattern
 * Uses Double Checked Locking strategy (DCL)
 */
public class Superman {

    private static volatile Superman superman; // Volatile provides thread safety and atomicity

    private Superman() {
        // Should never be instantiated
    }

    /**
     * Below code uses double check locking (DCL). The idea is that, we do two checks for superman == null in nested fashion
     * The first check without sync and next check with sync block. Once singleton has been initialised, all future invocations
     * of getInstance() method don't pass first null check and return instance without involving synchronisation.
     * That means threads only synchronise when the singleton instance has not yet been initialised
     * @return
     */
    public static Superman getInstance() {
        // Check is object is uninitialised
        if (superman == null) {

            /**
             * Now synchronise on the class object so only 1 thread can initialise the superman object
             * Note that: Multiple threads can actually find superman object to be null and fall into the
             * first if clause
             */
            synchronized (Superman.class) {

                /**
                 * Must check once more if the superman is still null. It is possible that another
                 * thread might have initialised it already as multiple threads could have made past the first if check
                 */
                if (superman == null) {
                    superman = new Superman();
                }
            }
        }
        return superman;
    }

    public void fly() {
        System.out.println("SuperMan started flying........!");
    }
}
