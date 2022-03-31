package com.galoev;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Command that lists content of current working directory
 */
public class CommandLs implements Command {
  @Override
  public InputStream execute(Environment environment, List<String> args, InputStream input) throws Exception {
    String output;

    if (args.isEmpty()) {
      output = Arrays.stream(Objects.requireNonNull(
          environment
            .getWorkingDir()
            .toFile()
            .listFiles()
        ))
        .reduce("", (str, file) -> str + " " + file.getName(), (x, y) -> x + y)
        .strip();
    } else if (args.size() == 1) {
      final var path = Utils.addWorkingDir(environment, Path.of(args.get(0)));

      if (path.toFile().isDirectory()) {
        output = Arrays.stream(
            path
              .toFile()
              .listFiles()
          )
          .reduce("", (str, file) -> str + " " + file.getName(), (x, y) -> x + y)
          .strip();
      } else {
        output = args.get(0);
      }

    } else {
      throw new IllegalStateException("ls: wrong argument number. Usage 'ls [path]'");
    }

    return new ByteArrayInputStream(output.getBytes(StandardCharsets.UTF_8));
  }
}
