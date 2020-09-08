package com.galoev;

import java.io.InputStream;
import java.util.List;

/**
 * Command interface
 */
public interface Command {
    /**
     * Executes the command
     *
     * @param args command arguments
     * @param input the input stream from which the command will read
     * @return result of command execution as InputStream
     * @throws Exception errors that commands can throw
     */
    InputStream execute(List<String> args, InputStream input) throws Exception;
}
