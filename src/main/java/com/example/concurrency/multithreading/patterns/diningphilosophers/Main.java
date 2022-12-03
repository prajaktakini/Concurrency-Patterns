package com.example.concurrency.multithreading.patterns.diningphilosophers;

/**
 * This is a classical synchronization problem proposed by Dijkstra.
 *
 * Imagine you have five philosopher's sitting on a round table.
 * The philosopher's do only two kinds of activities. One they contemplate, and two they eat.
 * However, they have only five forks between themselves to eat their food with.
 * Each philosopher requires both the fork to his
 * left and the fork to his right to eat his food.
 * Design a solution where each philosopher gets a chance to eat his food without causing a deadlock
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        runTest();
    }

    private static void runTest() throws InterruptedException {
        final DiningPhilosopherProblem dp = new DiningPhilosopherProblem();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                startPhilosopher(dp, 0);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                startPhilosopher(dp, 1);
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                startPhilosopher(dp, 2);
            }
        });

        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                startPhilosopher(dp, 3);
            }
        });

        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                startPhilosopher(dp, 4);
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
    }


    public static void startPhilosopher(DiningPhilosopherProblem dp, int id) {
        try {
            dp.lifecycleOfPhilosopher(id);
        } catch (InterruptedException ex) {
            System.out.println("Error occurred while starting lifecycle of philosopher " + id + " Error " + ex);
        }
    }
}
