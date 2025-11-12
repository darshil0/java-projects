import java.util.Scanner;

/**
 * A simple command-line calculator that performs basic arithmetic operations.
 */
public class Calculator {

    /**
     * The main method that runs the calculator.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Create a Scanner object to read input from the user.
        Scanner scanner = new Scanner(System.in);

        // Print a welcome message.
        System.out.println("Simple Command-Line Calculator");
        System.out.println("----------------------------");

        try {
            // Prompt the user to enter the first number.
            System.out.print("Enter the first number: ");
            double num1 = scanner.nextDouble();

            // Prompt the user to enter an operator.
            System.out.print("Enter an operator (+, -, *, /): ");
            char operator = scanner.next().charAt(0);

            // Prompt the user to enter the second number.
            System.out.print("Enter the second number: ");
            double num2 = scanner.nextDouble();

            // Variable to store the result of the calculation.
            double result;

            // Use a switch statement to perform the calculation based on the operator.
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    // Check for division by zero.
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        System.out.println("Error: Division by zero is not allowed.");
                        return; // Exit the program
                    }
                    break;
                default:
                    // Handle invalid operator input.
                    System.out.println("Error: Invalid operator. Please use +, -, *, or /.");
                    return; // Exit the program
            }

            // Print the result of the calculation.
            System.out.println("The result is: " + num1 + " " + operator + " " + num2 + " = " + result);

        } catch (Exception e) {
            // Handle any other exceptions, such as non-numeric input.
            System.out.println("Error: Invalid input. Please enter valid numbers.");
        } finally {
            // Close the scanner to prevent resource leaks.
            scanner.close();
        }
    }
}
