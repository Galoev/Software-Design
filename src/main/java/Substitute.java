import java.util.ArrayList;
import java.util.List;

/**
 * Class that does substitutions
 */
public class Substitute {
    /**
     * Makes substitutions in the token list
     *
     * @param tokens list of tokens
     * @param environment stores environment variables
     * @return list of tokens where all substitutions are made
     */
    public List<Token> substitution(List<Token> tokens, Environment environment) {
        List<Token> resTokens = new ArrayList<>();
        for (Token token: tokens) {
            if (token.getType() == Token.Type.SINGLE_QUOTE) {
                String val = token.getValue();
                resTokens.add(new Token(Token.Type.WORDS, val.substring(1, val.length() - 1)));
                continue;
            }
            if (token.getType() == Token.Type.DOUBLE_QUOTE) {
                String val = token.getValue();
                val = val.substring(1, val.length() - 1);
                resTokens.add(new Token(Token.Type.WORDS, substitution(val, environment)));
                continue;
            }
            if (token.getType() == Token.Type.SUBSTITUTION) {
                resTokens.add(new Token(Token.Type.WORDS, substitution(token.getValue(), environment)));
                continue;
            }
            resTokens.add(token);
        }
        return resTokens;
    }

    private String substitution(String value, Environment environment) {
        int len = value.length();
        String res = "";
        for (int i = 0; i < len;) {
            if (value.charAt(i) != '$') {
                res += value.charAt(i);
                i++;
                continue;
            }
            int j = i + 1;
            String tmp = "";
            while (j < len && Character.isLetterOrDigit(value.charAt(j))) {
                tmp += value.charAt(j);
                j++;
            }
            if (tmp.length() > 0) {
                res += environment.getVal(tmp);
            } else {
                res += value.charAt(i);
            }
            i = j;
        }
        return res;
    }
}
