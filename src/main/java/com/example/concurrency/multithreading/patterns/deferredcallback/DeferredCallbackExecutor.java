package com.example.concurrency.multithreading.patterns.deferredcallback;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DeferredCallbackExecutor {

    PriorityQueue<CallBack> pq = new PriorityQueue<>(new Comparator<CallBack>() {
        @Override
        public int compare(final CallBack o1,
                           final CallBack o2) {
            return (int) (o1.executeAt - o2.executeAt);
        }
    });

    ReentrantLock lock = new ReentrantLock();
    Condition newCallbackArrived = lock.newCondition();

    private long findSleepDuration() {
        long currentTime = System.currentTimeMillis();
        return pq.peek().executeAt - currentTime;
    }

    /**
     * Background executor thread runs initiates this method which does following
     * 1. If priority queue is empty, thread waits until signalled by consumer
     * 2. If priority queue is not empty, it calculates the shortest period for which it can sleep and sleeps until that
     * period or until signalled by consumer thread
     * 3. If executor thread wakes up due to signalling by consumer thread, it re-calculates the period for which it can sleep
     * and awaits for that duration
     * 4. If executor thread wakes up as the callback is due, it executes the callback
     * 5. While loops are used to avoid spurious wakeups
     * @throws InterruptedException
     */
    public void start() throws InterruptedException {
        long sleepFor = 0;

        while (true) {
            lock.lock();

            while (pq.size() == 0) {
                newCallbackArrived.await();
            }

            while (pq.size() != 0) {
                sleepFor = findSleepDuration();

                // If callback is due, break while loop and execute callback
                if (sleepFor <= 0) {
                    break;
                }

                newCallbackArrived.await(sleepFor, TimeUnit.MILLISECONDS);
            }

            CallBack callBack = pq.poll();
            System.out.println("Callback executed at " + System.currentTimeMillis()/1000 + " required at " + callBack.executeAt/1000 + " :message " + callBack.message);
            lock.unlock();
        }
    }

    public void registerCallback(CallBack callBack) {
        lock.lock();
        pq.add(callBack);
        newCallbackArrived.signal();
        lock.unlock();
    }

    public static void runLateThenEarlyCallback() throws InterruptedException {
        final DeferredCallbackExecutor deferredCallbackExecutor = new DeferredCallbackExecutor();

        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    deferredCallbackExecutor.start();
                } catch (InterruptedException ex) {
                    System.out.println("Received exception while initiating background thread " + ex);
                }
            }
        });

        backgroundThread.start();

        Thread lateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                CallBack cb = new CallBack(8, "I am the first callback scheduled to be run after 8 secs");
                deferredCallbackExecutor.registerCallback(cb);
            }
        });

        lateThread.start();
        Thread.sleep(3000);

        Thread earlyThread = new Thread(new Runnable() {
            @Override
            public void run() {
                CallBack cb = new CallBack(1, "I am the second callback scheduled to be run after 1 sec");
                deferredCallbackExecutor.registerCallback(cb);
            }
        });

        earlyThread.start();

        lateThread.join();
        earlyThread.join();
    }
    private static class CallBack {
        private long executeAt;
        private String message;

        public CallBack(final long executeAfter,
                        final String message) {
            this.executeAt = System.currentTimeMillis() + executeAfter * 1000;
            this.message = message;
        }
    }
}
