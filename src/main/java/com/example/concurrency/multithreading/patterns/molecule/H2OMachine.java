package com.example.concurrency.multithreading.patterns.molecule;

import java.util.Arrays;
import java.util.Collections;

public class H2OMachine {

    private Object sync; // sync for synchronization
    private int count; // count to store the current index of the molecule array.
    String[] molecule; // molecule which is a string array with a capacity of 3 elements (atoms)

    public H2OMachine() {
        this.sync = new Object();
        this.count = 0;
        this.molecule = new String[3];
    }

    public void createHydrogenAtom() {
        synchronized (sync) {
            // If 2 hydrogen atoms are already exists
            while (Collections.frequency(Arrays.asList(molecule), "H") == 2) {
                try {
                    sync.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            molecule[count] = "H";
            count++;

            // If molecule is already formed, then exit
            if (count == 3) {
                for (String element : molecule) {
                    System.out.print(element);
                }
                Arrays.fill(molecule, null);
                count = 0;
            }
            sync.notifyAll();
        }
    }

    public void createOxygenAtom() {
        synchronized (sync) {
            // If 1 oxygen atom already exists
            while (Collections.frequency(Arrays.asList(molecule), "O") == 1) {
                try {
                    sync.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            molecule[count] = "O";
            count++;

            // If molecule is already formed, then exit
            if (count == 3) {
                for (String element : molecule) {
                    System.out.print(element);
                }
                Arrays.fill(molecule, null);
                count = 0;
            }
            sync.notifyAll();
        }
    }
}
