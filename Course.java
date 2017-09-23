package ca.uwo.eng.se2205b.lab2.model;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Describes a course offering at a University
 */
public interface Course {

    /**
     * Get the unique course code for the Course.
     * @return Non-{@code null} or empty code for the course.
     */
    String getCourseCode();

    /**
     * Set the name of the course
     * @param name Name of the course
     *
     * @throws NullPointerException if {@code name} is {@code null}.
     * @throws IllegalArgumentException if {@code name} is empty.
     */
    void setName(@Nonnull String name) throws IllegalArgumentException;

    /**
     * Get the current name of a course
     * @return Current name
     */
    String getName();

    /**
     * Change the {@link Department} for a {@code Course}
     * @param newDepartment New department a course resides in.
     */
    void setDepartment(Department newDepartment);

    /**
     * Get the current {@link Department}
     * @return Possibly {@code null} department.
     */
    Department getDepartment();

    /**
     * Get the maximum occupancy of the course
     * @return Maximum number of students in the course.
     */
    int getMaximumOccupancy();

    /**
     * Enroll a student in the course.
     * @param student Non-{@code null} student to add.
     *
     * @throws IllegalStateException if there is no room.
     */
    void enrollStudent(@Nonnull Student student) throws IllegalArgumentException;

    /**
     * Remove a {@link Student} from a {@code Course}
     * @param student Removed student
     * @return The Student instance, if they were removed, otherwise {@code null}.
     */
    Student removeStudent(@Nonnull Student student);

    /**
     * Get all of the currently enrolled students
     * @return Non-{@code null} {@code List} of {@link Student}s, it may be empty.
     */
    List<Student> getEnrolledStudents();

    /**
     *
     * @return a string version of a course which consists of the course name, capacity, course ID, course dept.
     *          and the students enrolled in the course
     */
    String toString();

    /**
     *
     * @param o the object that we are comparing it to
     * @return true if they have the same course ID as it is the unique factor in the course, false otherwise
     */
    boolean equals(Object o);
}
