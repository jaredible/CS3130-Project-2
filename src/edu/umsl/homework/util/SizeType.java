package edu.umsl.homework.util;

public enum SizeType {

  SIZE_1000(1000),
  SIZE_10000(10000),
  SIZE_100000(100000);

  private final int size;

  private SizeType(int size) {
    this.size = size;
  }

  public int getSize() {
    return size;
  }

}
