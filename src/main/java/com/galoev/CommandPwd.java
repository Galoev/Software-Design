package com.galoev;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

/**
 * Command to print the current directory
 */
public class CommandPwd implements Command {
    @Override
    public InputStream execute(List<String> args, InputStream input) {
        return new ByteArrayInputStream((Paths.get(".").toAbsolutePath().normalize().toString() + "\n").getBytes());
    }
}
