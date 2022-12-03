package com.example.concurrency.multithreading.patterns.fizzbuzz;

public class MultithreadedFizzBuzz {

    private int n;
    private int num;

    public MultithreadedFizzBuzz(final int n) {
        this.n = n;
        num = 1;
    }

    public synchronized void printFizz() throws InterruptedException {
        while (num <= n) {
            // Num is divisible by 3 but not by 5
            if (num % 3 == 0 && num % 5 != 0) {
                System.out.println("Fizz");
                num++;
                this.notifyAll();
            } else {
                // Number not divisible by 3, hence wait
                this.wait();
            }
        }
    }

    public synchronized void printBuzz() throws InterruptedException {
        while (num <= n) {
            // Num is divisible by 5 but not by 3
            if (num % 3 != 0 && num % 5 == 0) {
                System.out.println("Buzz");
                num++;
                this.notifyAll();
            } else {
                // Number not divisible by 5, hence wait
                this.wait();
            }
        }
    }

    public synchronized void printFizzBuzz() throws InterruptedException {
        while (num <= n) {
            // Num is divisible by 3 and 5 i.e. divisible by 15
            if (num % 15 == 0) {
                System.out.println("FizzBuzz");
                num++;
                this.notifyAll();
            } else {
                // Number not divisible by 15, hence wait
                this.wait();
            }
        }
    }

    public synchronized void printNumber() throws InterruptedException {
        while (num <= n) {
            // Num is not divisible by 3 and 5, print number
            if (num % 3 != 0 && num % 5 != 0) {
                System.out.println(num);
                num++;
                this.notifyAll();
            } else {
                // Number is divisible by 3 or 5, hence wait
                this.wait();
            }
        }
    }
}
