package com.galoev;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * In this class, test cases for the class Lexer.
 */
public class LexerTest {
  @Test
  public void testEmptyArgs() throws Exception {
    Lexer lexer = new Lexer();
    assertEquals(new ArrayList<>(), lexer.parse(""));
  }

  @Test
  public void testPerformance1() throws Exception {
    Lexer lexer = new Lexer();
    List<Token> tokens = Arrays.asList(
            new Token(Token.Type.WORDS, "echo"),
            new Token(Token.Type.PIPE, "|"),
            new Token(Token.Type.SINGLE_QUOTE, "''"),
            new Token(Token.Type.DOUBLE_QUOTE, "\"\""),
            new Token(Token.Type.SUBSTITUTION, "$x"),
            new Token(Token.Type.ASSIGNMENT, "a=b")
    );
    List<Token> lexerTokens = lexer.parse("echo | '' \"\" $x a=b");
    assertTrue(compareListsTokens(tokens, lexerTokens));
  }

  @Test
  public void testPerformance2() throws Exception {
    Lexer lexer = new Lexer();
    List<Token> tokens = Arrays.asList(
            new Token(Token.Type.WORDS, "echo"),
            new Token(Token.Type.WORDS, "Hello"),
            new Token(Token.Type.PIPE, "|"),
            new Token(Token.Type.SINGLE_QUOTE, "'quote'"),
            new Token(Token.Type.PIPE, "|"),
            new Token(Token.Type.DOUBLE_QUOTE, "\"d_quote\""),
            new Token(Token.Type.PIPE, "|"),
            new Token(Token.Type.SUBSTITUTION, "$subst"),
            new Token(Token.Type.PIPE, "|"),
            new Token(Token.Type.ASSIGNMENT, "x=assignment"),
            new Token(Token.Type.PIPE, "|"),
            new Token(Token.Type.WORDS, "pwd"),
            new Token(Token.Type.PIPE, "|"),
            new Token(Token.Type.WORDS, "exit")
    );
    var commandLine = "echo Hello | 'quote' | \"d_quote\" | $subst | x=assignment | pwd | exit";
    List<Token> lexerTokens = lexer.parse(commandLine);
    assertTrue(compareListsTokens(tokens, lexerTokens));
  }

  private static boolean compareListsTokens(List<Token> first, List<Token> second) {
    if (first.size() != second.size()) {
      return false;
    }
    for (int i = 0; i < first.size(); i++) {
      if ((!(first.get(i).getType().equals(second.get(i).getType()))) ||
              (!(first.get(i).getValue().equals(second.get(i).getValue())))) {
        return false;
      }
    }
    return true;
  }

}
