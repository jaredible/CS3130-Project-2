package main.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Helper {

  public static String simplify(String string) {
    return Arrays.asList(string.split("(?<=[a-z])(?=[A-Z])")).stream().map(s -> s.toLowerCase()).collect(Collectors.joining("_"));
  }

}
