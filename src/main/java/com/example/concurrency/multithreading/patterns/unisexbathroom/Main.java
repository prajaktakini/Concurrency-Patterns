package com.example.concurrency.multithreading.patterns.unisexbathroom;

/**
 * A bathroom is being designed for the use of both males and females in an office
 * but requires the following constraints to be maintained:
 *
 * There cannot be men and women in the bathroom at the same time.
 * There should never be more than three employees in the bathroom simultaneously.
 * The solution should avoid deadlocks. For now, though, don’t worry about starvation.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        final UnisexBathroom unisexBathroom = new UnisexBathroom();

        Thread female1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    unisexBathroom.useBathroomByFemale("Susan");
                } catch (InterruptedException ex) {
                    System.out.println("Exception occurred while acquiring bathroom access by female");
                }
            }
        });

        Thread male1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    unisexBathroom.useBathroomByMale("Andrej");
                } catch (InterruptedException ex) {
                    System.out.println("Exception occurred while acquiring bathroom access by male");
                }
            }
        });

        Thread male2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    unisexBathroom.useBathroomByMale("John");
                } catch (InterruptedException ex) {
                    System.out.println("Exception occurred while acquiring bathroom access by male");
                }
            }
        });

        Thread male3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    unisexBathroom.useBathroomByMale("Bob");
                } catch (InterruptedException ex) {
                    System.out.println("Exception occurred while acquiring bathroom access by male");
                }
            }
        });

        Thread male4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    unisexBathroom.useBathroomByMale("Alex");
                } catch (InterruptedException ex) {
                    System.out.println("Exception occurred while acquiring bathroom access by male");
                }
            }
        });


        female1.start();
        male1.start();
        male2.start();
        male3.start();
        male4.start();

        female1.join();
        male1.join();
        male2.join();
        male3.join();
        male4.join();
    }
}
