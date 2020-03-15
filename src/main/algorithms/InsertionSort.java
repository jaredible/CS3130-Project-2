/*******************************************************************************
 * Copyright (c) 2020 Jared Diehl. All rights reserved.
 *******************************************************************************/
package main.algorithms;

import main.util.OrderType;
import main.util.SizeType;

/**
 * @filename InsertionSort.java
 * @author Jared Diehl
 * @date 2020-03-16
 * @course CMP SCI 3130
 * @title Project 2
 * @purpose To perform empirical analysis of the insertion sort algorithm.
 * @notes Part B
 */
public class InsertionSort extends SortingAlgorithm {

  public InsertionSort(Long seed, OrderType orderType, SizeType sizeType) {
    super(seed, orderType, sizeType);
  }

  @Override
  protected void sort() {
    for (int i = 1; i < array.length; i++) {
      int key = array[i];
      int j = i - 1;
      while (j >= 0 && array[j] > key) {
        array[j + 1] = array[j];
        j--;
      }
      array[j + 1] = key;
    }
  }

  public static void main(String[] args) {
    for (SizeType sizeType : SizeType.values()) {
      for (OrderType orderType : OrderType.values()) {
        SortingAlgorithm sortingAlgorithm = new InsertionSort(Long.valueOf(0), orderType, sizeType);
        sortingAlgorithm.start();
      }
    }
  }

}
