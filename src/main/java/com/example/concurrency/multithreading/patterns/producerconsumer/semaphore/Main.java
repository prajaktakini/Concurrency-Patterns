package com.example.concurrency.multithreading.patterns.producerconsumer.semaphore;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final BlockingQueueWithSemaphore<Integer> queue = new BlockingQueueWithSemaphore<>(5);

        Thread producer1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 20; i++) {
                        queue.enqueue(Integer.valueOf(i));
                        System.out.println("enqueued " + i + " by producer1");
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Thread consumer1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("Consumer1 dequeued " + queue.dequeue());
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Thread consumer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("Consumer2 dequeued " + queue.dequeue());
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        producer1.start();
        Thread.sleep(4000);

        consumer1.start();
        consumer1.join();

        consumer2.start();
        producer1.join();
        consumer2.join();
    }
}
