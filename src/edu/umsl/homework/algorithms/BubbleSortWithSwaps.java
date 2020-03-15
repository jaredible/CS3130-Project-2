/*******************************************************************************
 * Copyright (c) 2020 Jared Diehl. All rights reserved.
 *******************************************************************************/
package edu.umsl.homework.algorithms;

import edu.umsl.homework.util.OrderType;
import edu.umsl.homework.util.SizeType;

/**
 * @filename BubbleSortWithSwaps.java
 * @author Jared Diehl
 * @date 2020-03-16
 * @course CMP SCI 3130
 * @title Project 2
 * @purpose To perform empirical analysis of the bubble sort algorithm with counting swaps.
 * @notes Part C
 */
public class BubbleSortWithSwaps extends BubbleSort {

  public BubbleSortWithSwaps(Long seed, OrderType orderType, SizeType sizeType) {
    super(seed, orderType, sizeType);
  }

  @Override
  protected void sort() {
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

  public static void main(String[] args) {
    for (SizeType sizeType : SizeType.values()) {
      for (OrderType orderType : OrderType.values()) {
        SortingAlgorithm sortingAlgorithm = new BubbleSortWithSwaps(Long.valueOf(0), orderType, sizeType);
        sortingAlgorithm.start();
      }
    }
  }

  @Override
  public String toString() {
    return String.format("CountSwaps: Yes, %s", super.toString());
  }

}
