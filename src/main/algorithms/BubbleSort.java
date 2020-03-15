/*******************************************************************************
 * Copyright (c) 2020 Jared Diehl. All rights reserved.
 *******************************************************************************/
package main.algorithms;

import main.util.OrderType;
import main.util.SizeType;

/**
 * @filename BubbleSort.java
 * @author Jared Diehl
 * @date 2020-03-16
 * @course CMP SCI 3130
 * @title Project 2
 * @purpose To perform empirical analysis of the bubble sort algorithm with and without counting swaps.
 * @notes Part C
 */
public abstract class BubbleSort extends SortingAlgorithm {

  public BubbleSort(Long seed, OrderType orderType, SizeType sizeType) {
    super(seed, orderType, sizeType);
  }

}
