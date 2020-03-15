package edu.umsl.homework.util;

public enum OrderType {

  RANDOM("Random"),
  SORTED("Sorted"),
  ALMOST_SORTED("AlmostSorted"),
  SORTED_REVERSED("SortedReversed");

  private final String name;

  private OrderType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
