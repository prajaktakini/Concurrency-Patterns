package com.example.concurrency.multithreading.patterns.barbershop;

import java.util.HashSet;

/**
 * A barbershop consists of a waiting room with n chairs, and a barber chair for giving haircuts.
 * If there are no customers to be served, the barber goes to sleep.
 * If a customer enters the barbershop and all chairs are occupied, then the customer leaves the shop.
 * If the barber is busy, but chairs are available, then the customer sits in one of the free chairs.
 * If the barber is asleep, the customer wakes up the barber. Write a program to coordinate the
 * interaction between the barber and the customers.
 */
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

        // Create 10 customer threads
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

        // Bring in another set of 5 customers
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
