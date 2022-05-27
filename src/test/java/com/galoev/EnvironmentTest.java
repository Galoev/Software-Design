package com.galoev;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * In this class, test cases for the class Environment.
 */
public class EnvironmentTest {
    @Test
    public void testEmptyEnvironment() {
        Environment environment = new Environment();
        assertEquals("", environment.getVal("var"));
    }

    @Test
    public void testPerformance() {
        Environment environment = new Environment();
        environment.putVal("var1", "val1");
        assertEquals("", environment.getVal("var"));
        assertEquals("val1", environment.getVal("var1"));
        environment.putVal("var2", "val2");
        assertEquals("val1", environment.getVal("var1"));
        assertEquals("val2", environment.getVal("var2"));
    }
}
