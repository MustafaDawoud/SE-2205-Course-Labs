package ca.uwo.eng.se2205b.lab2.model;
import java.util.List;

/**
 * Created by musda on 2/7/2017.
 */
public interface Student {

    /**
     * Sets the first name
     * @param fName first name
     * @throws IllegalArgumentException if parameter is null
     */
    void setFirstName(String fName) throws IllegalArgumentException;

    /**
     * sets the last name
     * @param lName sets the last name
     * @throws IllegalArgumentException if parameter is null
     */
    void setLastName(String lName) throws IllegalArgumentException;

    /**
     * @return first name
     */
    String getFirstName();

    /**
     * @return last name
     */
    String getLastName();

    /**
     * @param newID the new ID to be set for the student
     * @throws IllegalArgumentException if the parameter is <= 0
     */
    void setID(long newID) throws IllegalArgumentException;

    /**
     * @return the student's ID
     */
    long getID();

    /**
     * removes a certain course
     * @param course the course that the user wants to remove
     * @throws IllegalArgumentException
     * @return the course name if we successfully remove the course, null if the course was not found
     */
    String removeCourse(Course course) throws IllegalArgumentException;

    /**
     * Adds a specific course to the user
     * @param course the course the user wants to add
     * @throws IllegalArgumentException if the course that the user inputs is null
     */
    void addCourse(Course course) throws IllegalArgumentException;

    /**
     * sets the department of the student
     * @param newDepartment the new department that we are setting it to
     */
    void setDepartment(Department newDepartment);

    /**
     *
     * @return the current department
     */
    Department getDepartment();

    /**
     *
     * @return a list version of the courses
     */
    List<Course> getCourses();

    /**
     *
     * @return a string representation of a student, which includes their name, the
     *          courses they're taking, the department they are in, and their id
     */
    String toString();

    /**
     *
     * @param o the object we are comparing to
     * @return true if they have the same id (as they are unique, so if same unique, then same student), false
     *          otherwise
     */
    boolean equals(Object o);

}
