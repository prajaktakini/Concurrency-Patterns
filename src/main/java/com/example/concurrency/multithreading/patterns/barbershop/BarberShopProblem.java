package com.example.concurrency.multithreading.patterns.barbershop;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class BarberShopProblem {

    private final int CHAIRS = 3; // Waiting chairs

    // Barber is waiting for customer to enter shop. Customer signals this semaphore as soon as enters
    private Semaphore waitForCustomerToEnter = new Semaphore(0);

    // Customer thread may be waiting as barber may be asleep. Barber signals this semaphore as soon as wakes up
    private Semaphore waitForBarberToGetReady = new Semaphore(0);

    // Customer threads signals below semaphore once hair cut is done
    private Semaphore waitForCustomerToLeave = new Semaphore(0);

    // Barber thread signals below semaphore once hair cut for current customer thread is done
    private Semaphore waitForBarberToCutHair = new Semaphore(0);

    private int waitingCustomer = 0; // This variable is in critical section hence we need lock
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

        waitingCustomer++; // increment waiting chairs
        lock.unlock(); // Unlock the lock as critical section is over

        // Let the barber know you are here in case he's asleep
        waitForCustomerToEnter.release();

        // Wait for barber to come to take you to salon chair when its your turn
        waitForBarberToGetReady.acquire();

        // This is where customer gets hair cut

        // The chair in waiting room becomes available at this point once barber has taken one of the customers to barber chair
        lock.lock(); // Acquire lock before updating waiting customer variable(critical section)
        waitingCustomer--; // Because of this change, barber is able to give service to 4 customers (3 waiting chairs + 1
        // barber chair) from set of customer threads
        lock.unlock();

        // Wait for hair cut to complete
        waitForBarberToCutHair.acquire();

        // Leave the barber thread and let barber thread know chair is vacant
        waitForCustomerToLeave.release();
    }

    public void barber() throws InterruptedException {
        // This is perpetual loop
        while (true) {
            waitForCustomerToEnter.acquire(); // Barber is waiting for customer to enter
            waitForBarberToGetReady.release(); // Barber is ready to do haircut. barber gets up, greets customer and leads
            // customer to chair

            hairCutsGiven++;

            System.out.println("Barber cutting hair..... No. of hair cuts given " + hairCutsGiven);
            Thread.sleep(50); // Simulates hair cut

            // Customer thread should be informed that hair cut is done
            waitForBarberToCutHair.release();

            // Let barber know the current customer thread left the barber chair and he can bring in next customer
            waitForCustomerToLeave.acquire();
        }
    }
}
