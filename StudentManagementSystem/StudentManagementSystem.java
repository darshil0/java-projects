import java.util.ArrayList;
import java.util.Scanner;

/**
 * A simple console-based student management system.
 */
public class StudentManagementSystem {

    /**
     * The main method that runs the student management system.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Create an ArrayList to store the student records.
        ArrayList<Student> students = new ArrayList<>();
        // Create a Scanner object to read input from the user.
        Scanner scanner = new Scanner(System.in);
        // Variable to store the user's choice.
        int choice;
        // Counter for student IDs.
        int nextId = 1;

        // Loop to display the menu and get user input.
        do {
            // Print the menu.
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add a new student");
            System.out.println("2. Remove a student");
            System.out.println("3. View all students");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            // Get the user's choice.
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // Use a switch statement to perform actions based on the user's choice.
            switch (choice) {
                case 1:
                    // Add a new student.
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student grade: ");
                    String grade = scanner.nextLine();
                    students.add(new Student(nextId++, name, grade));
                    System.out.println("Student added successfully.");
                    break;
                case 2:
                    // Remove a student.
                    System.out.println("Current students:");
                    viewStudents(students);
                    System.out.print("Enter the ID of the student to remove: ");
                    int idToRemove = scanner.nextInt();
                    students.removeIf(student -> student.getId() == idToRemove);
                    System.out.println("Student removed successfully.");
                    break;
                case 3:
                    // View all students.
                    viewStudents(students);
                    break;
                case 4:
                    // Exit the program.
                    System.out.println("Exiting the student management system.");
                    break;
                default:
                    // Handle invalid choices.
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        // Close the scanner.
        scanner.close();
    }

    /**
     * A helper method to view all the students in the system.
     * @param students The ArrayList containing the student records.
     */
    public static void viewStudents(ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students in the system.");
        } else {
            System.out.println("--- Student List ---");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }
}
