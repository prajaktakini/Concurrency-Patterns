package com.example.concurrency.multithreading.patterns.token.bucket.daemon.thread;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Set<Thread> threadSet = new HashSet<>();

        TokenBucketFilter tokenBucketFilter = TokenBucketFilterFactory.makeTokenBucketFilter(1);

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        tokenBucketFilter.getToken();
                    } catch (InterruptedException ex) {
                        System.out.println("Exception occurred while getting token " + ex);
                    }
                }
            });

            t.setName("Thread_" + (i + 1));
            threadSet.add(t);
        }

        // Start all threads at once
        for (Thread t : threadSet) {
            t.start();
        }

        // Wait until all threads are processed
        for (Thread t : threadSet) {
            t.join();
        }
    }
}
