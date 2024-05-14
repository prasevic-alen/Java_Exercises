package lu.uni;

import java.io.Serializable;

public class GuessFeedback implements Serializable {
    private int[] guess;
    private String feedback;

    public GuessFeedback(int[] guess, String feedback) {
        this.guess = guess;
        this.feedback = feedback;
    }

    public int[] getGuess() {
        return guess;
    }

    public String getFeedback() {
        return feedback;
    }
}

