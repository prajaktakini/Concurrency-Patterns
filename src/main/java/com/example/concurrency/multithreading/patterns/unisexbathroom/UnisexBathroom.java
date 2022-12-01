package com.example.concurrency.multithreading.patterns.unisexbathroom;

import java.util.concurrent.Semaphore;

public class UnisexBathroom {

    private static String WOMEN = "women";
    private static String MEN = "men";
    private static String NONE = "none";

    private String useInBy = NONE;
    private int peopleInBathroom = 0;

    private Semaphore maxPeople = new Semaphore(3); // At a time 3 people of same sex can use the bathroom

    private void useBathroom(final String name) throws InterruptedException {
        System.out.println("\n" + name + " is using bathroom. No. of people in bathroom " + peopleInBathroom + " at time " + System.currentTimeMillis());
        Thread.sleep(3000);
        System.out.println("\n" + name + " is done using bathroom at " + System.currentTimeMillis());
    }

    public void useBathroomByMale (final String name) throws InterruptedException {
        synchronized (this) {
            while (useInBy.equals(WOMEN)) {
                this.wait(); // If bathroom is used in by women at the moment, wait until it becomes vacant
            }

            maxPeople.acquire();
            peopleInBathroom++;
            useInBy = MEN;
        }

        useBathroom(name);
        /**
         * Note that: threads returning from the useBathroom method, release the semaphore.
         * We must release the semaphore here because if we do not then the blocked fourth male thread would never release the
         * object's monitor and the returning threads will never be able to access the second synchronization block.
         */
        maxPeople.release();

        synchronized (this) {
            peopleInBathroom--;

            if (peopleInBathroom == 0)
                useInBy = NONE;
            this.notifyAll(); // Notify waiting women that bathroom could be vacant
        }
    }

    public void useBathroomByFemale(final String name) throws InterruptedException {
        synchronized (this) {
            while (useInBy.equals(MEN)) {
                this.wait(); // If bathroom is used in by men at the moment, wait until it becomes vacant
            }

            maxPeople.acquire();
            peopleInBathroom++;
            useInBy = WOMEN;
        }

        useBathroom(name);
        maxPeople.release();

        synchronized (this) {
            peopleInBathroom--;

            if (peopleInBathroom == 0)
                useInBy = NONE;

            this.notifyAll(); // Notify waiting men that bathroom could be vacant
        }
    }





}
