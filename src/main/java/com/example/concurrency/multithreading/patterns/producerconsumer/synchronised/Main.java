package com.example.concurrency.multithreading.patterns.producerconsumer.synchronised;

import com.example.concurrency.multithreading.patterns.producerconsumer.reentrantlock.BlockingQueueWithMutex;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final BlockingQueueWithSynchronised obj = new BlockingQueueWithSynchronised(5);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 50; i++) {
                        obj.enqueue(i);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 25; i++) {
                        System.out.println("Item dequeued by t2 " + obj.dequeue());
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 25; i++) {
                        System.out.println("Item dequeued by t3 " + obj.dequeue());
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        t1.start();
        Thread.sleep(4000);

        t2.start();
        t2.join();

        t3.start();
        t1.join();
        t3.join();
    }
}
