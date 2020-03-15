package edu.umsl.homework;

import edu.umsl.homework.algorithms.SortingAlgorithm;
import edu.umsl.homework.util.OrderType;
import edu.umsl.homework.util.SizeType;

public class TestInfo {

  private final int testCount;
  private final Class<? extends SortingAlgorithm> sortingAlgorithmClass;
  private final OrderType orderType;
  private final SizeType sizeType;

  public TestInfo(int testCount, Class<? extends SortingAlgorithm> sortingAlgorithmClass, OrderType orderType, SizeType sizeType) {
    this.testCount = testCount;
    this.sortingAlgorithmClass = sortingAlgorithmClass;
    this.orderType = orderType;
    this.sizeType = sizeType;
  }

  public int getTestCount() {
    return testCount;
  }

  public Class<? extends SortingAlgorithm> getSortingAlgorithmClass() {
    return sortingAlgorithmClass;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public SizeType getSizeType() {
    return sizeType;
  }

}
