package com.example.concurrency.multithreading.patterns.producerconsumer;

public class Demonstration {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueueWithMutex<Integer> queue = new BlockingQueueWithMutex<Integer>(5);

        Thread producer1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 1;
                    while (true) {
                        queue.enqueue(i);
                        System.out.println("Producer thread1 enqueue item " + i);
                        i++;
                    }
                } catch (InterruptedException ex) {

                }
            }
        });

        Thread producer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 5000;
                    while (true) {
                        queue.enqueue(i);
                        System.out.println("Producer thread2 enqueue item " + i);
                        i++;
                    }
                } catch (InterruptedException ex) {

                }
            }
        });

        Thread producer3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 100000;
                    while (true) {
                        queue.enqueue(i);
                        System.out.println("Producer thread3 enqueue item " + i);
                        i++;
                    }
                } catch (InterruptedException ex) {

                }
            }
        });

        Thread consumer1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Integer element = queue.dequeue();
                    System.out.println("Consumer thread1 dequeued " + element);
                }

            }
        });

        Thread consumer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Integer element = queue.dequeue();
                    System.out.println("Consumer thread2 dequeued " + element);
                }

            }
        });

        Thread consumer3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Integer element = queue.dequeue();
                    System.out.println("Consumer thread3 dequeued " + element);
                }

            }
        });

        producer1.setDaemon(true);
        producer2.setDaemon(true);
        producer3.setDaemon(true);
        consumer1.setDaemon(true);
        consumer2.setDaemon(true);
        consumer3.setDaemon(true);

        producer1.start();
        producer2.start();
        producer3.start();

        consumer1.start();
        consumer2.start();
        consumer3.start();

        Thread.sleep(1000);

    }
}
