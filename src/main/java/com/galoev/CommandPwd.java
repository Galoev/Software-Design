package com.galoev;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

/**
 * Command to print the current directory.
 */
public class CommandPwd implements Command {
  @Override
  public InputStream execute(Environment environment, List<String> args, InputStream input) {
    var path = environment.getWorkingDir() + "\n";
    return new ByteArrayInputStream(path.getBytes());
  }
}
