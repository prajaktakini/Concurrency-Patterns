package com.example.concurrency.multithreading.patterns.foobar;

public class FooBar {

    private int n;
    private int flag = 0;

    public FooBar(final int n) {
        this.n = n;
    }

    public void printFoo() {
        for (int i = 1; i <= n; i++) {
            synchronized (this) {
                while (flag == 1) {
                    try {
                        this.wait();
                    } catch (InterruptedException ex) {

                    }
                }

                System.out.print("Foo");
                flag = 1;
                this.notifyAll();
            }
        }
    }

    public void printBar() {
        for (int i = 1; i <= n; i++) {
            synchronized (this) {
                while (flag == 0) {
                    try {
                        this.wait();
                    } catch (InterruptedException ex) {

                    }
                }

                System.out.println("Bar");
                flag = 0;
                this.notifyAll();
            }
        }
    }
}
