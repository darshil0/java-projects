/**
 * TODO List Manager
 * A simple command-line application for managing tasks
 * 
 * @author Darshil
 * @version 1.0
 * @since 2025-11-13
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a single task in the TODO list
 */
class Task {
    private String description;
    private boolean completed;
    private int id;

    /**
     * Creates a new task with the given ID and description
     * 
     * @param id The unique identifier for the task
     * @param description The task description
     */
    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.completed = false;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a formatted string representation of the task
     * 
     * @return Formatted task string with ID, status, and description
     */
    @Override
    public String toString() {
        String status = completed ? "[X]" : "[ ]";
        return String.format("%d. %s %s", id, status, description);
    }
}

/**
 * Main TODO List application class
 * Manages tasks and provides a command-line interface
 */
public class TODOList {
    private ArrayList<Task> tasks;
    private int nextId;
    private Scanner scanner;

    /**
     * Initializes a new TODO List Manager
     */
    public TODOList() {
        tasks = new ArrayList<>();
        nextId = 1;
        scanner = new Scanner(System.in);
    }

    /**
     * Adds a new task to the list
     * 
     * @param description The task description
     */
    public void addTask(String description) {
        if (description == null || description.trim().isEmpty()) {
            System.out.println("X Task description cannot be empty.");
            return;
        }
        Task task = new Task(nextId++, description.trim());
        tasks.add(task);
        System.out.println("Task added successfully!");
    }

    /**
     * Displays all tasks in the list
     */
    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\nNo tasks in your list.");
            return;
        }

        System.out.println("\n===== YOUR TODO LIST =====");
        for (Task task : tasks) {
            System.out.println(task);
        }
        System.out.println("==========================");
    }

    /**
     * Marks a task as completed
     * 
     * @param id The ID of the task to complete
     */
    public void completeTask(int id) {
        Task task = findTaskById(id);
        if (task != null) {
            if (task.isCompleted()) {
                System.out.println("Task is already completed.");
            } else {
                task.setCompleted(true);
                System.out.println("Task marked as completed!");
            }
        } else {
            System.out.println("X Task not found with ID: " + id);
        }
    }

    /**
     * Deletes a task from the list
     * 
     * @param id The ID of the task to delete
     */
    public void deleteTask(int id) {
        Task task = findTaskById(id);
        if (task != null) {
            tasks.remove(task);
            System.out.println("Task deleted successfully!");
        } else {
            System.out.println("X Task not found with ID: " + id);
        }
    }

    /**
     * Edits the description of an existing task
     * 
     * @param id The ID of the task to edit
     * @param newDescription The new description for the task
     */
    public void editTask(int id, String newDescription) {
        if (newDescription == null || newDescription.trim().isEmpty()) {
            System.out.println("X Task description cannot be empty.");
            return;
        }
        Task task = findTaskById(id);
        if (task != null) {
            task.setDescription(newDescription.trim());
            System.out.println("Task updated successfully!");
        } else {
            System.out.println("X Task not found with ID: " + id);
        }
    }

    /**
     * Finds a task by its ID
     * 
     * @param id The ID to search for
     * @return The task if found, null otherwise
     */
    private Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    /**
     * Displays the main menu
     */
    public void showMenu() {
        System.out.println("\n===== TODO LIST MENU =====");
        System.out.println("1. Add Task");
        System.out.println("2. View All Tasks");
        System.out.println("3. Complete Task");
        System.out.println("4. Edit Task");
        System.out.println("5. Delete Task");
        System.out.println("6. Exit");
        System.out.println("==========================");
        System.out.print("Choose an option (1-6): ");
    }

    /**
     * Main application loop
     * Handles user input and menu navigation
     */
    public void run() {
        System.out.println("Welcome to TODO List Manager!\n");
        
        boolean running = true;
        while (running) {
            try {
                showMenu();
                
                if (!scanner.hasNextLine()) {
                    break;
                }
                
                String input = scanner.nextLine().trim();
                
                if (input.isEmpty()) {
                    System.out.println("X Please enter a valid option.");
                    continue;
                }
                
                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("X Invalid input. Please enter a number between 1-6.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        System.out.print("Enter task description: ");
                        if (scanner.hasNextLine()) {
                            String description = scanner.nextLine();
                            addTask(description);
                        }
                        break;
                        
                    case 2:
                        viewTasks();
                        break;
                        
                    case 3:
                        viewTasks();
                        if (!tasks.isEmpty()) {
                            System.out.print("Enter task ID to complete: ");
                            if (scanner.hasNextLine()) {
                                String idInput = scanner.nextLine().trim();
                                try {
                                    int completeId = Integer.parseInt(idInput);
                                    completeTask(completeId);
                                } catch (NumberFormatException e) {
                                    System.out.println("X Invalid ID. Please enter a number.");
                                }
                            }
                        }
                        break;
                        
                    case 4:
                        viewTasks();
                        if (!tasks.isEmpty()) {
                            System.out.print("Enter task ID to edit: ");
                            if (scanner.hasNextLine()) {
                                String idInput = scanner.nextLine().trim();
                                try {
                                    int editId = Integer.parseInt(idInput);
                                    System.out.print("Enter new description: ");
                                    if (scanner.hasNextLine()) {
                                        String newDesc = scanner.nextLine();
                                        editTask(editId, newDesc);
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("X Invalid ID. Please enter a number.");
                                }
                            }
                        }
                        break;
                        
                    case 5:
                        viewTasks();
                        if (!tasks.isEmpty()) {
                            System.out.print("Enter task ID to delete: ");
                            if (scanner.hasNextLine()) {
                                String idInput = scanner.nextLine().trim();
                                try {
                                    int deleteId = Integer.parseInt(idInput);
                                    deleteTask(deleteId);
                                } catch (NumberFormatException e) {
                                    System.out.println("X Invalid ID. Please enter a number.");
                                }
                            }
                        }
                        break;
                        
                    case 6:
                        System.out.println("\nThank you for using TODO List Manager. Goodbye!");
                        running = false;
                        break;
                        
                    default:
                        System.out.println("X Invalid option. Please choose a number between 1-6.");
                }
            } catch (Exception e) {
                System.out.println("X An unexpected error occurred: " + e.getMessage());
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            }
        }
        
        if (scanner != null) {
            scanner.close();
        }
    }

    /**
     * Main entry point of the application
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        TODOList todoList = new TODOList();
        todoList.run();
    }
}
