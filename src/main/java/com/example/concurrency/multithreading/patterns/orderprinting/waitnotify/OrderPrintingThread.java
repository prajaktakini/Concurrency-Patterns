package com.example.concurrency.multithreading.patterns.orderprinting.waitnotify;

public class OrderPrintingThread extends Thread {

    private OrderPrinting object;
    private String method;

    public OrderPrintingThread(OrderPrinting object, String method) {
        this.object = object;
        this.method = method;
    }

    public void run() {
        // For printing "First"
        if ("first".equals(method)) {
            object.printFirst();
        }
        // For printing "Second"
        else if ("second".equals(method)) {
            try {
                object.printSecond();
            } catch (InterruptedException ex) {

            }
        }
        // For printing "Third"
        else if ("third".equals(method)) {
            try {
                object.printThird();
            } catch (InterruptedException ex) {

            }
        }
    }
}
