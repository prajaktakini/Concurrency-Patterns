package com.example.concurrency.multithreading.patterns.numberseries;

import java.util.concurrent.Semaphore;

public class NumberSeries {

    private int n;
    private Semaphore zeroSem, oddSem, evenSem;

    public NumberSeries(final int n) {
        this.n = n;
        this.zeroSem = new Semaphore(1);
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

            // If i is even release oddSem else evenSem
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
