/*******************************************************************************
 * Copyright (c) 2020 Jared Diehl. All rights reserved.
 *******************************************************************************/
package edu.umsl.homework.algorithms;

import edu.umsl.homework.util.OrderType;
import edu.umsl.homework.util.SizeType;

/**
 * @filename QuickSort.java
 * @author Jared Diehl
 * @date 2020-03-16
 * @course CMP SCI 3130
 * @title Project 2
 * @purpose To perform empirical analysis of the quick sort algorithm.
 * @notes Part D
 */
public class QuickSort extends SortingAlgorithm {

  public QuickSort(Long seed, OrderType orderType, SizeType sizeType) {
    super(seed, orderType, sizeType);
  }

  @Override
  protected void sort() {
    sort(0, array.length - 1);
  }

  private void sort(int low, int high) {
    if (low < high) {
      int pi = partition(low, high);
      sort(low, pi - 1);
      sort(pi + 1, high);
    }
  }

  private int partition(int low, int high) {
    int pivot = array[high];
    int i = (low - 1);
    for (int j = low; j <= high - 1; j++) {
      if (array[j] < pivot) {
        i++;
        swap(array, i, j);
      }
    }
    swap(array, i + 1, high);
    return i + 1;
  }

  // VM arguments: -Xss8m
  public static void main(String[] args) {
    for (SizeType sizeType : SizeType.values()) {
      for (OrderType orderType : OrderType.values()) {
        SortingAlgorithm sortingAlgorithm = new QuickSort(Long.valueOf(0), orderType, sizeType);
        sortingAlgorithm.start();
      }
    }
  }

}
