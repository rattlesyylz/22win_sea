// Cynthia Hong
// 02/23/2023
// CSE 143 AH with Sophie Lin Robertson
// Take-home Assessment #6
// The AnagramSolver uses a dictionary to find all combinations
// of words, which have the same letters as a given
// phrase.
import java.util.*;

public class AnagramSolver {
    // a dictionary of available words
    private List<String> list;
    // a sorted map of words and letters occurrences
    private Map<String, LetterInventory> collection;

    // posts: takes List<String> list as parameter,
    //        which is a nonempty collection of nonempty
    //        sequences of letters, contains no duplicates,
    //        and doesn't change in state as the
    //        program executes
    //        constructs an anagram solver,
    //        which uses the given list as its dictionary
    public AnagramSolver(List<String> list) {
        this.list = list;
        collection = new HashMap<>();
        for (String word: list) {
            collection.put(word, new LetterInventory(word));
        }
    }

    // pre: max is equal or greater than zero
    //       (throws an IllegalArgumentException if not)
    // post: takes String s and int max as parameter
    //       uses recursive backtracking to find
    //       combinations of words that have the same
    //       letters as the given string
    //       prints all combinations of words from the
    //       dictionary that are anagrams of s and that
    //       include at most max words
    public void print(String s, int max) {
        if (max < 0) {
            throw new IllegalArgumentException();
        }
        LetterInventory newString = new LetterInventory(s);
        List<String> dictionary = new ArrayList<>();
        for (String word: list) {
            if (newString.subtract(collection.get(word)) != null) {
                dictionary.add(word);
            }
        }
        Stack<String> result = new Stack<>();
        find(newString, dictionary, result, max);
    }

    // post: takes LetterInventory newString,
    //       List<String> dictionary, Stack<String>
    //       result, int max as parameter
    //       help to use recursive backtracking
    //       to find combinations of words
    //       that have the same
    //       letters as the given string
    //       prints all combinations of words from the
    //       dictionary that are anagrams of s and that
    //       include at most max words
    private void find(LetterInventory newString, List<String> dictionary,
                      Stack<String> result, int max) {
        if (newString.isEmpty()) {
            System.out.println(result);
        } else if (max == 0 || result.size() <  max) {
            for (String word: dictionary) {
                LetterInventory rest = newString.subtract(collection.get(word));
                if (rest != null) {
                    result.push(word);
                    find(rest, dictionary, result, max);
                    result.pop();
                }
            }
        }
    }
}