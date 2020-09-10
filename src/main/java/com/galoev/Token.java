package com.galoev;

/**
 * These are the objects into which the input string will be split
 */
public class Token {
    /**
     * Possible types of tokens
     */
    public enum Type {
        WORDS,
        PIPE,
        /**
         * single quoted expression
         */
        SINGLE_QUOTE,
        /**
         * expression with double quotes
         */
        DOUBLE_QUOTE,
        /**
         * variable expression to be substituted
         */
        SUBSTITUTION,
        /**
         * variable assignment
         */
        ASSIGNMENT
    }

    private final Type type;
    private final String value;

    Token(Type t, String val) {
       type = t;
       value = val;
    }

    /**
     * Returns the token type
     *
     * @return token type
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the value of the token
     *
     * @return token value
     */
    public String getValue() {
        return value;
    }
}
