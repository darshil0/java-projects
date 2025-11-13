import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
    private static final String BASIC_OPERATORS = "+-*/%^";

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
        System.out.println("  sin(x)   Sine (radians)      Example: sin(1.57)");
        System.out.println("  cos(x)   Cosine (radians)    Example: cos(0)");
        System.out.println("  tan(x)   Tangent (radians)   Example: tan(0.785)");
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
        // Replace scientific functions first
        expression = replaceScientificFunctions(expression);

        // Remove spaces
        expression = expression.replaceAll("\\s+", "");

        // Validate parentheses
        if (!areParenthesesBalanced(expression)) {
            throw new IllegalArgumentException("Unbalanced parentheses in expression.");
        }

        return evaluate(expression);
    }

    /**
     * Replace scientific function names with their calculated values.
     *
     * @param expr The expression to process.
     * @return The expression with scientific functions replaced by their values.
     */
    private static String replaceScientificFunctions(String expr) {
        // Process functions from innermost to outermost
        while (expr.contains("sin(") || expr.contains("cos(") || expr.contains("tan(") ||
                expr.contains("sqrt(") || expr.contains("log(") || expr.contains("ln(") ||
                expr.contains("abs(")) {

            expr = processFunctionOnce(expr, "sin", x -> Math.sin(x));
            expr = processFunctionOnce(expr, "cos", x -> Math.cos(x));
            expr = processFunctionOnce(expr, "tan", x -> Math.tan(x));
            expr = processFunctionOnce(expr, "sqrt", x -> {
                if (x < 0)
                    throw new ArithmeticException("Cannot take square root of negative number.");
                return Math.sqrt(x);
            });
            expr = processFunctionOnce(expr, "log", x -> {
                if (x <= 0)
                    throw new ArithmeticException("Logarithm undefined for non-positive numbers.");
                return Math.log10(x);
            });
            expr = processFunctionOnce(expr, "ln", x -> {
                if (x <= 0)
                    throw new ArithmeticException("Natural logarithm undefined for non-positive numbers.");
                return Math.log(x);
            });
            expr = processFunctionOnce(expr, "abs", x -> Math.abs(x));
        }

        return expr;
    }

    /**
     * Process one occurrence of a function.
     *
     * @param expr      The expression to process.
     * @param funcName  The name of the function to process.
     * @param func      The function to apply.
     * @return The expression with one function call replaced by its value.
     */
    private static String processFunctionOnce(String expr, String funcName, java.util.function.DoubleUnaryOperator func) {
        int index = expr.indexOf(funcName + "(");
        if (index == -1)
            return expr;

        int start = index + funcName.length();
        int parenCount = 1;
        int end = start + 1;

        while (end < expr.length() && parenCount > 0) {
            if (expr.charAt(end) == '(')
                parenCount++;
            else if (expr.charAt(end) == ')')
                parenCount--;
            end++;
        }

        if (parenCount != 0) {
            throw new IllegalArgumentException("Unbalanced parentheses in " + funcName + " function.");
        }

        String innerExpr = expr.substring(start + 1, end - 1);
        double value = evaluate(innerExpr);
        double result = func.applyAsDouble(value);

        return expr.substring(0, index) + result + expr.substring(end);
    }

    /**
     * Check if parentheses are balanced.
     *
     * @param expr The expression to check.
     * @return True if the parentheses are balanced, false otherwise.
     */
    private static boolean areParenthesesBalanced(String expr) {
        int count = 0;
        for (char c : expr.toCharArray()) {
            if (c == '(')
                count++;
            else if (c == ')')
                count--;
            if (count < 0)
                return false;
        }
        return count == 0;
    }

    /**
     * Evaluate expression using operator precedence.
     *
     * @param expression The expression to evaluate.
     * @return The result of the evaluation.
     */
    private static double evaluate(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ')
                    nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // Skip whitespace
            if (c == ' ')
                continue;

            // Handle numbers (including decimals and negatives)
            if (Character.isDigit(c) || c == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() &&
                        (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length())
                    throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)` | number
            // | function factor
            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+'))
                        x += parseTerm(); // addition
                    else if (eat('-'))
                        x -= parseTerm(); // subtraction
                    else
                        return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*'))
                        x *= parseFactor(); // multiplication
                    else if (eat('/'))
                        x /= parseFactor(); // division
                    else if (eat('%'))
                        x %= parseFactor(); // modulo
                    else
                        return x;
                }
            }

            double parseFactor() {
                if (eat('+'))
                    return parseFactor(); // unary plus
                if (eat('-'))
                    return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.')
                        nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z')
                        nextChar();
                    String func = expression.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt"))
                        x = Math.sqrt(x);
                    else if (func.equals("sin"))
                        x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos"))
                        x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan"))
                        x = Math.tan(Math.toRadians(x));
                    else
                        throw new RuntimeException("Unknown function: " + func);
                if (!operators.isEmpty())
                    operators.pop(); // Remove '('
            }
            // Handle operators
            else if (isOperator(c)) {
                // Handle negative numbers at start or after operators/parentheses
                if (c == '-' && (i == 0 || expression.charAt(i - 1) == '(' || isOperator(expression.charAt(i - 1)))) {
                    StringBuilder sb = new StringBuilder("-");
                    i++;
                    while (i < expression.length() &&
                            (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                        sb.append(expression.charAt(i++));
                    }
                    i--;
                    values.push(Double.parseDouble(sb.toString()));
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^'))
                    x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

        }

        // Apply remaining operators
        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    /**
     * Check if character is an operator.
     *
     * @param c The character to check.
     * @return True if the character is an operator, false otherwise.
     */
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^';
    }

    /**
     * Check operator precedence.
     *
     * @param op1 The first operator.
     * @param op2 The second operator.
     * @return True if op2 has higher or same precedence as op1, false otherwise.
     */
    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if (op1 == '^')
            return false; // ^ has highest precedence, right-associative
        if ((op1 == '*' || op1 == '/' || op1 == '%') && (op2 == '+' || op2 == '-'))
            return false;
        return true;
    }

    /**
     * Apply an operator to two operands.
     *
     * @param op The operator to apply.
     * @param b  The second operand.
     * @param a  The first operand.
     * @return The result of the operation.
     */
    private static double applyOperator(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new ArithmeticException("Division by zero is not allowed.");
                return a / b;
            case '%':
                if (b == 0)
                    throw new ArithmeticException("Modulo by zero is not allowed.");
                return a % b;
            case '^':
                return Math.pow(a, b);
            default:
                throw new IllegalArgumentException("Unknown operator: " + op);
        }
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
