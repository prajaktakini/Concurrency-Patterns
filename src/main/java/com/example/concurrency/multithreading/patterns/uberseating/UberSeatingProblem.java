package com.example.concurrency.multithreading.patterns.uberseating;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class UberSeatingProblem {

    private int republicans = 0;
    private int democrats = 0;

    private Semaphore demsWaiting = new Semaphore(0);
    private Semaphore repubsWaiting = new Semaphore(0);

    private final ReentrantLock lock = new ReentrantLock();
    CyclicBarrier barrier = new CyclicBarrier(4);

    public void seatDemocrat() throws InterruptedException, BrokenBarrierException {
        boolean rideLeader = false;
        lock.lock();

        democrats++;
        if (democrats == 4) {
            // All democrats can seat on this uber ride
            demsWaiting.release(3);
            democrats -= 4;
            rideLeader = true;
        }
        else if (democrats == 2 && republicans >= 2) {
            // We can take this uber ride with 2 democrats and 2 republicans
            demsWaiting.release(1);
            repubsWaiting.release(2);
            rideLeader = true;
            democrats -= 2;
            republicans -= 2;
        } else {
            // Wait until enough people are available
            lock.unlock(); // Release the lock
            demsWaiting.acquire();
        }

        allSeated();
        barrier.await();

        if (rideLeader) {
            driveCab();
            lock.unlock();
        }
    }

    public void seatRepublican() throws InterruptedException, BrokenBarrierException {
        boolean rideLeader = false;
        lock.lock();

        republicans++;

        if (republicans == 4) {
            // All republicans can seat in this ride
            repubsWaiting.release(3);
            rideLeader = true;
            republicans -= 4;
        }
        else if (republicans == 2 && democrats >= 2) {
            // We can still take the ride with 2 republicans and 2 democrats
            repubsWaiting.release(2);
            demsWaiting.release(2);
            rideLeader = true;
            republicans -= 2;
            democrats -= 2;
        } else {
            lock.unlock(); // Release the lock and wait until enough people are available
            repubsWaiting.acquire();
        }

        allSeated();
        barrier.await();

        if (rideLeader) {
            driveCab();
            lock.unlock();
        }
    }

    private void allSeated() {
        System.out.println("Thread " + Thread.currentThread().getName() + " seated");
    }

    private void driveCab() {
        System.out.println("Ready to take Uber ride with ride leader " + Thread.currentThread().getName());
    }
}
