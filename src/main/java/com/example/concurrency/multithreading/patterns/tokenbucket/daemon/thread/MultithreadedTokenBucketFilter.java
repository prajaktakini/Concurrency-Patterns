package com.example.concurrency.multithreading.patterns.tokenbucket.daemon.thread;

public class MultithreadedTokenBucketFilter extends TokenBucketFilter {
    private int MAX_TOKENS;

    private long remainingTokens = 0;

    private final int ONE_SECOND = 1000; // 1 sec is 1000 ms

    public MultithreadedTokenBucketFilter(int maxTokens) {
        this.MAX_TOKENS = maxTokens;
    }

    public void initialiseBackgroundThread() {
        // Never start a thread in a constructor
        Thread daemonThread = new Thread(() -> {
            initialiseDaemonThread();
        });
        daemonThread.setDaemon(true);
        daemonThread.start();
    }

    private void initialiseDaemonThread() {
        while (true) {
            synchronized (this) {
                // If current tokens count is less than max, add new token
                if (remainingTokens < MAX_TOKENS) {
                    remainingTokens++;
                }
                this.notify(); // Notify waiting threads that new token is available
            }
            // Make thread sleep for 1sec
            try {
                Thread.sleep(ONE_SECOND);
            } catch (InterruptedException ex) {
                // Received exception
            }
        }
    }

    public void getToken() throws InterruptedException {
        synchronized (this) {
            // if no tokens left, wait until they are available again
            if (remainingTokens == 0) {
                this.wait();
            }

            // else consume token
            remainingTokens--;
        }

        System.out.println("Thread " + Thread.currentThread().getName() + " was granted the token at sec " + (System.currentTimeMillis() / 1000));
    }
}
