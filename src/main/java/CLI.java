import java.util.List;
import java.util.Scanner;

public class CLI {
    private static final Environment environment = new Environment();
    private static final Lexer lexer = new Lexer();
    private static final Substitute substitute = new Substitute();
    private static final Parser parser = new Parser();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.print(">>");
            String commandLine = scanner.nextLine();
            try {
                List<Token> tokens = lexer.parse(commandLine);
                tokens = substitute.substitution(tokens, environment);
                String result = parser.run(tokens, environment);
                System.out.print(result);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
