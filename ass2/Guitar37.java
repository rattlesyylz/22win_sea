// Cynthia Hong
// 01/19/2023
// CSE 143 AH with Sophie Lin Robertson
// Take-home Assessment #2
// This GuitarHero could be used to simulate a guitar.
// When a guitar string is plucked, the string vibrates
// and creat sound. This part of GuitarHero is to keep
// track of a musical instrument with multiple strings

public class Guitar37 implements Guitar {
    // keyboard layout
    public static final String KEYBOARD =
            "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    // an integral to record times of "tic"
    private int ticTimes;
    //  strings an array to record guitar string
    private GuitarString[] newString;

    // post: constructs the array of strings for the guitar
    //       defines the character of the string with the
    //       corresponding frequency
    public Guitar37() {
        newString = new GuitarString[KEYBOARD.length()];
        ticTimes = 0;
        for (int i = 0; i < KEYBOARD.length(); i++) {
            newString[i] = new GuitarString(440 *
                                            Math.pow(2.0, (i - 24.0) / 12));
        }
    }

    // post: take an integer "pitch" as parameter
    //       converts from a "pitch" value to an index
    //       in the string by adding 24 to the pitch value
    //       the pitch is smaller or equal to 12
    //       and bigger or equal to -24
    //       (the pitch will be ignored if not)
    public void playNote(int pitch) {
        if (pitch <= 12 && pitch >= -24) {
            newString[pitch + 24].pluck();
        }
    }

    // post: take a character "key" as parameter
    //       returns true if the key is on the keyboard
    //       returns false if the key is not on the keyboard
    public boolean hasString(char key) {
        return KEYBOARD.indexOf(key) >= 0;
    }
    
    // pre: the key is on the keyboard
    //      (throws an IllegalArgumentException if not)
    // post: takes character key as parameter
    //       pluck the given key on the matched string
    public void pluck(char key) {
        if (!hasString(key)) {
            throw new IllegalArgumentException();
        }
        newString[KEYBOARD.indexOf(key)].pluck();
    }

    // post: constructs and return the sum of
    // the current samples
    public double sample() {
        double sum = 0.0;
        for (int i = 0; i < KEYBOARD.length(); i++) {
            sum += newString[i].sample();
        }
        return sum;
    }

    // post: advance the time forward one "tic"
    public void tic() {
        for (int i = 0; i < KEYBOARD.length(); i++) {
            newString[i].tic();
        }
        ticTimes++;
    }

    // post: determines the current time,
    //       which is the number of times tic
    //       has been called
    public int time() {
        return ticTimes;
    }
}
