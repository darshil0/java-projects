import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.List;

/**
 * An enhanced number guessing game with multiple difficulty levels,
 * scoring system, statistics tracking, and advanced hint system.
 */
public class NumberGuessingGame {
    
    // Game statistics
    private static int totalGamesPlayed = 0;
    private static int totalGamesWon = 0;
    private static int bestScore = Integer.MAX_VALUE;
    private static List<Integer> scoreHistory = new ArrayList<>();
    
    // Difficulty settings
    private enum Difficulty {
        EASY(1, 50, 10, "Easy"),
        MEDIUM(1, 100, 7, "Medium"),
        HARD(1, 500, 12, "Hard"),
        EXPERT(1, 1000, 15, "Expert");
        
        final int min;
        final int max;
        final int maxAttempts;
        final String name;
        
        Difficulty(int min, int max, int maxAttempts, String name) {
            this.min = min;
            this.max = max;
            this.maxAttempts = maxAttempts;
            this.name = name;
        }
    }
    
    /**
     * The main method that runs the game.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;
        
        printWelcome();
        
        while (playAgain) {
            try {
                // Select difficulty
                Difficulty difficulty = selectDifficulty(scanner);
                
                // Play one round
                playRound(scanner, difficulty);
                
                // Update statistics
                totalGamesPlayed++;
                
                // Ask to play again
                playAgain = askToPlayAgain(scanner);
                
            } catch (Exception e) {
                System.out.println("❌ An unexpected error occurred: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
        
        // Print final statistics
        printFinalStatistics();
        
        System.out.println("\n✨ Thank you for playing! Goodbye! ✨");
        scanner.close();
    }
    
    /**
     * Print welcome message.
     */
    private static void printWelcome() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║     🎮 ENHANCED NUMBER GUESSING GAME 🎮     ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("\n🎯 Goal: Guess the secret number in as few tries as possible!");
        System.out.println("💡 Hints: You'll get progressive hints based on your guesses.");
        System.out.println("⭐ Score: Lower attempts = Better score!\n");
    }
    
    /**
     * Select difficulty level.
     */
    private static Difficulty selectDifficulty(Scanner scanner) {
        System.out.println("\n━━━━━━━━━━━━━ SELECT DIFFICULTY ━━━━━━━━━━━━━");
        System.out.println("1. 🟢 Easy   (1-50,   Max 10 attempts)");
        System.out.println("2. 🟡 Medium (1-100,  Max 7 attempts)");
        System.out.println("3. 🔴 Hard   (1-500,  Max 12 attempts)");
        System.out.println("4. 💀 Expert (1-1000, Max 15 attempts)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        while (true) {
            try {
                System.out.print("\nEnter your choice (1-4): ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                
                switch (choice) {
                    case 1: return Difficulty.EASY;
                    case 2: return Difficulty.MEDIUM;
                    case 3: return Difficulty.HARD;
                    case 4: return Difficulty.EXPERT;
                    default:
                        System.out.println("⚠️ Invalid choice. Please select 1-4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Invalid input. Please enter a number (1-4).");
                scanner.nextLine(); // Clear buffer
            }
        }
    }
    
    /**
     * Play one round of the game.
     */
    private static void playRound(Scanner scanner, Difficulty difficulty) {
        Random random = new Random();
        int numberToGuess = random.nextInt(difficulty.max - difficulty.min + 1) + difficulty.min;
        int numberOfTries = 0;
        boolean hasGuessedCorrectly = false;
        int previousGuess = -1;
        
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║              🎮 NEW GAME STARTED 🎮          ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("📊 Difficulty: " + difficulty.name);
        System.out.println("🎯 Range: " + difficulty.min + " - " + difficulty.max);
        System.out.println("🎫 Maximum Attempts: " + difficulty.maxAttempts);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        
        // Game loop
        while (!hasGuessedCorrectly && numberOfTries < difficulty.maxAttempts) {
            try {
                // Get user guess with validation
                int userGuess = getUserGuess(scanner, difficulty, numberOfTries + 1, difficulty.maxAttempts);
                numberOfTries++;
                
                // Check if guess is correct
                if (userGuess == numberToGuess) {
                    hasGuessedCorrectly = true;
                    printVictory(numberOfTries, difficulty);
                    updateStatistics(numberOfTries, true);
                } else {
                    // Provide hints
                    provideHints(userGuess, numberToGuess, previousGuess, numberOfTries, difficulty);
                    previousGuess = userGuess;
                }
                
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear buffer
            }
        }
        
        // Check if player ran out of attempts
        if (!hasGuessedCorrectly) {
            System.out.println("\n💔 Game Over! You've used all " + difficulty.maxAttempts + " attempts.");
            System.out.println("🔢 The number was: " + numberToGuess);
            updateStatistics(numberOfTries, false);
        }
    }
    
    /**
     * Get validated user guess.
     */
    private static int getUserGuess(Scanner scanner, Difficulty difficulty, int currentAttempt, int maxAttempts) {
        while (true) {
            System.out.print("🎯 Attempt " + currentAttempt + "/" + maxAttempts + " - Enter your guess: ");
            
            if (!scanner.hasNextInt()) {
                System.out.println("⚠️ Invalid input. Please enter a number between " + 
                                 difficulty.min + " and " + difficulty.max + ".");
                scanner.next(); // Clear invalid input
                continue;
            }
            
            int guess = scanner.nextInt();
            
            // Validate range
            if (guess < difficulty.min || guess > difficulty.max) {
                System.out.println("⚠️ Out of range! Please enter a number between " + 
                                 difficulty.min + " and " + difficulty.max + ".");
                continue;
            }
            
            return guess;
        }
    }
    
    /**
     * Provide progressive hints based on distance and attempt number.
     */
    private static void provideHints(int guess, int target, int previousGuess, 
                                     int attempts, Difficulty difficulty) {
        int distance = Math.abs(guess - target);
        int range = difficulty.max - difficulty.min;
        double percentageOff = (distance * 100.0) / range;
        
        // Basic high/low hint
        if (guess < target) {
            System.out.print("📈 Too low! ");
        } else {
            System.out.print("📉 Too high! ");
        }
        
        // Distance-based hints
        if (percentageOff <= 2) {
            System.out.print("🔥 BURNING HOT! ");
        } else if (percentageOff <= 5) {
            System.out.print("🌶️ Very close! ");
        } else if (percentageOff <= 10) {
            System.out.print("🌡️ Close! ");
        } else if (percentageOff <= 20) {
            System.out.print("❄️ Getting warmer... ");
        } else if (percentageOff <= 40) {
            System.out.print("🧊 Cold... ");
        } else {
            System.out.print("⛄ Very cold! ");
        }
        
        // Progression hints (getting closer/farther)
        if (previousGuess != -1) {
            int previousDistance = Math.abs(previousGuess - target);
            if (distance < previousDistance) {
                System.out.print("✅ Getting closer!");
            } else if (distance > previousDistance) {
                System.out.print("⚠️ Getting farther!");
            } else {
                System.out.print("↔️ Same distance.");
            }
        }
        
        System.out.println();
        
        // Extra hint after multiple attempts
        if (attempts >= difficulty.maxAttempts / 2) {
            int hint = target / 10 * 10; // Round to nearest 10
            System.out.println("💡 Hint: The number is in the " + hint + "s range.");
        }
    }
    
    /**
     * Print victory message with performance rating.
     */
    private static void printVictory(int attempts, Difficulty difficulty) {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║          🎉 CONGRATULATIONS! 🎉              ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("✅ You guessed the number in " + attempts + " attempts!");
        
        // Performance rating
        String rating;
        String emoji;
        if (attempts <= 3) {
            rating = "LEGENDARY";
            emoji = "🏆";
        } else if (attempts <= 5) {
            rating = "EXCELLENT";
            emoji = "⭐";
        } else if (attempts <= 7) {
            rating = "GOOD";
            emoji = "👍";
        } else if (attempts <= difficulty.maxAttempts - 2) {
            rating = "AVERAGE";
            emoji = "✔️";
        } else {
            rating = "LUCKY";
            emoji = "🍀";
        }
        
        System.out.println(emoji + " Performance: " + rating + " " + emoji);
        
        // Check for new best score
        if (attempts < bestScore) {
            bestScore = attempts;
            System.out.println("🎊 NEW BEST SCORE! 🎊");
        }
        
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
    
    /**
     * Update game statistics.
     */
    private static void updateStatistics(int attempts, boolean won) {
        if (won) {
            totalGamesWon++;
            scoreHistory.add(attempts);
            if (attempts < bestScore) {
                bestScore = attempts;
            }
        }
    }
    
    /**
     * Ask if player wants to play again.
     */
    private static boolean askToPlayAgain(Scanner scanner) {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.print("🎮 Play again? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.startsWith("y");
    }
    
    /**
     * Print final statistics.
     */
    private static void printFinalStatistics() {
        if (totalGamesPlayed == 0) return;
        
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║           📊 GAME STATISTICS 📊              ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("🎮 Total Games Played: " + totalGamesPlayed);
        System.out.println("🏆 Games Won: " + totalGamesWon);
        System.out.println("💔 Games Lost: " + (totalGamesPlayed - totalGamesWon));
        
        if (totalGamesWon > 0) {
            double winRate = (totalGamesWon * 100.0) / totalGamesPlayed;
            System.out.println("📈 Win Rate: " + String.format("%.1f", winRate) + "%");
            System.out.println("⭐ Best Score: " + bestScore + " attempts");
            
            // Calculate average score
            double avgScore = scoreHistory.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
            System.out.println("📊 Average Score: " + String.format("%.1f", avgScore) + " attempts");
        }
        
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}
