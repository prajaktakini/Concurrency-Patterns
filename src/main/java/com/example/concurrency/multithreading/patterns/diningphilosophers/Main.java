package com.example.concurrency.multithreading.patterns.diningphilosophers;

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
