// Cynthia Hong
// 03/02/2023
// CSE 143 AH with Sophie Lin Robertson
// Take-home Assessment #7
//
public class QuestionNode {
    public String question;
    public QuestionNode left;
    public QuestionNode right;

    //
    public QuestionNode(String question) {
        this(question, null, null);
    }

    //
    public QuestionNode(String question, QuestionNode left, QuestionNode right) {
        this.question = question;
        this.left = left;
        this.right = right;
    }
}
