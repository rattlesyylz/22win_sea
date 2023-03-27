// Cynthia Hong
// 02/09/2023
// CSE 143 AH with Sophie Lin Robertson
// Take-home Assessment #5
// This GrammarSolver could be used to manipulate the
// grammar, which allow the user to randomly generate
// elements of the grammar.

import java.util.*;

public class GrammarSolver {
    // a sorted map of symbol and grammar rules
    private SortedMap<String, String[]> grammarRule;

    // pre: the grammar is not empty or is less than two
    //     entries in the grammar for the same non-terminal
    //     (throws an IllegalArgumentException if not)
    // post: takes List<String> grammar as parameter
    //       stores grammar in a convenient way, which
    //       is used for later generating parts of the
    //       grammar
    //       (the list of strings is not changed)
    public GrammarSolver(List<String> grammar) {
        if (grammar.isEmpty()) {
            throw new IllegalArgumentException();
        }
        grammarRule = new TreeMap<>();
        for (int i = 0; i < grammar.size(); i++) {
            String[] separatedSymbol = grammar.get(i).split("::=");
            if (grammarRule.containsKey(separatedSymbol[0])) {
                throw new IllegalArgumentException();
            }
            String[] newSymbol = separatedSymbol[1].split("[|]");
            grammarRule.put(separatedSymbol[0], newSymbol);
        }
    }

    // post: takes String symbol as parameter, which is
    //       the checked symbol
    //       returns true if the given symbol is a non-terminal
    //       of the grammar
    //       return false if the given symbol is not a non-terminal
    //       of the grammar
    //       matters case when comparing symbol
    public boolean grammarContains(String symbol) {
        return grammarRule.containsKey(symbol);
    }

    // pre: the grammar contain the given non-terminal symbol
    //      or the number of time is equal to or greater than
    //      zero (throws an IllegalArgumentException if not)
    // post: generates the given number of occurrences of
    //       the given symbol using the grammar
    //       return the result as an array of string
    //       applies the rule each of given non-terminal symbol
    //       with euqal probability
    //       matters case when comparing symbol
    public String[] generate(String symbol, int times) {
        if (!grammarContains(symbol) || times < 0) {
            throw new IllegalArgumentException();
        }
        String[] result = new String[times];
        for (int i = 0; i < times; i++) {
            result[i] = findSymbol(symbol);
        }
        return result;
    }

    // post: takes String symbol
    //       as parameters
    //       generate a random given symbol
    //       returns the symbol which is generated
    //       matters case when comparing symbol
    private String findSymbol(String symbol) {
        String result = "";
        if (!grammarContains(symbol)) {
            result += symbol;
        } else {
            String[] subString = grammarRule.get(symbol);
            String[] getString = subString[(int) (Math.random()
                    * subString.length)].split("[ \t]+");
            for (int i = 0; i < getString.length; i++) {
                result += findSymbol(getString[i]) + " ";
            }
        }
        return result.trim();
    }

    // post: returns a string representation of the
    //       various non-terminal symbols from the
    //       grammar as a sorted, comma-separated
    //       list enclosed in square bracket,
    //       as in "[<np>, <s>, <vp>]"
    public String getSymbols() {
        return grammarRule.keySet().toString();
    }
}
