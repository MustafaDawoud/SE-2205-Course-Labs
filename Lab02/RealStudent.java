package ca.uwo.eng.se2205b.lab2.model;

import com.google.common.base.MoreObjects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by musda on 2/7/2017.
 */

class RealStudent implements Student{
    private String firstName;
    private String lastName;
    private ArrayList<Course> courses;
    private Department dpt;
    private long id;

    /**
     * @param fname to initialize first name
     * @param lname to initialize last name
     * @param studentId to initialize student id
     */
    public RealStudent(@Nonnull String fname, @Nonnull String lname, @Nonnull long studentId)
            throws IllegalArgumentException{
        if(fname == "" || lname == "" || studentId <= 0){
            throw new IllegalArgumentException();
        }
        firstName = fname;
        lastName = lname;
        courses = new ArrayList<>();
        dpt = null;
        id = studentId;
    }

    /**
     * Creates a new Student.
     * @param fname Non-{@code null} or empty name
     * @param lname Non-{@code null} or empty name
     * @param studentId Positive student ID
     * @param department Department that may be {@code null}
     *
     * @throws IllegalArgumentException if the name/course code is {@code null} or empty
     *                                  or the max occupancy is negative
     */
    public RealStudent(@Nonnull String fname, @Nonnull String lname, long studentId, @Nullable Department department)
            throws IllegalArgumentException {
        if(fname == "" || lname == "" || studentId < 0){
            throw new IllegalArgumentException();
        }
        firstName = fname;
        lastName = lname;
        id = studentId;
        this.setDepartment(department);
        courses = new ArrayList<>();
    }

    /**
     * Sets the first name
     * @param fName first name
     * @throws IllegalArgumentException if parameter is null
     */
    @Override
    public void setFirstName(@Nonnull String fName) throws IllegalArgumentException{
        if(fName == ""){
            throw new IllegalArgumentException();
        }
        firstName = fName;
    }

    /**
     * sets the last name
     * @param lName sets the last name
     * @throws IllegalArgumentException if parameter is null
     */
    @Override
    public void setLastName(@Nonnull String lName) throws IllegalArgumentException{
        if(lName == ""){
            throw new IllegalArgumentException();
        }
        lastName = lName;
    }

    /**
     * returns the first name
     * @return first name
     */
    @Override
    public String getFirstName(){
        return firstName;
    }

    /**
     * returns the last name
     * @return last name
     */
    @Override
    public String getLastName(){
        return lastName;
    }

    /**
     * sets students ID
     * @param newID the new ID to be set for the student
     * @throws IllegalArgumentException if the parameter is <= 0
     */
    @Override
    public void setID(long newID) throws IllegalArgumentException{
        if(newID <= 0)
            throw new IllegalArgumentException();
        id = newID;
    }

    /**
     * returns student ID
     * @return the student's ID
     */
    @Override
    public long getID(){
        return id;
    }

    /**
     * removes a certain course
     * @param course the course that the user wants to remove
     * @throws IllegalArgumentException
     * @return the course name if we successfully remove the course, null if the course was not found
     */
    @Override
    public String removeCourse(@Nonnull Course course) throws IllegalArgumentException{
        //Checking if the student is even enrolled in the course
        if(course.removeStudent(this)!=null){
            //if the course is enrolled, remove from the student course list
            courses.remove(course);
            return course.getName();
        }
        return null;
    }

    /**
     * Adds a specific course to the user
     * @param course the course the user wants to add
     * @throws IllegalArgumentException if the course that the user inputs is null
     */
    @Override
    public void addCourse(@Nonnull Course course)throws IllegalArgumentException{
        //Enrolling the student in the course
        course.enrollStudent(this);
        //if the student ends up being enrolled in the course
        if(course.getEnrolledStudents().contains(this)){
            //if they are already enrollled in the course, just return, else add the course
            if(courses.contains(course))
                return;
            else
                courses.add(course);
        }
    }

    /**
     * sets the department of the student
     * @param newDepartment the new department that we are setting it to
     */
    @Override
    public void setDepartment(Department newDepartment){
        if(dpt == newDepartment)
            return;
        //Remove the student from the old department and set the department to null
        else if(dpt != null && newDepartment == null){
            dpt.removeStudent(this);
            dpt = newDepartment;
            return;
        }
        //if the old one is not null and the new one is not null, remove the student from the old and add to the new
        else if(newDepartment != null && dpt != null){
            dpt.removeStudent(this);
            dpt = newDepartment;
            dpt.enrollStudent(this);
            return;
        }
        //if the old is null, and the new is not null, just add the user to new department
        else if(newDepartment != null && dpt == null){
            dpt = newDepartment;
            dpt.enrollStudent(this);
            return;
        }
    }

    /**
     * returns the department of the student
     * @return the current department
     */
    @Override
    public Department getDepartment(){
        return dpt;
    }

    /**
     * returns the courses the student is currently enrolled in
     * @return a list version of the courses
     */
    @Override
    public List<Course> getCourses(){
        return Collections.unmodifiableList(courses);
    }

    /**
     * returns a string representation of the string
     * @return a string representation of a student, which includes their name, the
     *          courses they're taking, the department they are in, and their id
     */
    @Override
    public String toString(){
        return MoreObjects.toStringHelper(this)
                .add("Name", this.getFirstName())
                .add("Courses", courses.stream().map(Course::getCourseCode).collect(Collectors.toList()))
                .add("Department", (Objects.equals(dpt,null))? null:dpt.getName())
                .add("ID", id)
                .toString();
    }

    /**
     * compares an incoming object with this object
     * @param o the object we are comparing to
     * @return true if they have the same id (as they are unique, so if same unique, then same student), false
     *          otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealStudent that = (RealStudent) o;
        return id == that.id;
    }
}