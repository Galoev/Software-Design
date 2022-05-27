package com.galoev;

import java.util.List;
import java.util.Scanner;

/**
 * The main class of the program that implements the command line interface.
 */
public class CommandLineInterface {
    private final Environment environment = new Environment();
    private final Lexer lexer = new Lexer();
    private final Substitute substitute = new Substitute();
    private final Parser parser = new Parser();
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Our main method.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        var cli = new CommandLineInterface();
        while (true) {
            System.out.print(">>");
            String commandLine = cli.scanner.nextLine();
            try {
                cli.run(commandLine);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void run(String commandLine) throws Exception {
        List<Token> tokens = lexer.parse(commandLine);
        tokens = substitute.substitution(tokens, environment);
        String result = parser.run(tokens, environment);
        System.out.print(result);
    }
}
