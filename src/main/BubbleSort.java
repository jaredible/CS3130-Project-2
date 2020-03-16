/*******************************************************************************
 * Copyright (c) 2020 Jared Diehl. All rights reserved.
 *******************************************************************************/
package main;

/**
 * @filename BubbleSort.java
 * @author Jared Diehl
 * @date 2020-03-16
 * @course CMP SCI 3130
 * @title Project 2
 * @purpose To perform empirical analysis of the bubble sort algorithm with and without counting swaps.
 * @notes Part C
 */
public class BubbleSort extends SortingAlgorithm {

  public static void sortA(int[] array) {
    int n = array.length;
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (array[j] > array[j + 1]) {
          swap(array, j, j + 1);
        }
      }
    }
  }

  public static void sortB(int[] array) {
    int n = array.length;
    for (int i = 0; i < n - 1; i++) {
      boolean swap = false;
      for (int j = 0; j < n - i - 1; j++) {
        if (array[j] > array[j + 1]) {
          swap(array, j, j + 1);
          swap = true;
        }
      }
      if (!swap) {
        break;
      }
    }
  }

}
