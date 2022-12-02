package com.example.concurrency.multithreading.patterns.barbershop;

import java.util.HashSet;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        runTest();
    }

    public static void runTest() throws InterruptedException {
        HashSet<Thread> threadSet = new HashSet<>();
        final BarberShopProblem barberShopProblem = new BarberShopProblem();

        Thread barber = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    barberShopProblem.barber();
                } catch (InterruptedException ex) {
                    System.out.println("Error occurred while barber started acting upon " + ex);
                }
            }
        });

        barber.start();

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        barberShopProblem.customerWalksIn();
                    } catch (InterruptedException ex) {
                        System.out.println("Exception occurred while customer was walking in the shop " + ex);
                    }
                }
            });
            threadSet.add(t);
        }

        for (Thread t : threadSet) {
            t.start();
        }

        for (Thread t : threadSet) {
            t.join();
        }

        threadSet.clear();
        Thread.sleep(300);

        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        barberShopProblem.customerWalksIn();
                    } catch (InterruptedException ex) {
                        System.out.println("Exception occurred while customer was walking in the shop " + ex);
                    }
                }
            });
            threadSet.add(t);
        }

        for (Thread t : threadSet) {
            t.start();
            Thread.sleep(5);
        }

        barber.join();
    }
}
