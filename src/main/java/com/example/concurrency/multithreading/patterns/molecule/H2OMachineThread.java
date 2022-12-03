package com.example.concurrency.multithreading.patterns.molecule;

public class H2OMachineThread extends Thread {
    private H2OMachine h2OMachine;
    private String atom;

    public H2OMachineThread(H2OMachine h2OMachine, String atom) {
        this.h2OMachine = h2OMachine;
        this.atom = atom;
    }

    public void run() {
        if ("H".equals(atom)) {
            h2OMachine.createHydrogenAtom();
        } else if ("O".equals(atom)) {
            h2OMachine.createOxygenAtom();
        }
    }
}
