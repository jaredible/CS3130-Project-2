/*******************************************************************************
 * Copyright (c) 2020 Jared Diehl. All rights reserved.
 *******************************************************************************/
package main;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * @filename Benchmark.java
 * @author Jared Diehl
 * @date 2020-03-16
 * @course CMP SCI 3130
 * @title Project 2
 * @purpose To benchmark sorting algorithms.
 * @notes
 */
public class Benchmark {

  /** A 1-megabyte preallocation to ensure the heap is reasonably sized. */
  public static byte[] memoryReserve = new byte[1024 * 1024 * 1];

  private static final int INTEGER_MIN = 1;
  private static final int INTEGER_MAX = 10000;
  private static final int REORDER_NTH_POSITION = 10;

  private static final Random random = new Random();

  private static long time(Consumer<int[]> sortingFunction, int[] array) {
    array = copyArray(array);
    final long start = System.nanoTime();
    sortingFunction.accept(array);
    return System.nanoTime() - start;
  }

  private static int[] copyArray(int[] array) {
    int[] arrayCopy = new int[array.length];
    System.arraycopy(array, 0, arrayCopy, 0, array.length);
    return arrayCopy;
  }

  private interface ITestDataGenerator {
    int[] generate(int n);
  }

  private static final ITestDataGenerator randomTestData = (n) -> {
    int[] array = new int[n];
    for (int i = 0; i < n; i++) {
      array[i] = random.nextInt(INTEGER_MAX - INTEGER_MIN + 1) + INTEGER_MIN;
    }
    return array;
  };

  private static final ITestDataGenerator sortedTestData = (n) -> {
    int[] array = new int[n];
    for (int i = 0; i < n; i++) {
      array[i] = i + 1;
    }
    return array;
  };

  private static final ITestDataGenerator almostSortedTestData = (n) -> {
    int[] array = new int[n];
    for (int i = 0; i < n; i++) {
      array[i] = i + 1;
    }
    for (int i = 1; i <= n; i++) {
      if (i != 0 && i % REORDER_NTH_POSITION == 0) {
        array[i - 1] = random.nextInt(INTEGER_MAX - INTEGER_MIN + 1) + INTEGER_MIN;
      }
    }
    return array;
  };

  private static long doSortTest(String arrayType, Tuple<String, Consumer<int[]>> sortingFunctionTuple, Tuple<Integer, List<int[]>> arrayTuple, int testCount) throws IOException {
    // Prepare the output file
    String filename = String.format("%s_%s_%d.csv", arrayType, String.join("_", sortingFunctionTuple.x.toLowerCase().replace(" sort", "").split(" ")), arrayTuple.x);
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
    writer.write("Time");

    // Compute the sorting functions' times
    long timeSum = 0;
    int i = 0;
    do {
      writer.write("\n");
      long time = time(sortingFunctionTuple.y, arrayTuple.y.get(i));

      // Output the current sorting functions' time
      System.out.println(String.format("%.4f", time / 1e9d));
      writer.write(String.format("%.4f", time / 1e9d));

      timeSum += time;
    } while (++i < testCount);

    writer.close();

    return timeSum;
  }

  private static void benchmark(ITestDataGenerator testDataGenerator, String arrayType, int[] arraySizes, int testCount) throws IOException {
    // Prepare the output file
    String filename = String.format("benchmark_%s_output.csv", arrayType);
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));

    // Write the column headers
    writer.write("Array Size, Selection Sort, Insertion Sort, Bubble Sort A, Bubble Sort B, Quick Sort, Merge Sort\n");

    // Prepare the sorting functions
    List<Tuple<String, Consumer<int[]>>> sortingFunctionTuples = new ArrayList<Tuple<String, Consumer<int[]>>>();
    sortingFunctionTuples.add(new Tuple<String, Consumer<int[]>>("Selection Sort", SelectionSort::sort));
    sortingFunctionTuples.add(new Tuple<String, Consumer<int[]>>("Insertion Sort", InsertionSort::sort));
    sortingFunctionTuples.add(new Tuple<String, Consumer<int[]>>("Bubble Sort A", BubbleSort::sortA));
    sortingFunctionTuples.add(new Tuple<String, Consumer<int[]>>("Bubble Sort B", BubbleSort::sortB));
    sortingFunctionTuples.add(new Tuple<String, Consumer<int[]>>("Quick Sort", QuickSort::sort));
    sortingFunctionTuples.add(new Tuple<String, Consumer<int[]>>("Merge Sort", MergeSort::sort));

    // Prepare the array sizes
    List<Tuple<Integer, List<int[]>>> arrayTuples = new ArrayList<Tuple<Integer, List<int[]>>>();
    for (Integer arraySize : arraySizes) {
      Tuple<Integer, List<int[]>> arrayTuple = new Tuple<Integer, List<int[]>>(arraySize, new ArrayList<int[]>());
      for (int j = 0; j < testCount; j++) {
        arrayTuple.y.add(testDataGenerator.generate(arraySize));
      }
      arrayTuples.add(arrayTuple);
    }

    // Iterate over all of the array sizes
    for (Tuple<Integer, List<int[]>> arrayTuple : arrayTuples) {
      // Output the current array size
      System.out.println(arrayTuple.x);
      writer.write(String.format("%d,", arrayTuple.x));

      // Iterate over all of the sorting functions
      for (Tuple<String, Consumer<int[]>> sortingFunctionTuple : sortingFunctionTuples) {
        System.out.println(sortingFunctionTuple.x);

        // Get the current sorting functions' average time in seconds
        long timeSum = doSortTest(arrayType, sortingFunctionTuple, arrayTuple, testCount);
        double timeAverage = timeSum / testCount;

        // Output the sorting functions' average time
        System.out.println(String.format("%.4f", timeAverage / 1e9d));
        writer.write(String.format("%.4f,", timeAverage / 1e9d));
      }

      // Output a new line
      System.out.flush();
      writer.write("\n");
    }

    writer.close();
  }

  public static void main(String[] args) throws IOException {
    int[] arraySizes = {1000, 10000, 100000};

    System.out.println("Benchmarking random test data");
    benchmark(randomTestData, "random", arraySizes, 10);

    System.out.println("Benchmarking sorted test data");
    benchmark(sortedTestData, "sorted", arraySizes, 10);

    System.out.println("Benchmarking almost-sorted test data");
    benchmark(almostSortedTestData, "almost_sorted", arraySizes, 10);
  }

  private static class Tuple<X, Y> {

    public final X x;
    public final Y y;

    public Tuple(X x, Y y) {
      this.x = x;
      this.y = y;
    }

  }

}
