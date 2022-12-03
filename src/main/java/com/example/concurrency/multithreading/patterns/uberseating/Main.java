package com.example.concurrency.multithreading.patterns.uberseating;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;

/**
 * Imagine at the end of a political conference, republicans and democrats are trying to leave the venue
 * and ordering Uber rides at the same time. However, to make sure no fight breaks out in an Uber ride,
 * the software developers at Uber come up with an algorithm whereby either an Uber ride can
 * have all democrats or republicans or two Democrats and two Republicans. All other combinations can result in a fist-fight.
 *
 * Your task as the Uber developer is to model the ride requestors as threads.
 * Once an acceptable combination of riders is possible, threads are allowed to proceed to ride.
 * Each thread invokes the method seated() when selected by the system for the next ride.
 * When all the threads are seated, any one of the four threads can invoke the method drive()
 * to inform the driver to start the ride.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        runTest();
    }

    public static void runTest() throws InterruptedException {
        final UberSeatingProblem uberSeatingProblem = new UberSeatingProblem();
        Set<Thread> threadSet = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        uberSeatingProblem.seatDemocrat();
                    } catch (InterruptedException ex) {
                        System.out.println("Exception occurred while democrat was being seated " + ex);
                    } catch (BrokenBarrierException ex) {
                        System.out.println("Exception occurred while democrat was being seated " + ex);
                    }
                }
            });
            thread.setName("Democrat_" + (i + 1));
            threadSet.add(thread);

            Thread.sleep(50);
        }


        for (int i = 0; i < 14; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        uberSeatingProblem.seatRepublican();
                    } catch (InterruptedException ex) {
                        System.out.println("Exception occurred while republican was being seated " + ex);
                    } catch (BrokenBarrierException ex) {
                        System.out.println("Exception occurred while republican was being seated " + ex);
                    }
                }
            });
            thread.setName("Republican_" + (i + 1));
            threadSet.add(thread);

            Thread.sleep(20);
        }

        for (Thread t : threadSet) {
            t.start();
        }

        for (Thread t : threadSet) {
            t.join();
        }
    }
}
