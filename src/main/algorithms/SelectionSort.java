/*******************************************************************************
 * Copyright (c) 2020 Jared Diehl. All rights reserved.
 *******************************************************************************/
package main.algorithms;

import main.util.OrderType;
import main.util.SizeType;

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

  public SelectionSort(Long seed, OrderType orderType, SizeType sizeType) {
    super(seed, orderType, sizeType);
  }

  @Override
  protected void sort() {
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

  public static void main(String[] args) {
    for (SizeType sizeType : SizeType.values()) {
      for (OrderType orderType : OrderType.values()) {
        SortingAlgorithm sortingAlgorithm = new SelectionSort(Long.valueOf(0), orderType, sizeType);
        sortingAlgorithm.start();
      }
    }
  }

}
