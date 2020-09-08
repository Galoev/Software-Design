package com.galoev;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for parsing the passed string into tokens
 */
public class Lexer {
    private final List<Character> splitSymbols = Arrays.asList(' ', '\'', '\"', '|');

    /**
     * Parses an input string into a list of tokens
     *
     * @param commandLine input string
     * @return list of tokens
     * @throws Exception throws an exception if cannot find the second quote
     */
    public List<Token> parse(String commandLine) throws Exception {
        int len = commandLine.length();
        List<Token> tokens = new ArrayList<>();
        String curWord = "";
        boolean isAssign = false;
        boolean isSubstitution = false;
        for (int i = 0; i < len;) {
            char symbol = commandLine.charAt(i);
            if (splitSymbols.contains(symbol)) {
                if (curWord.length() > 0) {
                    if (isAssign) {
                        tokens.add(new Token(Token.Type.ASSIGNMENT, curWord));
                    } else if (isSubstitution) {
                        tokens.add(new Token(Token.Type.SUBSTITUTION, curWord));
                    } else {
                        tokens.add(new Token(Token.Type.WORDS, curWord));
                    }
                    curWord = "";
                }
                isAssign = false;
                isSubstitution = false;
            }
            if (symbol == ' ') {
                i++;
                continue;
            }
            if (symbol == '|') {
                tokens.add(new Token(Token.Type.PIPE, String.valueOf(symbol)));
                i++;
                continue;
            }
            if (symbol == '\'' || symbol == '"') {
                int quotesLen = 0;
                boolean foundSecondQuote = false;
                for (int j = i + 1; j < len; j++) {
                    if (commandLine.charAt(j) == symbol) {
                        quotesLen = j;
                        foundSecondQuote = true;
                        break;
                    }
                }
                if (!foundSecondQuote) {
                    throw new Exception("Second quote not found");
                }
                if (symbol == '\'') {
                    tokens.add(new Token(Token.Type.SINGLE_QUOTE, commandLine.substring(i, quotesLen + 1)));
                } else {
                    tokens.add(new Token(Token.Type.DOUBLE_QUOTE, commandLine.substring(i, quotesLen + 1)));
                }
                i = quotesLen + 1;
                continue;
            }
            if (symbol == '$') {
                isSubstitution = true;
            }
            if (symbol == '=') {
                isAssign = true;
            }
            curWord += symbol;
            i++;
        }
        if (curWord.length() > 0) {
            if (isAssign) {
                tokens.add(new Token(Token.Type.ASSIGNMENT, curWord));
            } else if (isSubstitution) {
                tokens.add(new Token(Token.Type.SUBSTITUTION, curWord));
            } else {
                tokens.add(new Token(Token.Type.WORDS, curWord));
            }
        }
        return tokens;
    }
}
