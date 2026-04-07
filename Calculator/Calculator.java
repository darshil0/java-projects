import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.List;

/**
 * A comprehensive command-line calculator with advanced features including
 * memory functions, history, parentheses support, and scientific operations.
 *
 * @author Jules
 * @author Darshil
 * @version 2.0
 */
public class Calculator {

    // Constants for validation
    private static final double MAX_NUMBER = 1e308;
    private static final double MIN_NUMBER = -1e308;

    // Memory storage
    private static double memory = 0.0;

    // Calculation history
    private static List<String> history = new ArrayList<>();

    /**
     * The main method that runs the calculator.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueCalculating = true;

        printWelcome();

        while (continueCalculating) {
            try {
                System.out.print("\nEnter expression (or 'help' for commands): ");
                String input = scanner.nextLine().trim();

                // Handle special commands
                if (input.equalsIgnoreCase("help")) {
                    printHelp();
                    continue;
                } else if (input.equalsIgnoreCase("history")) {
                    printHistory();
                    continue;
                } else if (input.equalsIgnoreCase("clear history")) {
                    clearHistory();
                    continue;
                } else if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                    continueCalculating = false;
                    continue;
                } else if (input.toLowerCase().startsWith("m")) {
                    handleMemoryCommand(input);
                    continue;
                } else if (input.isEmpty()) {
                    System.out.println("⚠️ Please enter an expression.");
                    continue;
                }

                // Evaluate the expression
                double result = evaluateExpression(input);

                // Check for special values
                if (Double.isInfinite(result)) {
                    System.out.println("⚠️ Warning: Result is too large (overflow occurred).");
                } else if (Double.isNaN(result)) {
                    System.out.println("⚠️ Warning: Result is undefined (NaN).");
                } else {
                    String formattedResult = formatResult(result);
                    System.out.println("✅ Result: " + formattedResult);

                    // Add to history
                    addToHistory(input + " = " + formattedResult);
                }

            } catch (ArithmeticException e) {
                System.out.println("❌ Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("❌ Unexpected error: " + e.getMessage());
            }
        }

        System.out.println("\nThank you for using the calculator. Goodbye!");
        scanner.close();
    }

    /**
     * Prints the welcome message and instructions.
     */
    private static void printWelcome() {
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║   Advanced Scientific Calculator v2.0         ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println("\nFeatures:");
        System.out.println("• Basic: +, -, *, /, %, ^ (power)");
        System.out.println("• Scientific: sin, cos, tan, sqrt, log, ln, abs");
        System.out.println("• Parentheses: (2 + 3) * 4");
        System.out.println("• Memory: M+, M-, MR, MC");
        System.out.println("• Commands: help, history, clear history, exit");
        System.out.println("\nType 'help' for detailed instructions.");
    }

    /**
     * Prints help information.
     */
    private static void printHelp() {
        System.out.println("\n═══════════════ HELP ═══════════════");
        System.out.println("\nBASIC OPERATIONS:");
        System.out.println("  +    Addition         Example: 5 + 3");
        System.out.println("  -    Subtraction      Example: 10 - 4");
        System.out.println("  *    Multiplication   Example: 6 * 7");
        System.out.println("  /    Division         Example: 20 / 4");
        System.out.println("  %    Modulo          Example: 10 % 3");
        System.out.println("  ^    Power           Example: 2 ^ 8");
        System.out.println("\nSCIENTIFIC FUNCTIONS:");
        System.out.println("  sin(x)   Sine (degrees)      Example: sin(90)");
        System.out.println("  cos(x)   Cosine (degrees)    Example: cos(0)");
        System.out.println("  tan(x)   Tangent (degrees)   Example: tan(45)");
        System.out.println("  sqrt(x)  Square root         Example: sqrt(16)");
        System.out.println("  log(x)   Base-10 logarithm   Example: log(100)");
        System.out.println("  ln(x)    Natural logarithm   Example: ln(2.718)");
        System.out.println("  abs(x)   Absolute value      Example: abs(-5)");
        System.out.println("\nPARENTHESES:");
        System.out.println("  Example: (2 + 3) * 4 = 20");
        System.out.println("  Example: sqrt((9 + 16)) = 5");
        System.out.println("\nMEMORY FUNCTIONS:");
        System.out.println("  M+       Add to memory       Example: M+ 10");
        System.out.println("  M-       Subtract from memory Example: M- 5");
        System.out.println("  MR       Recall memory       Example: MR");
        System.out.println("  MC       Clear memory        Example: MC");
        System.out.println("\nCOMMANDS:");
        System.out.println("  help           Show this help");
        System.out.println("  history        Show calculation history");
        System.out.println("  clear history  Clear calculation history");
        System.out.println("  exit/quit      Exit calculator");
        System.out.println("════════════════════════════════════");
    }

    /**
     * Evaluates a mathematical expression.
     *
     * @param expression The expression to evaluate.
     * @return The result of the evaluation.
     */
    private static double evaluateExpression(String expression) {
        // Remove spaces
        final String expr = expression.replaceAll("\\s+", "").toLowerCase();

        if (expr.isEmpty()) return 0;

        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expr.length()) ? expr.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expr.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor | term `%` factor
            // factor = `+` factor | `-` factor | `(` expression `)` | number | function factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) {
                        double divisor = parseFactor();
                        if (divisor == 0) throw new ArithmeticException("Division by zero");
                        x /= divisor;
                    }
                    else if (eat('%')) {
                        double divisor = parseFactor();
                        if (divisor == 0) throw new ArithmeticException("Modulo by zero");
                        x %= divisor;
                    }
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expr.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = expr.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else if (func.equals("log")) x = Math.log10(x);
                    else if (func.equals("ln")) x = Math.log(x);
                    else if (func.equals("abs")) x = Math.abs(x);
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    /**
     * Handle memory commands.
     *
     * @param input The memory command to handle.
     */
    private static void handleMemoryCommand(String input) {
        String[] parts = input.split("\\s+");
        String command = parts[0].toUpperCase();

        switch (command) {
            case "M+":
                if (parts.length > 1) {
                    try {
                        double value = evaluateExpression(input.substring(2).trim());
                        memory += value;
                        System.out.println(
                                "✅ Added " + formatResult(value) + " to memory. Memory = " + formatResult(memory));
                    } catch (Exception e) {
                        System.out.println("❌ Error: Invalid expression for M+");
                    }
                } else {
                    System.out.println("❌ Error: M+ requires a value. Example: M+ 10");
                }
                break;

            case "M-":
                if (parts.length > 1) {
                    try {
                        double value = evaluateExpression(input.substring(2).trim());
                        memory -= value;
                        System.out.println("✅ Subtracted " + formatResult(value) + " from memory. Memory = "
                                + formatResult(memory));
                    } catch (Exception e) {
                        System.out.println("❌ Error: Invalid expression for M-");
                    }
                } else {
                    System.out.println("❌ Error: M- requires a value. Example: M- 5");
                }
                break;

            case "MR":
                System.out.println("📋 Memory recall: " + formatResult(memory));
                break;

            case "MC":
                memory = 0.0;
                System.out.println("✅ Memory cleared.");
                break;

            default:
                System.out.println("❌ Unknown memory command. Use M+, M-, MR, or MC.");
        }
    }

    /**
     * Add calculation to history.
     *
     * @param calculation The calculation to add to the history.
     */
    private static void addToHistory(String calculation) {
        history.add(calculation);
        // Limit history to last 50 entries
        if (history.size() > 50) {
            history.remove(0);
        }
    }

    /**
     * Print calculation history.
     */
    private static void printHistory() {
        if (history.isEmpty()) {
            System.out.println("\n📋 History is empty.");
            return;
        }

        System.out.println("\n═══════════════ HISTORY ═══════════════");
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i));
        }
        System.out.println("═══════════════════════════════════════");
    }

    /**
     * Clear calculation history.
     */
    private static void clearHistory() {
        history.clear();
        System.out.println("✅ History cleared.");
    }

    /**
     * Format result to handle floating-point precision.
     *
     * @param result The result to format.
     * @return The formatted result.
     */
    private static String formatResult(double result) {
        if (result == Math.floor(result) && !Double.isInfinite(result)) {
            return String.format("%.0f", result);
        }
        String formatted = String.format("%.10f", result).replaceAll("0+$", "").replaceAll("\\.$", "");
        return formatted.isEmpty() ? "0" : formatted;
    }
}
