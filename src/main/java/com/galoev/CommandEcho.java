package com.galoev;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Command that displays its arguments.
 */
public class CommandEcho implements Command {
    @Override
    public InputStream execute(List<String> args, InputStream input) throws Exception {
        return new ByteArrayInputStream((String.join(" ", args) + "\n").getBytes());
    }
}
