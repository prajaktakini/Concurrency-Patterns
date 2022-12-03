package com.example.concurrency.multithreading.patterns.molecule;

/**
 * Create H2O molecule
 * Suppose we have a machine that creates molecules by combining atoms.
 * We are creating water molecules by joining one Oxygen and two Hydrogen atoms.
 * The atoms are represented by threads. The machine will wait for the required atoms (threads),
 * then group one Oxygen and two Hydrogen threads to simulate the creation of a molecule.
 * The molecule then exists the machine. You have to ensure that one molecule is completed
 * before moving onto the next molecule. If more than the required number of threads arrive, they will have to wait.
 */
public class Main {

    public static void main(String[] args) {
        final H2OMachine h2OMachine = new H2OMachine();

        H2OMachineThread t1 = new H2OMachineThread(h2OMachine, "H");
        H2OMachineThread t2 = new H2OMachineThread(h2OMachine, "O");
        H2OMachineThread t3 = new H2OMachineThread(h2OMachine, "H");
        H2OMachineThread t4 = new H2OMachineThread(h2OMachine, "O");

        t2.start();
        t1.start();
        t4.start();
        t3.start();
    }
}
