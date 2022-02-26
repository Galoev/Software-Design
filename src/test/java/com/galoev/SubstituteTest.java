package com.galoev;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * In this class, test cases for the class Substitute.
 */
public class SubstituteTest {
  @Test
  public void testOneArg() {
    Environment environment = new Environment();
    Substitute substitute = new Substitute();
    environment.putVal("a", "ex");
    environment.putVal("b", "it");
    List<Token> substitutionRes = substitute.substitution(Arrays.asList(
            new Token(Token.Type.SUBSTITUTION, "$a$b")
    ), environment);
    List<Token> tokens = Arrays.asList(new Token(Token.Type.WORDS, "exit"));
    assertTrue(compareListsTokens(tokens, substitutionRes));
  }

  @Test
  public void testMultipleArgs() {
    Environment environment = new Environment();
    Substitute substitute = new Substitute();
    environment.putVal("x", "1");
    environment.putVal("y", "2");
    environment.putVal("var", "val");
    List<Token> substitutionRes = substitute.substitution(Arrays.asList(
            new Token(Token.Type.SUBSTITUTION, "$x"),
            new Token(Token.Type.SUBSTITUTION, "$y"),
            new Token(Token.Type.SUBSTITUTION, "$var"),
            new Token(Token.Type.SUBSTITUTION, "$x$y$var")
    ), environment);
    List<Token> tokens = Arrays.asList(
            new Token(Token.Type.WORDS, "1"),
            new Token(Token.Type.WORDS, "2"),
            new Token(Token.Type.WORDS, "val"),
            new Token(Token.Type.WORDS, "12val")
    );
    assertTrue(compareListsTokens(tokens, substitutionRes));
  }


  @Test
  public void testSingleQuote() {
    Environment environment = new Environment();
    Substitute substitute = new Substitute();
    environment.putVal("x", "1");
    environment.putVal("y", "2");
    environment.putVal("var", "val");
    List<Token> substitutionRes = substitute.substitution(Arrays.asList(
            new Token(Token.Type.SINGLE_QUOTE, "'$x'"),
            new Token(Token.Type.SINGLE_QUOTE, "'$var'"),
            new Token(Token.Type.SINGLE_QUOTE, "'$x $y Hello $var'"),
            new Token(Token.Type.SINGLE_QUOTE, "'$x$y$var'")
    ), environment);
    List<Token> tokens = Arrays.asList(
            new Token(Token.Type.WORDS, "$x"),
            new Token(Token.Type.WORDS, "$var"),
            new Token(Token.Type.WORDS, "$x $y Hello $var"),
            new Token(Token.Type.WORDS, "$x$y$var")
    );
    assertTrue(compareListsTokens(tokens, substitutionRes));
  }

  @Test
  public void testDoubleQuote() {
    Environment environment = new Environment();
    Substitute substitute = new Substitute();
    environment.putVal("x", "1");
    environment.putVal("y", "2");
    environment.putVal("var", "val");
    List<Token> substitutionRes = substitute.substitution(Arrays.asList(
            new Token(Token.Type.DOUBLE_QUOTE, "\"$x\""),
            new Token(Token.Type.DOUBLE_QUOTE, "\"$var\""),
            new Token(Token.Type.DOUBLE_QUOTE, "\"$x $y Hello $var\""),
            new Token(Token.Type.DOUBLE_QUOTE, "\"$x$y$var\"")
    ), environment);
    List<Token> tokens = Arrays.asList(
            new Token(Token.Type.WORDS, "1"),
            new Token(Token.Type.WORDS, "val"),
            new Token(Token.Type.WORDS, "1 2 Hello val"),
            new Token(Token.Type.WORDS, "12val")
    );
    assertTrue(compareListsTokens(tokens, substitutionRes));
  }

  private static final boolean compareListsTokens(List<Token> first, List<Token> second) {
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
