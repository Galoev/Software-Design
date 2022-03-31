package com.galoev;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

/**
 * Command that changes current working directory
 */
public class CommandCd implements Command {
  @Override
  public InputStream execute(Environment environment, List<String> args, InputStream input) throws Exception {
    //noinspection StatementWithEmptyBody
    if (args.isEmpty()) {
      // noop
    } else if (args.size() == 1) {
      if (!environment.setWorkingDir(Utils.addWorkingDir(environment, Path.of(args.get(0))))) {
        throw new NoSuchFileException("cd: " + args.get(0) + " Not a directory");
      }
    } else {
       throw new IllegalStateException("cd: wrong argument count. Usage 'cd [path]'");
    }

    return new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
  }
}
