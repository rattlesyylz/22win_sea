// Cynthia Hong
// 03/09/2023
// CSE 143 AH with Sophie Lin Robertson
// Take-home Assessment #8
// HuffmanNode builds the node of the binary tree,
// which stores the character of the HuffmanCode

public class HuffmanNode implements Comparable<HuffmanNode> {
    // the number of character
    public int chara;
    // the frequency of character
    public int frequency;
    // the left node
    public HuffmanNode left;
    // the right node
    public HuffmanNode right;

    // post: takes int chara, int frequency as
    //       parameters
    //       builds the node with the given
    //       character and frequency
    public HuffmanNode(int chara, int frequency) {
        this(chara, frequency, null, null);
    }

    // post: takes int chara, int frequency
    //       HuffmanNode left, HuffmanNode right
    //       as parameters
    //       builds the node with the given
    //       character, frequency, left, and
    //       right subtrees
    public HuffmanNode(int chara, int frequency, HuffmanNode left,
                       HuffmanNode right) {
        this.chara = chara;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    // post: takes HuffmanNode other as parameter
    //       compare two HuffmanNodes with their frequency
    //       returns the difference of the frequency,
    //       if positive, it means this frequency is larger
    //       than other's; if negative, it means this node is smaller
    //       than other; if equal, they are the same
    public int compareTo(HuffmanNode other) {
        return this.frequency - other.frequency;
    }
}
