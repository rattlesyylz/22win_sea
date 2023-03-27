// Cynthia Hong
// 03/02/2023
// CSE 143 AH with Sophie Lin Robertson
// Take-home Assessment #7
// This assignment implements a yes/no guessing game.
// QuestionTree constructs a binary tree where each
// leaf has the name of an object and each branch node has a
// yes/no question that distinguishes between the objects.
import java.util.*;
import java.io.*;

public class QuestionTree {
    private QuestionNode root;
    private Scanner console;

    // post: construct a question tree
    // with one leaf node representing the object
    // “computer”
    public QuestionTree() {
        root = new QuestionNode("computer");
        console = new Scanner(System.in);
    }

    // post: takes Scanner input as parameter,
    //       which is linked to the file
    //       replaces the current tree with a new
    //       tree by reading the line in the file,
    //       where the file is legal and in standard format
    public void read(Scanner input) {
        root = newRead(input);
    }

    // post: takes Scanner input as parameter,
    //       which is linked to the file
    //       helps to replace the current tree with a new
    //       tree by reading the line in the file,
    //       where the file is legal and in standard format
    private QuestionNode newRead(Scanner input) {
        String type = input.nextLine();
        String content = input.nextLine();
        QuestionNode node = new QuestionNode(content);
        if (type.equals("Q:")) {
            node.left = newRead(input);
            node.right = newRead(input);
        }
        return node;
    }

    // post: takes PrintStream output as parameter,
    //       which is open for writing
    //       and in the pre-order format
    //       allows users to write and store the current
    //       tree to an output file
    public void write(PrintStream output) {
        newWrite(output, root);
    }

    // post: takes PrintStream output, which is open for writing
    //       and in the pre-order format,
    //       QuestionNode current as parameters
    //       allows users to write and store the current
    //       tree to an output file
    private void newWrite(PrintStream output, QuestionNode current) {
        if (current.left ==null && current.right == null) {
            output.println("A:");
            output.println(current.question);
        } else {
            output.println("Q:");
            output.println(current.question);
            newWrite(output, current.left);
            newWrite(output, current.right);
        }
    }

    // post: uses the current tree
    //       to ask the user yes/no questions until
    //       the guess is correct or until
    //       the user fails, where the tree
    //       includes users' object and a new question to
    //       distinguish their object from the others
    public void askQuestions() {
        root = askQuestions(root);
    }

    // post: takes QuestionNode root as parameter
    //       help to use the current tree
    //       to ask the user yes/no questions until
    //       the guess is correct or until
    //       the user fails, where the tree
    //       includes users' object and a new question to
    //       distinguish their object from the others
    private QuestionNode askQuestions(QuestionNode root) {
        if (root.left == null && root.right == null) {
            if (yesTo("Would your object happen to be "
                      + root.question + "?")) {
                System.out.print("Great, I got it right!");
            } else {
                System.out.print("What is the name of your object? ");
                String newRoot = console.nextLine();
                System.out.println ("Please give me a yes/no question that");
                System.out.println ("distinguishes between your object");
                System.out.print ("and mine--> ");
                String curQuestion = console.nextLine();
                if (yesTo("And what is the answer for your object? ")) {
                    return new QuestionNode(curQuestion, new QuestionNode(newRoot), root);
                } else {
                    return new QuestionNode(curQuestion, root, new QuestionNode(newRoot));
                }
            }
        } else if (yesTo(root.question)) {
            root.left = askQuestions(root.left);
        } else {
            root.right = askQuestions(root.right);
        }
        return root;
    }
    
    // post: asks the user a question, forcing an answer of "y " or "n";
    //       returns true if the answer was yes, returns false otherwise
    public boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
}
