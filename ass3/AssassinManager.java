// Cynthia Hong
// 01/26/2023
// CSE 143 AH with Sophie Lin Robertson
// Take-home Assessment #3
// The class AssassinManager could be used to
// manage a game of assassin and record
// the information of the game. It keeps
// track of who is stalking whom and the history of
// who killed whom.

import java.util.*;

public class AssassinManager {
    // a list of people in the front of the kill ring
    private AssassinNode killRing;
    // a list people people in the front of the graveyard
    private AssassinNode frontGraveyard;

    // pre: the List<String> names is not empty
    //      (throws an IllegalArgumentException if not)
    // post: takes List<String> names as parameter
    //       to give the name of people in this game
    //       constructs an assassin manager object
    //       adds the names from the list into the kill
    //       ring in the same order
    //       ignores the case of the name and no duplicate names
    public AssassinManager(List<String> names) {
        if (names.isEmpty()) {
            throw new IllegalArgumentException();
        }
        killRing = new AssassinNode(names.get(0));
        AssassinNode current = killRing;
        for (int i = 1; i < names.size(); i++) {
            current.next = new AssassinNode(names.get(i));
            current = current.next;
        }
    }

    // post: prints the name of the people in the kill ring,
    //       one per line indented four spaces, with output
    //       of the form "One is stalking another one."
    //       If there is only one left in the ring, reports
    //       the person is stalking themselves
    public void printKillRing() {
        AssassinNode current = killRing;
        while (current.next != null) {
            System.out.println("    " + current.name + " is stalking "
                               + current.next.name);
            current = current.next;
        }
        System.out.println("    " + current.name + " is stalking "
                           + killRing.name);
    }

    // post: prints the names of the people in the graveyard,
    //       one per line, intend four spaces,
    //       with output of the form " One was killed
    //       by another one"
    //       produces no output if the graveyard is empty
    public void printGraveyard() {
        AssassinNode current = frontGraveyard;
        while (current != null) {
            System.out.println("    " + current.name + " was killed by "
                               + current.killer);
            current = current.next;
        }
    }

    // post: takes string name as parameter to be checked
    //       returns true if the given name is in the
    //       current kill ring
    //       return false otherwise
    public boolean killRingContains(String name) {
        return findContains(killRing, name);
    }

    // post: takes string name as parameter to be checked
    //       returns true if the given name is in the
    //       current graveyard
    //       return false otherwise
    public boolean graveyardContains(String name) {
        return findContains(frontGraveyard, name);
    }

    // post: returns true if the game is over
    //       returns false otherwise
    public boolean gameOver() {
        return (killRing.next == null);
    }

    // post: returns name of the winner of the game
    //       returns null if the game is not over
    public String winner() {
        if (gameOver()) {
            return killRing.name;
        }
        return null;
    }

    // pre: the given name is part of the current kill ring
    //      (throws an IllegalArgumentException if not)
    //      the game is not over
    //      (throws an IllegalStateException if not)
    // post: takes string name as parameter to be the next killed
    //       person
    //       records the killing of the person with the given
    //       names
    //       transfers the person from the kill ring to the
    //       graveyard without changing the kill ring order
    public void kill(String name) {
        if (!killRingContains(name)) {
            throw new IllegalArgumentException();
        } else if (gameOver()) {
            throw new IllegalStateException();
        }

        AssassinNode killing = killRing;
        while (killing.next != null
                && !killing.next.name.equalsIgnoreCase(name)) {
            killing = killing.next;
        }
        AssassinNode death = frontGraveyard;
        if (killRing.name.equalsIgnoreCase(name)) {
            death = killRing;
            killRing = killRing.next;
        } else {
            death = killing.next;
            killing.next = killing.next.next;
        }
        death.killer = killing.name;
        death.next = frontGraveyard;
        frontGraveyard = death;
    }

    // post: takes AssassinNode list and String name as parameters
    //       checks the given name whether in the given list
    //       returns true if it is
    //       return false if it is not
    private boolean findContains(AssassinNode list, String name) {
        AssassinNode current = list;
        while (current != null) {
            if (current.name.equalsIgnoreCase(name)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}
