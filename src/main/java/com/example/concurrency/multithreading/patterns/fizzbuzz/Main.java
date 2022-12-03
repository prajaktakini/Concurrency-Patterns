package com.example.concurrency.multithreading.patterns.fizzbuzz;

/**
 * FizzBuzz is a common interview problem in which a program prints a number series from 1 to n
 * such that for every number that is a multiple of 3 it prints "fizz", for every number that
 * is a multiple of 5 it prints "buzz" and for every number that is a multiple of both 3 and 5
 * it prints "fizzbuzz". We will be creating a multi-threaded solution for this problem.
 *
 * Suppose we have four threads t1, t2, t3 and t4. Thread t1 checks if the number is divisible by 3
 * and prints fizz. Thread t2 checks if the number is divisible by 5 and prints buzz.
 * Thread t3 checks if the number is divisible by both 3 and 5 and prints fizzbuzz.
 */
public class Main {
    public static void main(String[] args) {

        final MultithreadedFizzBuzz multithreadedFizzBuzz = new MultithreadedFizzBuzz(20);

        MultithreadedFizzBuzzThread t1 = new MultithreadedFizzBuzzThread(multithreadedFizzBuzz, "Fizz");
        MultithreadedFizzBuzzThread t2 = new MultithreadedFizzBuzzThread(multithreadedFizzBuzz, "Buzz");
        MultithreadedFizzBuzzThread t3 = new MultithreadedFizzBuzzThread(multithreadedFizzBuzz, "FizzBuzz");
        MultithreadedFizzBuzzThread t4 = new MultithreadedFizzBuzzThread(multithreadedFizzBuzz, "Number");

        t2.start();
        t1.start();
        t4.start();
        t3.start();
    }
}
