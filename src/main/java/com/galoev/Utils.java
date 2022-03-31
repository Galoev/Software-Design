package com.galoev;

import java.nio.file.Path;

public class Utils {
  private Utils() {}

  public static Path addWorkingDir(Environment environment, Path path) {
    if (!path.isAbsolute()) {
      return Path.of(environment.getWorkingDir().toString(), path.toString()).normalize();
    }

    return path;
  }
}
