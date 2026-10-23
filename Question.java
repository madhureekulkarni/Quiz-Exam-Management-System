public class Question {
    private int id;
    private String text;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctOption;

    public Question(int id, String text, String optionA, String optionB,
                    String optionC, String optionD, String correctOption) {
        this.id = id;
        this.text = text;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
    }

    public Question(String text, String optionA, String optionB,
                    String optionC, String optionD, String correctOption) {
        this(0, text, optionA, optionB, optionC, optionD, correctOption);
    }

    public int getId() { return id; }
    public String getText() { return text; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }
    public String getCorrectOption() { return correctOption; }

    public void display(int number) {
        System.out.println();
        System.out.println("Q" + number + ". " + text);
        System.out.println("A. " + optionA);
        System.out.println("B. " + optionB);
        System.out.println("C. " + optionC);
        System.out.println("D. " + optionD);
    }
}
