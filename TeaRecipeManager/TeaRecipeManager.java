import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Tea Recipe Manager
 * A simple application for managing tea preparation recipes.
 * Based on the grounded evidence from the misc directory.
 *
 * @author Jules
 * @version 1.0
 */
public class TeaRecipeManager {

    private List<Recipe> recipes;

    public TeaRecipeManager() {
        recipes = new ArrayList<>();
        loadDefaultRecipe();
    }

    private void loadDefaultRecipe() {
        Recipe tea = new Recipe("Classic Ginger Tea");
        tea.addIngredient("2 tablespoons of tea");
        tea.addIngredient("3 Cups of Water");
        tea.addIngredient("1 tablespoon of Ginger Powder");
        tea.addIngredient("Milk");

        tea.addStep("Heat the mixture of tea, water, and ginger powder for 10-15 minutes.");
        tea.addStep("Add milk to the mixture.");
        tea.addStep("Stir the mixture for another 10-15 minutes.");
        tea.addStep("Serve the tea hot in 2 cups.");
        tea.addStep("Enjoy!");

        recipes.add(tea);
    }

    public void showMenu() {
        System.out.println("\n===== TEA RECIPE MANAGER =====");
        System.out.println("1. View All Recipes");
        System.out.println("2. Add New Recipe");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 3) {
            showMenu();
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) continue;
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    viewRecipes();
                    break;
                case 2:
                    addNewRecipe(scanner);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void viewRecipes() {
        if (recipes.isEmpty()) {
            System.out.println("No recipes found.");
            return;
        }

        for (int i = 0; i < recipes.size(); i++) {
            System.out.println("\n--- Recipe #" + (i + 1) + " ---");
            System.out.println(recipes.get(i));
        }
    }

    private void addNewRecipe(Scanner scanner) {
        System.out.print("Enter recipe name: ");
        String name = scanner.nextLine();
        Recipe recipe = new Recipe(name);

        System.out.println("Enter ingredients (type 'done' when finished):");
        while (true) {
            String ingredient = scanner.nextLine();
            if (ingredient.equalsIgnoreCase("done")) break;
            recipe.addIngredient(ingredient);
        }

        System.out.println("Enter preparation steps (type 'done' when finished):");
        while (true) {
            String step = scanner.nextLine();
            if (step.equalsIgnoreCase("done")) break;
            recipe.addStep(step);
        }

        recipes.add(recipe);
        System.out.println("Recipe added successfully!");
    }

    public static void main(String[] args) {
        new TeaRecipeManager().run();
    }

    static class Recipe {
        String name;
        List<String> ingredients;
        List<String> steps;

        Recipe(String name) {
            this.name = name;
            this.ingredients = new ArrayList<>();
            this.steps = new ArrayList<>();
        }

        void addIngredient(String ingredient) {
            ingredients.add(ingredient);
        }

        void addStep(String step) {
            steps.add(step);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Recipe: ").append(name).append("\n");
            sb.append("\nIngredients:\n");
            for (String ing : ingredients) {
                sb.append("- ").append(ing).append("\n");
            }
            sb.append("\nSteps:\n");
            for (int i = 0; i < steps.size(); i++) {
                sb.append((i + 1)).append(". ").append(steps.get(i)).append("\n");
            }
            return sb.toString();
        }
    }
}
