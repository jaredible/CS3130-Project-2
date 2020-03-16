/*******************************************************************************
 * Copyright (c) 2020 Jared Diehl. All rights reserved.
 *******************************************************************************/
package main;

/**
 * @filename SelectionSort.java
 * @author Jared Diehl
 * @date 2020-03-16
 * @course CMP SCI 3130
 * @title Project 2
 * @purpose To perform empirical analysis of the selection sort algorithm.
 * @notes Part A
 */
public class SelectionSort extends SortingAlgorithm {

  public static void sort(int[] array) {
    int n = array.length;
    for (int i = 0; i < n - 1; i++) {
      int k = i;
      for (int j = i + 1; j < n; j++) {
        if (array[j] < array[k]) {
          k = j;
        }
      }
      swap(array, k, i);
    }
  }

}
