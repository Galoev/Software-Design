package com.galoev;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;

/**
 * In this class, test cases for the class Parser.
 */
public class ParserTest {
  private static final Lexer lexer = new Lexer();
  private static final Substitute substitute = new Substitute();

  @Test
  public void testOnePipe() throws Exception {
    Environment environment = new Environment();
    Parser parser = new Parser();
    String commandLine = "echo Hello | wc";
    List<Token> tokens = lexer.parse(commandLine);
    tokens = substitute.substitution(tokens, environment);
    String result = parser.run(tokens, environment);
    assertEquals("1 1 6\n", result);
  }

  @Test
  public void testMultiPipe() throws Exception {
    Environment environment = new Environment();
    Parser parser = new Parser();

    String commandLine = "x=Hello";
    List<Token> tokens = lexer.parse(commandLine);
    tokens = substitute.substitution(tokens, environment);
    String result = parser.run(tokens, environment);
    assertEquals("", result);
    assertEquals("Hello", environment.getVal("x"));

    commandLine = "echo \"$x\" | wc | cat";
    tokens = lexer.parse(commandLine);
    tokens = substitute.substitution(tokens, environment);
    result = parser.run(tokens, environment);
    assertEquals("1 1 6\n", result);
  }

  @Test(expected = Exception.class)
  public void testException() throws Exception {
    Environment environment = new Environment();
    Parser parser = new Parser();

    String commandLine = "echo Hello | | wc";
    List<Token> tokens = null;
    tokens = lexer.parse(commandLine);
    tokens = substitute.substitution(tokens, environment);
    String result = parser.run(tokens, environment);
  }
}
