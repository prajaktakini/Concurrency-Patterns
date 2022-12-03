package com.example.concurrency.multithreading.patterns.mergesort;

public class SingleThreadedMergeSort {

    private static int[] temp = new int[10];

    public static void main(String[] args) {
        int[] input = new int[] {7, 6, 5, 4, 3, 2, 1, 0};
        printArray(input, "Before Merge Sort");
        mergeSort(0, input.length - 1, input);
        printArray(input, "After merge sort");
    }

    private static void mergeSort(int start, int end, int[] arr) {
        if (start == end)
            return;

        int mid = start + (end - start) / 2;

        // Sort first half
        mergeSort(start, mid, arr);

        // Sort second half
        mergeSort(mid + 1, end, arr);

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

    private static void printArray(int[] arr, String msg) {
        System.out.println();
        System.out.println("msg ");
        for (int element : arr) {
            System.out.print(" " + element + " ");
        }
        System.out.println();
    }
}
