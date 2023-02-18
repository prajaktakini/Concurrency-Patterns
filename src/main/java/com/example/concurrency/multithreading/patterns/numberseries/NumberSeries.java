package com.example.concurrency.multithreading.patterns.numberseries;

import java.util.concurrent.Semaphore;

public class NumberSeries {

    private int n;
    private Semaphore zeroSem, oddSem, evenSem;

    /**
     * The argument passed to semaphore's constructor is the number of 'permits' available.
     * For oddSem and evenSem, all acquire() calls will be blocked initially as they are initialized with 0.
     * For zeroSem, the first acquire() call will succeed as it is initialized with 1.
     * @param n number series
     */
    public NumberSeries(final int n) {
        this.n = n;
        this.zeroSem = new Semaphore(1); // Only give permit to print zero initially
        this.oddSem = new Semaphore(0);
        this.evenSem = new Semaphore(0);
    }

    public void printZero() {
        for (int i = 0; i < n; i++) {
            try {
                zeroSem.acquire();
            } catch (InterruptedException ex) {

            }

            System.out.print("0");

            // If i is even, release odd semaphore permit else even semaphore permit
            (i % 2 == 0 ? oddSem : evenSem).release();
        }
    }

    public void printOdd() {
        for (int i = 1; i <= n; i += 2) {
            try {
                oddSem.acquire();
            } catch (InterruptedException ex) {

            }

            System.out.print(i);
            zeroSem.release();
        }
    }

    public void printEven() {
        for (int i = 2; i <= n; i += 2) {
            try {
                evenSem.acquire();
            } catch (InterruptedException ex) {

            }

            System.out.print(i);
            zeroSem.release();
        }
    }
}
