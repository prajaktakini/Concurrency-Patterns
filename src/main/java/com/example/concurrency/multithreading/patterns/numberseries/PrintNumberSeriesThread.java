package com.example.concurrency.multithreading.patterns.numberseries;

public class PrintNumberSeriesThread extends Thread {

    private NumberSeries numberSeries;
    private String method;

    public PrintNumberSeriesThread(NumberSeries numberSeries, String method) {
        this.numberSeries = numberSeries;
        this.method = method;
    }

    public void run() {
        if ("zero".equals(method)) {
            numberSeries.printZero();
        } else if ("odd".equals(method)) {
            numberSeries.printOdd();
        } else if ("even".equals(method)) {
            numberSeries.printEven();
        }
    }


}
