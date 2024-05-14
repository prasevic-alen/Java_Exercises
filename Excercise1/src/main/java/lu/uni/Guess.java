package lu.uni;

public class Guess {
    private int digit;
    private String cssClass;

    // Constructor
    public Guess(int digit, String cssClass) {
        this.digit = digit;
        this.cssClass = cssClass;
    }

    // Getters and setters
    public int getDigit() {
        return digit;
    }

    public void setDigit(int digit) {
        this.digit = digit;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
}