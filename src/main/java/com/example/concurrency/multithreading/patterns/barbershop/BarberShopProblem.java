package com.example.concurrency.multithreading.patterns.barbershop;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class BarberShopProblem {

    private final int CHAIRS = 3; // Waiting chairs
    private Semaphore waitForCustomerToEnter = new Semaphore(0);
    private Semaphore waitForBarberToGetReady = new Semaphore(0);
    private Semaphore waitForCustomerToLeave = new Semaphore(0);
    private Semaphore waitForBarberToCutHair = new Semaphore(0);

    private int waitingCustomer = 0;
    private ReentrantLock lock = new ReentrantLock();
    private int hairCutsGiven = 0;

    public void customerWalksIn() throws InterruptedException {
        lock.lock(); // First acquire lock before updating var 'waitingCustomer'

        if (waitingCustomer == CHAIRS) {
            // No more customers can wait
            System.out.println("Customer walks out as all waiting chairs are occupied");
            lock.unlock(); // Don't forget to unlock the lock before leaving shop
            return;
        }

        waitingCustomer++;
        lock.unlock();

        waitForCustomerToEnter.release();
        waitForBarberToGetReady.acquire();

        // The chair in waiting room becomes available at this point
        lock.lock(); // Acquire lock before updating waiting customer variable
        waitingCustomer--;
        lock.unlock();

        waitForBarberToCutHair.acquire();
        waitForCustomerToLeave.release();
    }

    public void barber() throws InterruptedException {
        while (true) {
            waitForCustomerToEnter.acquire();
            waitForBarberToGetReady.release();
            hairCutsGiven++;
            System.out.println("Barber cutting hair..... No. of hair cuts given " + hairCutsGiven);
            Thread.sleep(50);
            waitForBarberToCutHair.release();
            waitForCustomerToLeave.acquire();
        }
    }
}
