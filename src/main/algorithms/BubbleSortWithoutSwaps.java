/*******************************************************************************
 * Copyright (c) 2020 Jared Diehl. All rights reserved.
 *******************************************************************************/
package main.algorithms;

import main.util.OrderType;
import main.util.SizeType;

/**
 * @filename BubbleSortWithoutSwaps.java
 * @author Jared Diehl
 * @date 2020-03-16
 * @course CMP SCI 3130
 * @title Project 2
 * @purpose To perform empirical analysis of the bubble sort algorithm without counting swaps.
 * @notes Part C
 */
public class BubbleSortWithoutSwaps extends BubbleSort {

  public BubbleSortWithoutSwaps(Long seed, OrderType orderType, SizeType sizeType) {
    super(seed, orderType, sizeType);
  }

  @Override
  protected void sort() {
    int n = array.length;
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (array[j] > array[j + 1]) {
          swap(array, j, j + 1);
        }
      }
    }
  }

  public static void main(String[] args) {
    for (SizeType sizeType : SizeType.values()) {
      for (OrderType orderType : OrderType.values()) {
        SortingAlgorithm sortingAlgorithm = new BubbleSortWithoutSwaps(Long.valueOf(0), orderType, sizeType);
        sortingAlgorithm.start();
      }
    }
  }

  @Override
  public String toString() {
    return String.format("CountSwaps: No, %s", super.toString());
  }

}
