package com.example.concurrency.multithreading.patterns.uberseating;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;

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
