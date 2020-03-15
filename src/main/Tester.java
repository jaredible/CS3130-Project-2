package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import main.algorithms.SortingAlgorithm;
import main.util.Constants;
import main.util.Helper;
import main.util.OrderType;
import main.util.SizeType;

public class Tester {

  public static void test(TestInfo testInfo) {
    try {
      runTests(testInfo);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void runTests(TestInfo testInfo) throws Exception {
    System.err.println(String.format("[Pre] SortingAlgorithm: %s, OrderType: %s, SizeType: %d", testInfo.getSortingAlgorithmClass().getSimpleName(), testInfo.getOrderType().getName(), testInfo.getSizeType().getSize()));
    System.err.flush();

    ExecutorService executorService = Executors.newFixedThreadPool(testInfo.getTestCount());

    List<SortingAlgorithm> sortingAlgorithms = Collections.synchronizedList(new ArrayList<SortingAlgorithm>(testInfo.getTestCount()));

    for (int i = 0; i < testInfo.getTestCount(); i++) {
      Constructor<? extends SortingAlgorithm> constructor = testInfo.getSortingAlgorithmClass().getConstructor(new Class[] {Long.class, OrderType.class, SizeType.class});
      SortingAlgorithm sortingAlgorithm = constructor.newInstance(new Object[] {Long.valueOf(i), testInfo.getOrderType(), testInfo.getSizeType()});
      sortingAlgorithms.add(sortingAlgorithm);
    }

    for (int i = 0; i < testInfo.getTestCount(); i++) {
      executorService.execute(sortingAlgorithms.get(i));
    }

    executorService.shutdown();

    boolean finished = executorService.awaitTermination(10, TimeUnit.MINUTES);
    if (finished) {
      for (int i = 0; i < testInfo.getTestCount(); i++) {
        SortingAlgorithm sortingAlgorithm = sortingAlgorithms.get(i);
        System.out.println(String.format("Index: %d, Seed: %d, Time: %.4f seconds", i, sortingAlgorithm.getSeed(), sortingAlgorithm.getSortTime()));
      }
      System.out.flush();

      Double[] sortTimes = sortingAlgorithms.stream().map(sa -> sa.getSortTime()).toArray(Double[]::new);

      TestSaver.saveCSVFile(testInfo, sortTimes);

      System.err.println(String.format("[Post] SortingAlgorithm: %s, OrderType: %s, SizeType: %d, AverageTime: %.4f seconds", testInfo.getSortingAlgorithmClass().getSimpleName(), testInfo.getOrderType().getName(), testInfo.getSizeType().getSize(), getAverageTime(sortTimes)));
      System.err.flush();
    }
  }

  private static double getAverageTime(Double[] sortTimes) {
    double result = 0;
    for (Double sortTime : sortTimes) {
      result += sortTime;
    }
    return result / sortTimes.length;
  }

  private static class TestSaver {

    public static void saveCSVFile(TestInfo testInfo, Double[] sortingTimes) {
      try {
        String name = Helper.simplify(testInfo.getSortingAlgorithmClass().getSimpleName().replace("Sort", ""));
        String orderType = Helper.simplify(testInfo.getOrderType().getName());
        String sizeType = String.valueOf(testInfo.getSizeType().getSize());

        FileWriter csvWriter = new FileWriter(getFile(sizeType, orderType, name));

        csvWriter.append("Time(seconds)\n");

        for (Double sortingTime : sortingTimes) {
          csvWriter.append(String.format("%.4f\n", sortingTime));
        }

        csvWriter.flush();
        csvWriter.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private static File getFile(String... strings) {
      String fileName = String.join("_", Arrays.asList(strings).stream().map(s -> s.toLowerCase()).collect(Collectors.toList()));

      int testNum = 1;

      File saveDirectory = new File(Constants.TEST_SAVE_DIRECTORY);
      if (!saveDirectory.exists()) {
        saveDirectory.mkdir();
      }

      while (true) {
        File saveFile = new File(saveDirectory, fileName + "_" + testNum + ".csv");

        if (!saveFile.exists()) {
          return saveFile;
        }

        testNum++;
      }
    }

  }

}
