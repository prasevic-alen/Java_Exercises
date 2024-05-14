package lu.uni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class NumberleBean implements Serializable {

    private int[] targetNumber;
    private int[] currentGuess = new int[6];
    private List<String> previousGuesses = new ArrayList<>();
    private int remainingAttempts = 6;
    private final String NUMBERS_FILE_PATH = "/numbers.txt";
    private static final Logger logger = Logger.getLogger("loginBean");

    public NumberleBean() {
        startGame();
    }

    public void startGame() {
        targetNumber = generateRandomNumber();
        currentGuess = new int[6];
        previousGuesses.clear();
        remainingAttempts = 6;
    }

    private int[] generateRandomNumber() {
        Random random = new Random();
        int lineNumber = random.nextInt(5500) + 1; // Generate a random line number between 1 and 5500
        int[] number = new int[6];
        try {
            InputStream inputStream = getClass().getResourceAsStream(NUMBERS_FILE_PATH);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            for (int i = 0; i < lineNumber - 1; i++) {
                reader.readLine(); // Skip lines until reaching the random line
            }
            
            String line = reader.readLine(); // Read the random line
            logger.log(Level.INFO, "INFO: number to guess: " + line);
            for (int i = 0; i < 6; i++) {
                number[i] = Character.getNumericValue(line.charAt(i));
            }
            reader.close(); // Close the reader
        } catch (IOException e) {
            e.printStackTrace(); // Handle file reading exception
        }
        return number;
    }

    public String makeGuess() {
        // Decrease remaining attempts
        remainingAttempts--;

        // Check if guess is correct
        StringBuilder feedback = new StringBuilder(); // Feedback string for the current guess
        int greenCount = 0; // Correct digits at the right position
        for (int i = 0; i < 6; i++) {
            if (currentGuess[i] == targetNumber[i]) {
                greenCount++;
                feedback.append("correct ");
            } else if (containsDigit(targetNumber, currentGuess[i])) {
                int targetIndex = getIndex(targetNumber, currentGuess[i]);
                if (targetIndex < i) {
                    feedback.append("misplaced-left ");
                } else {
                    feedback.append("misplaced-right ");
                }
            } else {
                feedback.append("incorrect ");
            }
        }

        // If all digits are correct
        if (greenCount == 6) {
            previousGuesses.add(formatGuess(currentGuess) + " " + feedback.toString().trim());
            return "success";
        }

        // If remaining attempts are zero
        if (remainingAttempts == 0) {
            previousGuesses.add(formatGuess(currentGuess) + " " + feedback.toString().trim());
            return "failure";
        }

        previousGuesses.add(formatGuess(currentGuess) + " " + feedback.toString().trim()); // Add the guess and feedback to previous guesses
        return null;
    }

    

    public String getFeedbackStyle(int index) {
        if (currentGuess[index] == targetNumber[index]) {
            return "correct";
        } else if (containsDigit(targetNumber, currentGuess[index])) {
            int targetIndex = getIndex(targetNumber, currentGuess[index]);
            if (targetIndex < index) {
                return "misplaced-left";
            } else {
                return "misplaced-right";
            }
        } else {
            return "incorrect";
        }
    }

    private boolean containsDigit(int[] array, int digit) {
        for (int num : array) {
            if (num == digit) {
                return true;
            }
        }
        return false;
    }

    private int getIndex(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    private String formatGuess(int[] guess) {
        StringBuilder sb = new StringBuilder();
        for (int digit : guess) {
            sb.append(digit);
        }
        return sb.toString();
    }


    // Getters and setters
    public int[] getCurrentGuess() {
        return currentGuess;
    }

    public void setCurrentGuess(int[] currentGuess) {
        this.currentGuess = currentGuess;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public List<String> getPreviousGuesses() {
        return previousGuesses;
    }

    public List<Integer> getIndices() {
        return IntStream.range(0, 6).boxed().collect(Collectors.toList());
    }
}
