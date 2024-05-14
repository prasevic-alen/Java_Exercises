package lu.uni;

public class GameNumber {
    private String number;
    private DigitStatus[] digitStatuses;

    public GameNumber(String number) {
        this.number = number;
        digitStatuses = new DigitStatus[6];
        // Initialize all statuses to default (e.g., RED)
        for (int i = 0; i < 6; i++) {
            digitStatuses[i] = DigitStatus.RED; 
        }
    }

    public String getNumber() {
        return number;
    }

    public DigitStatus[] getDigitStatuses() {
        return digitStatuses;
    }

    public void setDigitStatus(int index, DigitStatus status) {
        this.digitStatuses[index] = status;
    }
}