package com.example.concurrency.multithreading.patterns.fizzbuzz;

public class MultithreadedFizzBuzzThread extends Thread {

    private MultithreadedFizzBuzz multithreadedFizzBuzz;
    private String method;

    public MultithreadedFizzBuzzThread(MultithreadedFizzBuzz multithreadedFizzBuzz, String method) {
        this.multithreadedFizzBuzz = multithreadedFizzBuzz;
        this.method = method;
    }

    public void run() {
        if ("Fizz".equals(method)) {
            try {
                multithreadedFizzBuzz.printFizz();
            } catch (InterruptedException ex) {

            }
        } else if ("Buzz".equals(method)) {
            try {
                multithreadedFizzBuzz.printBuzz();
            } catch (InterruptedException ex) {

            }
        } else if ("FizzBuzz".equals(method)) {
            try {
                multithreadedFizzBuzz.printFizzBuzz();
            } catch (InterruptedException ex) {

            }
        } else if ("Number".equals(method)) {
            try {
                multithreadedFizzBuzz.printNumber();
            } catch (InterruptedException ex) {

            }
        }
    }
}
