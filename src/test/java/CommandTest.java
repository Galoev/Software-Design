import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class CommandTest {
    
    @Test
    public void testCat1() {
        Command command = new CommandCat();
        Path path = null;
        try {
            path = Files.createTempFile("temp", ".txt");
            String text = "Hello World!";
            byte[] buf = text.getBytes();
            Files.write(path, buf);
            InputStream inputStream = command.execute(
                    Collections.singletonList(path.toAbsolutePath().toString()),
                    new ByteArrayInputStream("".getBytes())
            );
            String commandResult = IOUtils.toString(inputStream);
            assertEquals(text, commandResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCat2() {
        Command command = new CommandCat();
        try {
            String text = "Hello World!";
            InputStream inputStream = command.execute(
                    new ArrayList<>(),
                    new ByteArrayInputStream(text.getBytes())
            );
            String commandResult = IOUtils.toString(inputStream);
            assertEquals(text, commandResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCat3() {
        Command command = new CommandCat();
        Path path1 = null;
        Path path2 = null;
        Path path3 = null;
        try {
            path1 = Files.createTempFile("temp1", ".txt");
            path2 = Files.createTempFile("temp2", ".txt");
            path3 = Files.createTempFile("temp3", ".txt");
            String text1 = "Text file1";
            String text2 = "Text file2";
            String text3 = "Text file3";
            StringBuilder stringBuilder = new StringBuilder();
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
            InputStream inputStream = command.execute(
                    args,
                    new ByteArrayInputStream("".getBytes())
            );
            String commandResult = IOUtils.toString(inputStream);
            assertEquals(stringBuilder.toString(), commandResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(expected = Exception.class)
    public void testCat4() throws Exception {
        Command command = new CommandCat();
        command.execute(
                    Collections.singletonList("NoSuchFile.txt"),
                    new ByteArrayInputStream("".getBytes())
            );
    }

    @Test
    public void testEcho1() {
        Command command = new CommandEcho();
        try {
            InputStream inputStream = command.execute(
                    new ArrayList<>(),
                    new ByteArrayInputStream("".getBytes())
            );
            assertEquals("\n", IOUtils.toString(inputStream));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEcho2() {
        Command command = new CommandEcho();
        try {
            List<String> args = new ArrayList<>();
            args.add("Hello");
            args.add("World");
            args.add("!");
            InputStream inputStream = command.execute(
                    args,
                    new ByteArrayInputStream("".getBytes())
            );
            assertEquals(String.join(" ", args) + "\n", IOUtils.toString(inputStream));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWc1() {
        Command command = new CommandWc();
        Path path = null;
        try {
            path = Files.createTempFile("temp", ".txt");
            String text = "Hello World!\n Hello World!";
            byte[] buf = text.getBytes();
            Files.write(path, buf);
            InputStream inputStream = command.execute(
                    Collections.singletonList(path.toAbsolutePath().toString()),
                    new ByteArrayInputStream("".getBytes())
            );
            String commandResult = IOUtils.toString(inputStream);
            assertEquals("2 4 26 " + path.toAbsolutePath().toString() + "\n", commandResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWc2() {
        Command command = new CommandWc();
        Path path1 = null;
        Path path2 = null;
        Path path3 = null;
        try {
            path1 = Files.createTempFile("temp1", ".txt");
            path2 = Files.createTempFile("temp2", ".txt");
            path3 = Files.createTempFile("temp3", ".txt");
            String text1 = "Text file1";
            String text2 = "Text file2\nHello World!";
            String text3 = "Text file3\nHello World!\nHello World!";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("1 2 10 " + path1.toAbsolutePath().toString() + "\n");
            stringBuilder.append("2 4 23 " + path2.toAbsolutePath().toString() + "\n");
            stringBuilder.append("3 6 36 " + path3.toAbsolutePath().toString() + "\n");
            stringBuilder.append("6 12 69 total\n");
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
            InputStream inputStream = command.execute(
                    args,
                    new ByteArrayInputStream("".getBytes())
            );
            String commandResult = IOUtils.toString(inputStream);
            assertEquals(stringBuilder.toString(), commandResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(expected = Exception.class)
    public void testWc3() throws Exception {
        Command command = new CommandWc();
        command.execute(
                Collections.singletonList("NoSuchFile.txt"),
                new ByteArrayInputStream("".getBytes())
        );
    }

    @Test
    public void testExternal() throws Exception {
        Command command = new CommandExternal();
        String text = "Hello World!\nHello World!";
        InputStream inputStream = command.execute(
                Arrays.asList("echo", text),
                new ByteArrayInputStream("".getBytes())
        );
        assertEquals(text + "\n", IOUtils.toString(inputStream));
    }

    @Test
    public void testPwd() throws Exception {
        Command command = new CommandPwd();
        InputStream inputStream = command.execute(
                new ArrayList<>(),
                new ByteArrayInputStream("".getBytes())
        );
        assertEquals(System.getProperty("user.dir") + "\n", IOUtils.toString(inputStream));
    }
}
