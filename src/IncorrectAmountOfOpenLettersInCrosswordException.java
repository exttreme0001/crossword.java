public class IncorrectAmountOfOpenLettersInCrosswordException extends Exception {
    private final int incorrectAmountOfOpenLetters;

    public IncorrectAmountOfOpenLettersInCrosswordException(int incorrectAmountOfOpenLetters) {
        this.incorrectAmountOfOpenLetters = incorrectAmountOfOpenLetters;
    }

    public int getIncorrectAmountOfOpenLetters() {
        return incorrectAmountOfOpenLetters;
    }
}
