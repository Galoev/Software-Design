package com.galoev;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that stores environment variables.
 */
public class Environment {
  private Map<String, String> varValues = new HashMap<>();
  private Path workingDir = Paths.get("").toAbsolutePath().normalize();

  public Path getWorkingDir() {
    return workingDir;
  }

  public boolean setWorkingDir(Path workingDir) {
    if (workingDir.toFile().isDirectory()) {
      this.workingDir = workingDir.toAbsolutePath().normalize();

      return true;
    }

    return false;
  }

  /**
   * Sets the environment variable by the passed variable and its value.
   *
   * @param var variable name
   * @param val variable value
   */
  public void putVal(String var, String val) {
    varValues.put(var, val);
  }

  /**
   * Returns the value of an environment variable by its name.
   *
   * @param var variable name
   * @return variable value
   */
  public String getVal(String var) {
    return varValues.getOrDefault(var, "");
  }
}
