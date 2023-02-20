package com.shinelon.deathknight.algorithm;

import org.junit.jupiter.api.Test;

public class SortTest {

    @Test
    public void bubbleSortTest() {
        int[] arr = new int[]{
                65, 98, 34, 16, 0, -5, -6, 45, 39, -44, -99, 35, 34
        };

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[i] < arr[j + 1]) {
                    int tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
    }

    @Test
    public void selectionSortTest() {
        int[] arr = new int[]{
                65, 98, 34, 16, 0, -5, -6, 45, 39, -44, -99, 35, 34
        };


        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }

            if (i != minIndex) {
                int tmp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;
            }

        }


        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }

    }

    @Test
    public void insertionSortTest() {

        int[] arr = new int[]{
                65, 98, 34, 16, 0, -5, -6, 45, 39, -44, -99, 35, 34
        };


        for (int i = 1; i < arr.length; i++) {
            int j = i;
            int target = arr[j];

            while (j > 0) {
                if (target < arr[j - 1]) {
                    break;
                }
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = target;

        }

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
    }
}
