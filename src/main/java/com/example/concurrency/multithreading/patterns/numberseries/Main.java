package com.example.concurrency.multithreading.patterns.numberseries;

/**
 * Suppose we are given a number n based on which a program creates the series 010203...0n. There are three threads t1, t2 and t3 which print a specific type of number from the series.
 * t1 only prints zeros, t2 prints odd numbers and t3 prints even numbers from the series.
 */
public class Main {
    public static void main(String[] args) {
        final NumberSeries numberSeries = new NumberSeries(10);

        PrintNumberSeriesThread t1 = new PrintNumberSeriesThread(numberSeries, "zero");
        PrintNumberSeriesThread t2 = new PrintNumberSeriesThread(numberSeries, "odd");
        PrintNumberSeriesThread t3 = new PrintNumberSeriesThread(numberSeries, "even");

        t3.start();
        t2.start();
        t1.start();
    }
}
