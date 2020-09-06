import org.apache.commons.io.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Command that prints the number of lines, words and bytes in a file or InputStream
 */
public class CommandWc implements Command {
    @Override
    public InputStream execute(List<String> args, InputStream input) throws Exception {
        StringBuilder result = new StringBuilder();
        int curLinesCount = 0;
        int curWordsCount = 0;
        int curBytesCount = 0;

        int totalLinesCount = 0;
        int totalWordsCount = 0;
        int totalBytesCount = 0;

        if (args.isEmpty()) {
            String text = IOUtils.toString(input);
            curLinesCount = text.split("\n").length;
            curWordsCount = text.trim().split("\\s+").length;
            curBytesCount = text.length();
            result.append(curLinesCount + " " + curWordsCount + " " + curBytesCount + "\n");
        } else {
            for (String arg: args) {
                try {
                    String text = new String(Files.readAllBytes(Paths.get(arg)));
                    curLinesCount = text.split("\n").length;
                    curWordsCount = text.trim().split("\\s+").length;
                    curBytesCount = text.length();

                    result.append(curLinesCount + " " + curWordsCount + " " + curBytesCount + " " + arg + "\n");

                    totalLinesCount += curLinesCount;
                    totalWordsCount += curWordsCount;
                    totalBytesCount += curBytesCount;
                } catch (NoSuchFileException e) {
                    result.append("wc: " + arg + " No such file or directory");
                    throw new Exception("File " + arg + "not found");
                }
            }
            if (args.size() > 1) {
                result.append(totalLinesCount + " " + totalWordsCount + " " + totalBytesCount + " total\n");
            }
        }
        return new ByteArrayInputStream(result.toString().getBytes());
    }
}
