import java.util.ArrayList;
import java.util.Scanner;

class Task {
    private String description;
    private boolean completed;
    private int id;

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

    @Override
    public String toString() {
        String status = completed ? "[X]" : "[ ]";
        return String.format("%d. %s %s", id, status, description);
    }
}

class TODOList {
    private ArrayList<Task> tasks;
    private int nextId;
    private Scanner scanner;

    public TODOList() {
        tasks = new ArrayList<>();
        nextId = 1;
        scanner = new Scanner(System.in);
    }

    public void addTask(String description) {
        if (description == null || description.trim().isEmpty()) {
            System.out.println("X Task description cannot be empty.");
            return;
        }
        Task task = new Task(nextId++, description.trim());
        tasks.add(task);
        System.out.println("Task added successfully!");
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks in your list.");
            return;
        }

        System.out.println("\n===== YOUR TODO LIST =====");
        for (Task task : tasks) {
            System.out.println(task);
        }
        System.out.println("==========================\n");
    }

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
            System.out.println("X Task not found.");
        }
    }

    public void deleteTask(int id) {
        Task task = findTaskById(id);
        if (task != null) {
            tasks.remove(task);
            System.out.println("Task deleted successfully!");
        } else {
            System.out.println("X Task not found.");
        }
    }

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
            System.out.println("X Task not found.");
        }
    }

    private Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public void showMenu() {
        System.out.println("\n===== TODO LIST MENU =====");
        System.out.println("1. Add Task");
        System.out.println("2. View All Tasks");
        System.out.println("3. Complete Task");
        System.out.println("4. Edit Task");
        System.out.println("5. Delete Task");
        System.out.println("6. Exit");
        System.out.println("==========================");
        System.out.print("Choose an option: ");
    }

    public void run() {
        System.out.println("Welcome to TODO List Manager!");
        
        boolean running = true;
        while (running) {
            try {
                showMenu();
                int choice = getIntInput();

                switch (choice) {
                    case 1:
                        System.out.print("Enter task description: ");
                        String description = scanner.nextLine();
                        addTask(description);
                        break;
                    case 2:
                        viewTasks();
                        break;
                    case 3:
                        viewTasks();
                        if (!tasks.isEmpty()) {
                            System.out.print("Enter task ID to complete: ");
                            int completeId = getIntInput();
                            completeTask(completeId);
                        }
                        break;
                    case 4:
                        viewTasks();
                        if (!tasks.isEmpty()) {
                            System.out.print("Enter task ID to edit: ");
                            int editId = getIntInput();
                            System.out.print("Enter new description: ");
                            String newDesc = scanner.nextLine();
                            editTask(editId, newDesc);
                        }
                        break;
                    case 5:
                        viewTasks();
                        if (!tasks.isEmpty()) {
                            System.out.print("Enter task ID to delete: ");
                            int deleteId = getIntInput();
                            deleteTask(deleteId);
                        }
                        break;
                    case 6:
                        System.out.println("Thank you for using TODO List Manager. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("X Invalid option. Please choose 1-6.");
                }
            } catch (Exception e) {
                System.out.println("X An error occurred: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
        scanner.close();
    }

    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return input;
    }

    public static void main(String[] args) {
        TODOList todoList = new TODOList();
        todoList.run();
    }
}
