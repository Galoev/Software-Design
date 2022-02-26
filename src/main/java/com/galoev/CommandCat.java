package com.galoev;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Command that displays the contents of a file or InputStream.
 */
public class CommandCat implements Command {
  @Override
  public InputStream execute(List<String> args, InputStream input) throws Exception {
    if (args.isEmpty()) {
      return input;
    } else {
      var result = new StringBuilder();
      for (String arg : args) {
        try {
          result.append(new String(Files.readAllBytes(Paths.get(arg))));
        } catch (NoSuchFileException e) {
          throw new Exception("File " + arg + " not found");
        }
      }
      return new ByteArrayInputStream(result.toString().getBytes());
    }
  }
}
