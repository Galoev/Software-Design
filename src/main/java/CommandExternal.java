import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Class for executing external commands
 */
public class CommandExternal implements Command {
    @Override
    public InputStream execute(List<String> args, InputStream input) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new Exception("Command not found");
        }
        return process.getInputStream();
    }
}
