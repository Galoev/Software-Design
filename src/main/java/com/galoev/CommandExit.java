package com.galoev;

import java.io.InputStream;
import java.util.List;

/**
 * Command to exit the interpreter.
 */
public class CommandExit implements Command {
  @Override
  public InputStream execute(Environment environment, List<String> args, InputStream input) throws Exception {
    System.exit(0);
    return null;
  }
}
