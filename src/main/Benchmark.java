package main;

import java.util.Random;
import java.util.function.Consumer;

public class Benchmark {

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

  private static void benchmark1(ITestDataGenerator testDataGenerator) {
    for (int i = 1000; i <= 100000; i *= 10) {
      System.out.println(i);
      int[] array = testDataGenerator.generate(i);

      long selectionSort = time(SelectionSort::sort, array);
      long insertionSort = time(InsertionSort::sort, array);
      long bubbleSortA = time(BubbleSort::sortA, array);
      long bubbleSortB = time(BubbleSort::sortB, array);
      long quickSort = time(QuickSort::sort, array);
      long mergeSort = time(MergeSort::sort, array);

      System.out.println(String.format("%.4f seconds", mergeSort / 1e9d));
    }
  }

  public static void main(String[] args) {
    benchmark1(randomTestData);
  }

}
