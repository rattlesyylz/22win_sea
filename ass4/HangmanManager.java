// Cynthia Hong
// 02/02/2023
// CSE 143 AH with Sophie Lin Robertson
// Take-home Assessment #4
// This HangmanManager could be used to keep track of
// the state of a game of hangman, where computer is
// going to delay picking a word until it is forced to,
// and there is a set of words that are currently being
// used by computer

import java.util.*;

public class HangmanManager {
    // the current set of words being considered by game
    private Set<String> allWords;
    // the current pattern of "word families"
    private String findPattern;
    // the time of left guesses number
    private int leftGuesses;
    // the character being guessed in the game
    private Set<Character> charValues;

    // pre: the length is equal or bigger than 1
    //       (throws an IllegalArgumentException if not)
    //      the max is equal or bigger than 0
    //       (throws an IllegalArgumentException if not)
    // post: takes Collection<String> dictionary, which is
    //       a dictionary of words,
    //       int length, which is the length of the word,
    //       and int max, which is the max time of wrong
    //       guesses, as parameters
    //       initializes the state of the game, where the
    //       set of words should initially contain all words
    //       from the dictionary of the given length,
    //       eliminating and duplicates
    public HangmanManager(Collection<String> dictionary, int length, int max) {
        if (length < 1 || max < 0) {
            throw new IllegalArgumentException();
        }
        allWords = new TreeSet<String>();
        findPattern = "-";
        leftGuesses = max;
        charValues = new TreeSet<Character>();
        for (String diction : dictionary) {
            if (diction.length() == length) {
                allWords.add(diction);
            }
        }
        for (int i = 0; i < length - 1; i++) {
            findPattern += " -";
        }
    }

    // post: returns the current set of words
    //       being considered by the game
    public Set<String> words() {
        return allWords;
    }

    // post: returns how many guesses the player
    //      has left.
    public int guessesLeft() {
        return leftGuesses;
    }

    // post: returns current set of letters that
    //       have been guessed by the user
    public Set<Character> guesses() {
        return charValues;
    }

    // pre: the set of words is not empty
    //      (throws an IllegalStateException if not)
    // post: returns the current pattern to be displayed
    //       for the hangman game taking into account guesses
    //       that have been made
    public String pattern() {
        if (allWords.isEmpty()) {
            throw new IllegalStateException();
        }
        return findPattern;
    }

    // pre: the number of guess left is equal or
    //      bigger than 1
    //      (throws an IllegalStateException if not)
    //     the set of words is not empty
    //      (throws an IllegalStateException if not)
    //     the character being guessed was not guessed
    //     previously
    //     (throws an IllegalArgumentException if not)
    // post: records the next guess made by the user
    //       decides what set of words to use going forward
    //       returns the number of occurrences of the
    //       guessed letter in the new pattern
    //       updates the number of guesses left
    public int record(char guess) {
        if (leftGuesses < 1 || allWords.isEmpty()) {
            throw new IllegalStateException();
        } else if (charValues.contains(guess)) {
            throw new IllegalArgumentException();
        }
        charValues.add(guess);
        Map<String, Set<String>> pattern = newPattern();
        getNextPattern(pattern);
        int time = findTime(guess);
        return time;
    }

    // post: takes char guess as parameter
    //       checks whether the given guess
    //       is the letter in the word
    //       returns letter if it is in the word
    //       returns "-" if it is not in the word
    private char getChar(char guess) {
        if (charValues.contains(guess)) {
            return guess;
        } else {
            return '-';
        }
    }

    // post: takes char guess and String word
    //       as parameter
    //       Uses the character guess if it is in the word
    //       or "-" if guess is not in the word to construct
    //       the pattern of the word
    //       returns the pattern of the word
    private String findChar(String word) {
        String newPattern = "" + getChar(word.charAt(0));
        for (int i = 1; i < word.length(); i++) {
            newPattern += " " + getChar(word.charAt(i));
        }
        return newPattern;
    }

    // post: takes char guess as parameter
    //       finds which pattern the given word belongs to
    //       returns all word patterns with the words in
    //       the word patterns
    private Map<String, Set<String>> newPattern() {
        Map<String, Set<String>> pattern = new TreeMap<>();
        for (String word : allWords) {
            String getPattern = findChar(word);
            if (!pattern.containsKey(getPattern)) {
                pattern.put(getPattern, new TreeSet<>());
            }
            pattern.get(getPattern).add(word);
        }
        return pattern;
    }

    // post: takes Map<String, Set<String>> pattern as parameter
    //       updates the current pattern according to the word
    //       updates the current set of words being considered by game
    //       according to the pattern of the word
    private void getNextPattern(Map<String, Set<String>> pattern) {
        int count = 0;
        String getPattern = "";
        for (String word : pattern.keySet()) {
            int currentCount = pattern.get(word).size();
            if (currentCount > count) {
                getPattern = word;
                count = currentCount;
            }
        }
        findPattern = getPattern;
        allWords = pattern.get(getPattern);
    }

    // post: takes char guess as parameter,
    //       which is the character the player
    //       guesses
    //       returns the times of the character
    //       occur if it is the character in the word
    //       if it is not the character in the word,
    //       updates the left guesses number
    private int findTime(char guess) {
        int time = 0;
        for (int i = 0; i < findPattern.length(); i++) {
            if (findPattern.charAt(i) == guess) {
                time++;
            }
        }
        if (time == 0) {
            leftGuesses--;
        }
        return time;
    }
}