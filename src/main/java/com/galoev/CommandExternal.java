package com.galoev;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Class for executing external commands.
 */
public class CommandExternal implements Command {
    @Override
    public InputStream execute(List<String> args, InputStream input) throws Exception {
        var processBuilder = new ProcessBuilder(args);
        processBuilder.redirectErrorStream(true);
        Process process;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new Exception("Command not found");
        }
        process.waitFor();
        return process.getInputStream();
    }

}
