package com.example.concurrency.multithreading.patterns.diningphilosophers;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * If we have 5 diners and 5 folks -> it may lead to deadlock if all diners grab their left fork
 * There are 2 alternatives to avoid deadlock
 * 1. Allow max 4 diners with 5 forks max. This way deadlock will be impossible, at 4 diners can grab 4 forks at max. Remaining
 * one fork can be grabbed by one of the diners and they can proceed with dining
 * 2. Another solution is to order fork pick up by diners. Make any of the diner to pick up left fork first instead of right
 * one.
 */
public class DiningPhilosopherProblem {

    private static Random random = new Random(System.currentTimeMillis());

    private Semaphore[] forks = new Semaphore[5]; // We have 5 forks

    // To avoid deadlock, declare only 4 philosophers
    private Semaphore maxDiners = new Semaphore(4); // We have 4 diners

    public DiningPhilosopherProblem() {
        forks[0] = new Semaphore(1);
        forks[1] = new Semaphore(1);
        forks[2] = new Semaphore(1);
        forks[3] = new Semaphore(1);
        forks[4] = new Semaphore(1);
    }

    public void lifecycleOfPhilosopher(final int id)  throws InterruptedException {
        while (true) {
            contemplate();
            eat(id);
        }
    }

    private void contemplate() throws InterruptedException {
        Thread.sleep(random.nextInt(50));
    }

    // Below method limits philosophers to 4
    private void eat(final int id) throws InterruptedException {
        maxDiners.acquire();

        forks[id].acquire();
        forks[(id + 1) % 5].acquire();

        System.out.println("Philosopher " + id + " is eating");

        forks[id].release();
        forks[(id + 1) % 5].release();

        maxDiners.release();
    }

    // Below method orders the fork pick up. It mandates 3rd philosopher to first left fork first. All others must be right
    // handled to avoid deadlock
    private void eat2(final int id) throws InterruptedException {
        // Philosopher 3 should grab left fork first
        if (id == 3) {
            acquireForkLeftHanded(id);
        } else {
            acquireForkRightHandled(id);
        }

        System.out.println("Philosopher " + id + " is eating");
        forks[id].release();
        forks[(id + 1) % 5].release();
    }

    /**
     * Picks up left fork first and then right fork
     * @param id
     * @throws InterruptedException
     */
    private void acquireForkLeftHanded(final int id) throws InterruptedException {
        forks[(id + 1) % 5].acquire();
        forks[id].acquire();
    }

    private void  acquireForkRightHandled(final int id) throws InterruptedException {
        forks[id].acquire();
        forks[(id + 1) % 5].acquire();
    }
}
