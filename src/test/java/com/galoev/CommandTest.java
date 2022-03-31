package com.galoev;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * In this class, test cases for commands.
 */
public class CommandTest {
  private final Environment environment = new Environment();

  @Test
  public void testCat1() throws Exception {
    Command command = new CommandCat();
    Path path = null;
    path = Files.createTempFile("temp", ".txt");
    String text = "Hello World!";
    byte[] buf = text.getBytes();
    Files.write(path, buf);
    InputStream inputStream = command.execute(
            environment,
            Collections.singletonList(path.toAbsolutePath().toString()),
            new ByteArrayInputStream("".getBytes())
    );
    String commandResult = IOUtils.toString(inputStream);
    assertEquals(text, commandResult);
  }

  @Test
  public void testCat2() throws Exception {
    Command command = new CommandCat();
    String text = "Hello World!";
    InputStream inputStream = command.execute(
            environment,
            new ArrayList<>(),
            new ByteArrayInputStream(text.getBytes())
    );
    String commandResult = IOUtils.toString(inputStream);
    assertEquals(text, commandResult);
  }

  @Test
  public void testCat3() throws Exception {
    Path path1 = null;
    Path path2 = null;
    Path path3 = null;

    path1 = Files.createTempFile("temp1", ".txt");
    path2 = Files.createTempFile("temp2", ".txt");
    path3 = Files.createTempFile("temp3", ".txt");

    String text1 = "Text file1";
    String text2 = "Text file2";
    String text3 = "Text file3";

    var stringBuilder = new StringBuilder();
    stringBuilder.append(text1);
    stringBuilder.append(text2);
    stringBuilder.append(text3);

    byte[] buf = text1.getBytes();
    Files.write(path1, buf);
    buf = text2.getBytes();
    Files.write(path2, buf);
    buf = text3.getBytes();
    Files.write(path3, buf);

    List<String> args = new ArrayList<>();
    args.add(path1.toAbsolutePath().toString());
    args.add(path2.toAbsolutePath().toString());
    args.add(path3.toAbsolutePath().toString());

    Command command = new CommandCat();
    InputStream inputStream = command.execute(
            environment,
            args,
            new ByteArrayInputStream("".getBytes())
    );
    String commandResult = IOUtils.toString(inputStream);
    assertEquals(stringBuilder.toString(), commandResult);
  }

  @Test(expected = Exception.class)
  public void testCat4() throws Exception {
    Command command = new CommandCat();
    command.execute(
            environment,
            Collections.singletonList("NoSuchFile.txt"),
            new ByteArrayInputStream("".getBytes())
    );
  }

  @Test
  public void testEcho1() throws Exception {
    Command command = new CommandEcho();
    InputStream inputStream = command.execute(
            environment,
            new ArrayList<>(),
            new ByteArrayInputStream("".getBytes())
    );
    assertEquals("\n", IOUtils.toString(inputStream));
  }

  @Test
  public void testEcho2() throws Exception {
    Command command = new CommandEcho();
    List<String> args = new ArrayList<>();
    args.add("Hello");
    args.add("World");
    args.add("!");
    InputStream inputStream = command.execute(
            environment,
            args,
            new ByteArrayInputStream("".getBytes())
    );
    assertEquals(String.join(" ", args) + "\n", IOUtils.toString(inputStream));
  }

  @Test
  public void testWc1() throws Exception {
    Command command = new CommandWc();
    Path path = Files.createTempFile("temp", ".txt");
    String text = "Hello World!\n Hello World!";
    byte[] buf = text.getBytes();
    Files.write(path, buf);
    InputStream inputStream = command.execute(
            environment,
            Collections.singletonList(path.toAbsolutePath().toString()),
            new ByteArrayInputStream("".getBytes())
    );
    String commandResult = IOUtils.toString(inputStream);
    assertEquals("2 4 26 " + path.toAbsolutePath().toString() + "\n", commandResult);
  }

  @Test
  public void testWc2() throws Exception {
    Path path1 = Files.createTempFile("temp1", ".txt");
    Path path2 = Files.createTempFile("temp2", ".txt");
    Path path3 = Files.createTempFile("temp3", ".txt");

    var stringBuilder = new StringBuilder();
    stringBuilder.append("1 2 10 " + path1.toAbsolutePath().toString() + "\n");
    stringBuilder.append("2 4 23 " + path2.toAbsolutePath().toString() + "\n");
    stringBuilder.append("3 6 36 " + path3.toAbsolutePath().toString() + "\n");
    stringBuilder.append("6 12 69 total\n");





    var text1 = "Text file1";
    byte[] buf = text1.getBytes();
    Files.write(path1, buf);

    var text2 = "Text file2\nHello World!";
    buf = text2.getBytes();
    Files.write(path2, buf);

    var text3 = "Text file3\nHello World!\nHello World!";
    buf = text3.getBytes();
    Files.write(path3, buf);

    List<String> args = new ArrayList<>();
    args.add(path1.toAbsolutePath().toString());
    args.add(path2.toAbsolutePath().toString());
    args.add(path3.toAbsolutePath().toString());

    Command command = new CommandWc();
    InputStream inputStream = command.execute(
            environment,
            args,
            new ByteArrayInputStream("".getBytes())
    );
    String commandResult = IOUtils.toString(inputStream);
    assertEquals(stringBuilder.toString(), commandResult);
  }

  @Test(expected = Exception.class)
  public void testWc3() throws Exception {
    Command command = new CommandWc();
    command.execute(
            environment,
            Collections.singletonList("NoSuchFile.txt"),
            new ByteArrayInputStream("".getBytes())
    );
  }

  @Test
  public void testExternal() throws Exception {
    Command command = new CommandExternal();
    String text = "Hello World!" + System.lineSeparator() + "Hello World!";
    InputStream inputStream = command.execute(
            environment,
            Arrays.asList("echo", text),
            new ByteArrayInputStream("".getBytes())
    );
    assertEquals(text + "\n", IOUtils.toString(inputStream));
  }

  @Test
  public void testPwd() throws Exception {
    Command command = new CommandPwd();
    InputStream inputStream = command.execute(
            environment,
            new ArrayList<>(),
            new ByteArrayInputStream("".getBytes())
    );
    assertEquals(System.getProperty("user.dir") + "\n", IOUtils.toString(inputStream));
  }
}
