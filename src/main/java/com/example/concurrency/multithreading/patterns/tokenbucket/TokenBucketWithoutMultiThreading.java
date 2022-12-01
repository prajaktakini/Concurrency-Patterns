package com.example.concurrency.multithreading.patterns.tokenbucket;

import java.util.HashSet;
import java.util.Set;

public class TokenBucketWithoutMultiThreading {

    public static void main(String[] args) throws InterruptedException {
        TokenBucketFilter.generateTokenEachSecond();
    }

    private static class TokenBucketFilter {
        private int MAX_TOKENS;

        private long lastRequestTime = System.currentTimeMillis();

        private long possibleTokens = 0;

        public TokenBucketFilter(int maxTokens) {
            this.MAX_TOKENS = maxTokens;
        }

        /**
         * First five threads are granted tokens immediately at the same second granularity instant. After that, the subsequent
         * threads are slowly given tokens at an interval of 1 second since one token gets generated every second.
         * @throws InterruptedException
         */
        public synchronized void getToken() throws InterruptedException {
            // As we generate 1 token per sec i.e. 1000 ms
            possibleTokens += (System.currentTimeMillis() - lastRequestTime) / 1000;

            if (possibleTokens > MAX_TOKENS) {
                possibleTokens = MAX_TOKENS;
            }

            if (possibleTokens == 0) {
                // Sleep the thread for 1s so that it can generate new token new sec
                Thread.sleep(1000);
            } else {
                // Consume the token
                possibleTokens--;
            }

            // Update last request time
            lastRequestTime = System.currentTimeMillis();
            System.out.println("Thread " + Thread.currentThread().getName() + " was granted the token at sec " + (lastRequestTime / 1000));
        }

        public static void generateTokenEachSecond() throws InterruptedException {
            Set<Thread> threadSet = new HashSet<>();

            // Initialise bucket with 5 tokens
            final TokenBucketFilter tokenBucketFilter = new TokenBucketFilter(5);

            // Don't let thread get the token for first 10 secs
            Thread.sleep(10000);

            for (int i = 0; i < 12; i++) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tokenBucketFilter.getToken();
                        } catch (InterruptedException ex) {
                            System.out.println("Exception occurred while getting token " + ex);
                        }
                    }
                });
                thread.setName("Thread_" + (i + 1));
                threadSet.add(thread);
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
}


