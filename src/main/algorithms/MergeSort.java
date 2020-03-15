/*******************************************************************************
 * Copyright (c) 2020 Jared Diehl. All rights reserved.
 *******************************************************************************/
package main.algorithms;

import main.util.OrderType;
import main.util.SizeType;

/**
 * @filename MergeSort.java
 * @author Jared Diehl
 * @date 2020-03-16
 * @course CMP SCI 3130
 * @title Project 2
 * @purpose To perform empirical analysis of the merge sort algorithm.
 * @notes Part E
 */
public class MergeSort extends SortingAlgorithm {

  public MergeSort(Long seed, OrderType orderType, SizeType sizeType) {
    super(seed, orderType, sizeType);
  }

  @Override
  protected void sort() {
    sort(0, array.length - 1);
  }

  private void sort(int l, int r) {
    if (l < r) {
      int m = l + (r - l) / 2;
      sort(l, m);
      sort(m + 1, r);
      merge(l, m, r);
    }
  }

  private void merge(int l, int m, int r) {
    int i, j, k;
    int n1 = m - l + 1;
    int n2 = r - m;

    int[] left = new int[n1];
    int[] right = new int[n2];

    for (i = 0; i < n1; i++) {
      left[i] = array[l + i];
    }
    for (j = 0; j < n2; j++) {
      right[j] = array[m + 1 + j];
    }

    i = j = 0;
    k = l;

    while (i < n1 && j < n2) {
      if (left[i] <= right[j]) {
        array[k] = left[i];
        i++;
      } else {
        array[k] = right[j];
        j++;
      }
    }

    while (i < n1) {
      array[k] = left[i];
      i++;
      k++;
    }

    while (j < n2) {
      array[k] = right[j];
      j++;
      k++;
    }
  }

  public static void main(String[] args) {
    for (SizeType sizeType : SizeType.values()) {
      for (OrderType orderType : OrderType.values()) {
        SortingAlgorithm sortingAlgorithm = new MergeSort(Long.valueOf(0), orderType, sizeType);
        sortingAlgorithm.start();
      }
    }
  }

}
