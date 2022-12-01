package com.example.concurrency.multithreading.patterns.semaphore;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final CountingSemaphore countingSemaphore = new CountingSemaphore(1);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 5; i++) {
                        countingSemaphore.acquire();
                        System.out.println("Acquired semaphore " + i + " by the thread1");
                    }

                } catch (InterruptedException ex) {
                    System.out.println("Exception occurred while acquiring a semaphore");
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 5; i++) {
                        countingSemaphore.release();
                        System.out.println("Released semaphore " + i + " by the thread2");
                    }

                } catch (InterruptedException ex) {
                    System.out.println("Exception occurred while releasing a semaphore");
                }
            }
        });

        t2.start();
        t1.start();

        t1.join();
        t2.join();
    }







}
