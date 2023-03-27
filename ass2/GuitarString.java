// Cynthia Hong
// 01/19/2023
// CSE 143 AH with Sophie Lin Robertson
// Take-home Assessment #2
// This class of GuitarHero could be used to simulate a guitar.
// When a guitar string is plucked, the string vibrates
// and creat sound. This part of assignment is to model a
// vibrating guitar string of a given frequency.

import java.util.*;

public class GuitarString {
    // class constant used to enter the energy decay factor
    public static final double DEFAULT_CAPACITY = 0.996;
    // queue for the ring buffer
    private Queue<Double> ringBuffer;

    // pre: the desired capacity N is bigger or equal to 2
    //      (throws an IllegalArgumentException if not)
    //      the frequency is bigger than 0
    //      (throws an IllegalArgumentException if not)
    // post: take a floating-point number "frequency" as parameter
    //       constructs a guitar string of the given frequency
    //       creates a ring buffer of the desired capacity N
    //       initializes it to represent a guitar string at rest
    //       by enqueueing 0
    public GuitarString(double frequency) {
        int desiredCap = (int) (Math.round(StdAudio.SAMPLE_RATE / frequency));
        if (desiredCap < 2 || frequency <= 0) {
            throw new IllegalArgumentException();
        }
        ringBuffer = new LinkedList<>();
        for (int i = 0; i < desiredCap; i++) {
            ringBuffer.add(0.0);
        }
    }

    // pre: the elements in the array of contents of the
    //      ring buffer is bigger or equal to 2
    //      (throws an IllegalArgumentException if not)
    // post: take a floating-point number "init" as parameter
    //       constructs a guitar string
    //       initials the contents of the ring buffer to the
    //       values in the array, which is used to test
    public GuitarString(double[] init) {
        if (init.length < 2) {
            throw new IllegalArgumentException();
        }
        ringBuffer = new LinkedList<>();
        for (int i = 0; i < init.length; i++) {
            ringBuffer.add(init[i]);
        }
    }

    // post: replaces the N elements in the ring buffer
    //       with N random values between -0.5 inclusive
    //       and +0.5 exclusive
    public void pluck() {
        Random rand = new Random();
        for (int i = 0; i < ringBuffer.size(); i++) {
            ringBuffer.remove();
            ringBuffer.add(rand.nextDouble() - 0.5);
        }
    }

    // post: apply the Karplus-Strong update
    //       deletes the sample at the front of
    //       the ring buffer
    //       adds to the end of the ring buffer the
    //       average of the first two samples,
    //       multiplied by the energy decay factor
    public void tic() {
        double sample = ringBuffer.remove();
        sample += ringBuffer.peek();
        ringBuffer.add(sample / 2 * DEFAULT_CAPACITY);
    }

    // post: return the current sample
    public double sample() {
        return ringBuffer.peek();
    }
}
