package com.example.concurrency.multithreading.patterns.mergesort;

import java.util.Random;

/**
 * Implement multithreaded merge sort algorithm
 */
public class MultiThreadedMergeSort {
    private static int SIZE = 25;
    private static Random random = new Random(System.currentTimeMillis());
    private static int[] input = new int[SIZE];

    private static int[] temp = new int[SIZE];

    private static void createTestData() {
        for (int i = 0; i < SIZE; i++) {
            input[i] = random.nextInt(10000);
        }
    }

    public static void main(String[] args) {
        createTestData();
        System.out.println("Unsorted array");
        printArray(input);
        long startTime = System.currentTimeMillis();
        mergeSort(0, SIZE - 1, input);
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken to sort array " + (endTime - startTime) + " milliseconds");
        System.out.println("Sorted array");
        printArray(input);
    }


    private static void printArray(int[] arr) {
        System.out.println();
        for (int element : arr) {
            System.out.print(" " + element + " ");
        }
        System.out.println();
    }

    private static void mergeSort(int start, int end, int[] arr) {
        if (start == end)
            return;

        int mid = start + (end - start) / 2;

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                // Sort first half
                mergeSort(start, mid, arr);
            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                // Sort second half
                mergeSort(mid + 1, end, arr);
            }
        });

        // Start both worker threads
        t1.start();
        t2.start();

        // Wait until threads complete
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            System.out.println("Exception occurred while waiting for threads to complete " + ex);
        }

        // Temporarily copy elements
        for (int i = start; i <= end; i++) {
            temp[i] = arr[i];
        }
        // Now merge two sorted arrays
        int i = start;
        int j = mid + 1;
        int k;

        k = start; // To keep track of which element is processed
        while (k <= end) {
            if (i <= mid && j <= end) {
                arr[k] = Math.min(temp[i], temp[j]);

                if (arr[k] == temp[i]) {
                    i++;
                } else {
                    j++;
                }
            } else if (i <= mid && j > end) {
                arr[k] = temp[i];
                i++;
            } else {
                arr[k] = temp[j];
                j++;
            }
            k++;
        }
    }
}
