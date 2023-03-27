// Cynthia Hong
// 03/09/2023
// CSE 143 AH with Sophie Lin Robertson
// Take-home Assessment #8
// HuffmanTree helps to compress the text files
// by using a coding scheme based on the frequency of characters

import java.util.*;
import java.io.*;
public class HuffmanTree {
    // builds the root of the tree
    private HuffmanNode root;

    // post: takes int[] count as parameter,
    //       where the numbers of occurrences
    //       of the character
    //       constructs the initial Huffman
    //       tree using the given array of frequencies
    public HuffmanTree(int[] count) {
        Queue<HuffmanNode> tree = new PriorityQueue<HuffmanNode>();
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0) {
                tree.add(new HuffmanNode(i, count[i]));
            }
        }
        tree.add(new HuffmanNode(count.length, 1));
        while (tree.size() > 1) {
            HuffmanNode zero = tree.remove();
            HuffmanNode one = tree.remove();
            tree.add(new HuffmanNode(-1,
                    zero.frequency + one.frequency, zero, one));
        }
        root = tree.remove();
    }

    // post: takes PrintStream output as parameter
    //       writes the tree to the given output stream
    //       in standard format
    public void write(PrintStream output) {
        helpWrite(output, root, "");
    }

    // post: takes PrintStream output
    //       HuffmanNode node, String content as parameter
    //       help to write the tree to the given output stream
    //       in standard format
    private void helpWrite(PrintStream output, HuffmanNode node,
                           String content) {
        if (node.left == null && node.right == null) {
            output.println(node.chara);
            output.println(content);
        } else {
            helpWrite(output, node.left, content + "0");
            helpWrite(output, node.right, content + "1");
        }
    }

    // post: takes Scanner input as parameter,
    //       which contains a tree
    //       stored in standard format,
    //       reconstructs the tree from a file
    public HuffmanTree(Scanner input) {
        while (input.hasNextLine()) {
            int number = Integer.parseInt(input.nextLine());
            String content = input.nextLine();
            root = helpConstructor(number, content, root);
        }
    }

    // post: takes int chara, String content, HuffmanNode node
    //       as parameters
    //       help to reconstruct the tree from a file
    private HuffmanNode helpConstructor(int number, String content,
                                        HuffmanNode node) {
        if (node == null) {
            node = new HuffmanNode(-1, -1);
        }
        if (content.length() == 0) {
            node = new HuffmanNode(number, -1);
        } else {
            char index = content.charAt(0);
            String code = content.substring(1);
            if (index == '0') {
                node.left = helpConstructor(number, code, node.left);
            } else {
                node.right = helpConstructor(number, code, node.right);
            }
        }
        return node;
    }

    // post: takes BitInputStream input, PrintStream
    //       output, int eof as parameters
    //       reads individual bits from the input stream
    //       writes the corresponding characters to
    //       the output
    //       stop reading when it encounters a
    //       character with value equal to the eof parameter
    //       assumes the input stream contains a legal
    //       encoding of characters
    public void decode(BitInputStream input, PrintStream output, int eof) {
        HuffmanNode curr = root;
        while (curr.chara != eof) {
            if (curr.left == null && curr.right == null) {
                output.write(curr.chara);
                curr = root;
            } else {
                int position = input.readBit();
                if (position == 0) {
                    curr = curr.left;
                } else {
                    curr = curr.right;
                }
            }
        }
    }
}
