package com.galoev;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that stores environment variables
 */
public class Environment {
    private Map<String, String> varValues = new HashMap<>();

    /**
     * Sets the environment variable by the passed variable and its value
     *
     * @param var variable name
     * @param val variable value
     */
    public void putVal(String var, String val) {
        varValues.put(var, val);
    }

    /**
     * Returns the value of an environment variable by its name
     *
     * @param var variable name
     * @return variable value
     */
    public String getVal(String var) {
        return varValues.getOrDefault(var, "");
    }
}
