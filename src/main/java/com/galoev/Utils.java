package com.galoev;

import java.nio.file.Path;

public class Utils {
  private Utils() {}

  /**
   * Adds current working directory to relative path
   * @param environment current environment
   * @param path path to be changed
   * @return If given path is relative returns new absolute path to the same place, else returns the same path
   */
  public static Path addWorkingDir(Environment environment, Path path) {
    if (!path.isAbsolute()) {
      return Path.of(environment.getWorkingDir().toString(), path.toString()).normalize();
    }

    return path;
  }
}
