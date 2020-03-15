/*******************************************************************************
 * Copyright (c) 2020 Jared Diehl. All rights reserved.
 *******************************************************************************/
package edu.umsl.homework.algorithms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import edu.umsl.homework.util.Constants;
import edu.umsl.homework.util.OrderType;
import edu.umsl.homework.util.SizeType;

/**
 * @filename SortAlgorithm.java
 * @author Jared Diehl
 * @date 2020-03-16
 * @course CMP SCI 3130
 * @title Project 2
 * @purpose To perform empirical analysis of sorting algorithms.
 */
@SuppressWarnings("unchecked")
public abstract class SortingAlgorithm implements Runnable {

  /** A 1-megabyte preallocation to ensure the heap is reasonably sized. */
  public static byte[] memoryReserve = new byte[1024 * 1024 * 1];

  private static final Map<String, Class<? extends SortingAlgorithm>> sortingAlgorithmMappings = new HashMap<String, Class<? extends SortingAlgorithm>>();

  private final Long seed;
  private final OrderType orderType;
  private final SizeType sizeType;

  private Thread thread;
  protected int[] array;
  private long nanoSortTime;

  public SortingAlgorithm(Long seed, OrderType orderType, SizeType sizeType) {
    this.seed = seed;
    this.orderType = orderType;
    this.sizeType = sizeType;
  }

  public final void start() {
    thread = new Thread(this, "SelectionSort");
    thread.start();
  }

  public final void stop() {
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public final void run() {
    initArray();

    if (Constants.DEBUG) {
      printFormatted("[", getName(), "] UnsortedArray: ", getArrayToString());
    }

    long nanoStartTime, nanoEndTime;

    nanoStartTime = System.nanoTime();
    sort();
    nanoEndTime = System.nanoTime();

    nanoSortTime = nanoEndTime - nanoStartTime;

    if (Constants.DEBUG) {
      printFormatted("[", getName(), "] SortedArray: ", getArrayToString());
    }

    if (Constants.DISPLAY_OUTPUT) {
      printFormatted(this.toString());
    }
  }

  private void initArray() {
    array = new int[sizeType.getSize()];

    Random r = new Random(getSeed());
    int min = Constants.INTEGER_MIN;
    int max = Constants.INTEGER_MAX;
    int d = Constants.REORDER_NTH_POSITION;
    int n = array.length;

    switch (orderType) {
      case RANDOM:
        for (int i = 0; i < n; i++) {
          array[i] = r.nextInt(max - min + 1) + min;
        }
        break;
      case SORTED:
        for (int i = 0; i < n; i++) {
          array[i] = i + 1;
        }
        break;
      case ALMOST_SORTED:
        for (int i = 0; i < n; i++) {
          array[i] = i + 1;
        }
        for (int i = 1; i <= n; i++) {
          if (i != 0 && i % d == 0) {
            array[i - 1] = r.nextInt(max - min + 1) + min;
          }
        }
        break;
      case SORTED_REVERSED:
        for (int i = 0; i < n; i++) {
          array[i] = n - i;
        }
        break;
    }
  }

  protected abstract void sort();

  protected final void swap(int[] array, int i, int j) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  protected final void printFormatted(String... strings) {
    StringBuilder sb = new StringBuilder();
    for (String string : strings) {
      sb.append(string);
    }
    System.out.println(sb);
  }

  public final double getSortTime() {
    return nanoSortTime / 1e9d;
  }

  public final String getArrayToString() {
    return Arrays.toString(array);
  }

  public final String getName() {
    return this.getClass().getSimpleName();
  }

  public final Long getSeed() {
    return seed;
  }

  public final OrderType getOrderType() {
    return orderType;
  }

  public final SizeType getSizeType() {
    return sizeType;
  }

  @Override
  public String toString() {
    return String.format("Seed: %d, Order: %s, Size: %d, Time: %.4f seconds", seed, orderType.getName(), sizeType.getSize(), getSortTime());
  }

  public static Map<String, Class<? extends SortingAlgorithm>> getSortingAlgorithms() {
    return sortingAlgorithmMappings;
  }

  static {
    Class<?>[] sortingAlgorithms = new Class<?>[] {SelectionSort.class, InsertionSort.class, BubbleSortWithSwaps.class, BubbleSortWithoutSwaps.class, QuickSort.class, MergeSort.class};
    for (Class<?> sortingAlgorithm : sortingAlgorithms) {
      sortingAlgorithmMappings.put(sortingAlgorithm.getSimpleName(), (Class<? extends SortingAlgorithm>) sortingAlgorithm);
    }
  }

}
