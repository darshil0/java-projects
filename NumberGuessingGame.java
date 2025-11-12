import java.util.Random;
import java.util.Scanner;

/**
 * A simple number guessing game.
 */
public class NumberGuessingGame {

    /**
     * The main method that runs the number guessing game.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Create a Random object to generate a random number.
        Random random = new Random();
        // Create a Scanner object to read input from the user.
        Scanner scanner = new Scanner(System.in);

        // Generate a random number between 1 and 100.
        int numberToGuess = random.nextInt(100) + 1;
        // Variable to store the user's guess.
        int userGuess;
        // Variable to store the number of attempts.
        int numberOfTries = 0;
        // Boolean to track if the user has guessed the number.
        boolean hasGuessedCorrectly = false;

        // Print a welcome message.
        System.out.println("--- Number Guessing Game ---");
        System.out.println("I'm thinking of a number between 1 and 100.");

        // Loop until the user guesses the correct number.
        while (!hasGuessedCorrectly) {
            // Prompt the user to enter their guess.
            System.out.print("Enter your guess: ");
            userGuess = scanner.nextInt();
            // Increment the number of tries.
            numberOfTries++;

            // Check if the user's guess is correct.
            if (userGuess == numberToGuess) {
                hasGuessedCorrectly = true;
                System.out.println("Congratulations! You guessed the number in " + numberOfTries + " tries.");
            } else if (userGuess < numberToGuess) {
                // Provide a hint if the guess is too low.
                System.out.println("Your guess is too low.");
            } else {
                // Provide a hint if the guess is too high.
                System.out.println("Your guess is too high.");
            }
        }

        // Close the scanner.
        scanner.close();
    }
}
