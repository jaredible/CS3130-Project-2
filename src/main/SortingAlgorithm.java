/*******************************************************************************
 * Copyright (c) 2020 Jared Diehl. All rights reserved.
 *******************************************************************************/
package main;

/**
 * @filename SortAlgorithm.java
 * @author Jared Diehl
 * @date 2020-03-16
 * @course CMP SCI 3130
 * @title Project 2
 * @purpose To perform empirical analysis of sorting algorithms.
 * @notes
 */
public class SortingAlgorithm {

  protected static void swap(int[] array, int i, int j) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

}
