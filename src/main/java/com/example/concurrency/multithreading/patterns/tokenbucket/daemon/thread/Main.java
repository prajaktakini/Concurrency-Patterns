package com.example.concurrency.multithreading.patterns.tokenbucket.daemon.thread;

import java.util.HashSet;
import java.util.Set;

/**
 * Imagine you have a bucket that gets filled with tokens at the rate of 1 token per second.
 * The bucket can hold a maximum of N tokens. Implement a thread-safe class that
 * lets threads get a token when one is available. If no token is available, then the token-requesting threads should block.
 *
 * The class should expose an API called getToken that various threads can call to get a token
 */
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
