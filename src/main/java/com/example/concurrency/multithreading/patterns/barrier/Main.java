package com.example.concurrency.multithreading.patterns.barrier;

/**
 * A barrier can be thought of as a point in the program code, which all or some of the threads need
 * to reach at before any one of them is allowed to proceed further.
 * A barrier allows multiple threads to congregate at a point in code before any one of the threads is allowed to move forward.
 * Java and most other languages provide libraries which make barrier construct available for developer use.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        runTest();
    }

    private static void runTest() throws InterruptedException {
        final Barrier barrier = new Barrier(3);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Thread 1");
                    barrier.await();
                    System.out.println("Thread 1");
                    barrier.await();
                    System.out.println("Thread 1");
                    barrier.await();
                } catch (InterruptedException ex) {

                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    System.out.println("Thread 2");
                    barrier.await();
                    Thread.sleep(500);
                    System.out.println("Thread 2");
                    barrier.await();
                    Thread.sleep(500);
                    System.out.println("Thread 2");
                    barrier.await();
                } catch (InterruptedException ex) {

                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    System.out.println("Thread 3");
                    barrier.await();
                    Thread.sleep(1500);
                    System.out.println("Thread 3");
                    barrier.await();
                    Thread.sleep(1500);
                    System.out.println("Thread 3");
                    barrier.await();
                } catch (InterruptedException ex) {

                }
            }
        });

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }
}
