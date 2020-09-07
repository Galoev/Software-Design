import org.apache.commons.io.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for parsing tokens and executing commands
 */
public class Parser {
    private static final Map<String, Command> COMMANDS = new HashMap<>(Map.of(
            "cat", new CommandCat(),
            "echo", new CommandEcho(),
            "wc", new CommandWc(),
            "pwd", new CommandPwd(),
            "grep", new CommandGrep(),
            "exit", new CommandExit()
    ));

    /**
     * Executes a list of tokens
     *
     * @param tokens list of tokens to be executed
     * @param environment keeps a list of environment variables
     * @return result of token execution
     * @throws Exception errors that commands can throw
     */
    public String run(List<Token> tokens, Environment environment) throws Exception {
        List<List<Token>> splitTokens = splitByPipe(tokens);
        InputStream inputStream = System.in;
        for (List<Token> words : splitTokens) {
            if (words.isEmpty()) {
                throw new Exception("Unexpected pipe found");
            }
            Token firstToken = words.get(0);
            if (firstToken.getType().equals(Token.Type.ASSIGNMENT)) {
                String val = firstToken.getValue();
                int splitIdx = val.indexOf('=');
                environment.putVal(val.substring(0, splitIdx), val.substring(splitIdx + 1));
                inputStream = new ByteArrayInputStream("".getBytes());
                continue;
            }
            Command command;
            List<Token> arguments;
            if (COMMANDS.containsKey(firstToken.getValue())) {
                command = COMMANDS.get(firstToken.getValue());
                arguments = words.subList(1, words.size());
            } else {
                command = new CommandExternal();
                arguments = words;
            }
            inputStream = command.execute(
                    arguments.stream().map(Token::getValue).collect(Collectors.toList()),
                    inputStream);
        }
        return IOUtils.toString(inputStream);
    }

    private static List<List<Token>> splitByPipe(List<Token> lexemes) {
        List<List<Token>> res = new ArrayList<>();
        ArrayList<Token> tmp = new ArrayList<>();
        int i = 0;
        while (i < lexemes.size()) {
            if (lexemes.get(i).getType().equals(Token.Type.PIPE)) {
                res.add(tmp);
                tmp = new ArrayList<>();
            } else {
                tmp.add(lexemes.get(i));
            }
            i+=1;
        }
        if (tmp.size() > 0) {
            res.add(tmp);
        }
        return res;
    }
}