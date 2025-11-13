/**
 * A simple class to represent a student.
 * It holds the student's ID, name, and grade.
 *
 * @author Darshil
 * @version 1.0
 */
public class Student {
    private int id;
    private String name;
    private String grade;

    /**
     * Constructor for the Student class.
     *
     * @param id    The student's ID.
     * @param name  The student's name.
     * @param grade The student's grade.
     */
    public Student(int id, String name, String grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    /**
     * Gets the student's ID.
     *
     * @return The student's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the student's name.
     *
     * @return The student's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the student's grade.
     *
     * @return The student's grade.
     */
    public String getGrade() {
        return grade;
    }

    /**
     * Returns a string representation of the student.
     *
     * @return A string containing the student's details.
     */
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Grade: " + grade;
    }
}
