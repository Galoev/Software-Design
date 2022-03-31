package com.galoev;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * In this class, test cases for the class Environment.
 */
public class EnvironmentTest {
  @Test
  public void testEmptyEnvironment() {
    Environment environment = new Environment();
    assertEquals("", environment.getVal("var"));
    assertEquals(Paths.get("").toAbsolutePath().normalize(), environment.getWorkingDir());
  }

  @Test
  public void testPerformance() throws IOException {
    Environment environment = new Environment();
    environment.putVal("var1", "val1");
    assertEquals("", environment.getVal("var"));
    assertEquals("val1", environment.getVal("var1"));
    environment.putVal("var2", "val2");
    assertEquals("val1", environment.getVal("var1"));
    assertEquals("val2", environment.getVal("var2"));

    assertTrue(environment.setWorkingDir(Paths.get("../")));
    assertEquals(Paths.get("../").toAbsolutePath().normalize(), environment.getWorkingDir());
    var prevDir = environment.getWorkingDir().toString();
    var f = Files.createTempFile("test", "tmp");
    assertFalse(environment.setWorkingDir(f));
    assertEquals(
        "Setting working directory to a file doesnt change anything",
        prevDir,
        environment.getWorkingDir().toString()
    );
  }
}
