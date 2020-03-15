package edu.umsl.homework;

import java.util.Map;
import edu.umsl.homework.algorithms.SortingAlgorithm;
import edu.umsl.homework.util.OrderType;
import edu.umsl.homework.util.SizeType;

public class Test {

  private static void printSystemInfo() {
    System.err.println(String.format("JavaVersion: %s", System.getProperty("java.version")));
    System.err.println(String.format("OSArchitecture: %s", System.getProperty("os.arch")));
    System.err.println(String.format("OSName: %s", System.getProperty("os.name")));
    System.err.println(String.format("OSVersion: %s", System.getProperty("os.version")));
    System.err.println(String.format("AvailableProcessors: %d", Runtime.getRuntime().availableProcessors()));
    System.err.println(String.format("FreeMemory: %d", Runtime.getRuntime().freeMemory()));
    System.err.println(String.format("MaxMemory: %d", Runtime.getRuntime().maxMemory()));
    System.err.println(String.format("TotalMemory: %d", Runtime.getRuntime().totalMemory()));
  }

  // VM arguments: -Xss8m
  public static void main(String[] args) {
    printSystemInfo();

    int testCount = 5;
    int testCountPerAlgorithm = 10;

    for (int i = 0; i < testCount; i++) {
      for (SizeType sizeType : SizeType.values()) {
        for (OrderType orderType : OrderType.values()) {
          for (Map.Entry<String, Class<? extends SortingAlgorithm>> sortingAlgorithmMapping : SortingAlgorithm.getSortingAlgorithms().entrySet()) {
            TestInfo testInfo = new TestInfo(testCountPerAlgorithm, sortingAlgorithmMapping.getValue(), orderType, sizeType);
            Tester.test(testInfo);
          }
        }
      }
    }
  }

}
