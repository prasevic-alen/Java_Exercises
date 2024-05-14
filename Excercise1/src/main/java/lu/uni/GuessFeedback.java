package lu.uni;

public class GuessFeedback {
    private int digit;
    private String cssClass;

    public GuessFeedback(int digit, String cssClass) {
        this.digit = digit;
        this.cssClass = cssClass;
    }

    public int getDigit() {
        return digit;
    }

    public String getCssClass() {
        return cssClass;
    }
}