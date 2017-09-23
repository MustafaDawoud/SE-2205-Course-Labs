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

class RealCourse implements Course {
    private String cname;
    private String courseID;
    private ArrayList<Student> students;
    private final int maxStudents;
    private Department dpt;

    /**
     *
     * @param name for the course name
     * @param id for the course ID
     * @param ms for the maximum capacity
     * @throws IllegalArgumentException if the name/course code is {@code null} or empty
     */
    public RealCourse(@Nonnull String name, @Nonnull String id, int ms)
            throws IllegalArgumentException{
        if(name == "" || ms < 0){
            throw new IllegalArgumentException();
        }
        cname = name;
        courseID = id;
        students = new ArrayList<>(ms);
        maxStudents = ms;
        dpt = null;
    }

    /**
     * Creates a new Course.
     * @param name Non-{@code null} or empty name
     * @param courseCode Non-{@code null} or empty courseCode
     * @param department Department that may be {@code null}
     * @param maxOccupancy The maximum number of students allowed in the course.
     *
     * @throws IllegalArgumentException if the name/course code is {@code null} or empty
     *                                  or the max occupancy is negative
     */
    public RealCourse(@Nonnull String name, @Nonnull String courseCode, @Nullable Department department, int maxOccupancy)
            throws IllegalArgumentException {
        if(courseCode == "" || maxOccupancy < 0){
            throw new IllegalArgumentException();
        }
        cname = name;
        courseID = courseCode;
        this.setDepartment(department);
        maxStudents = maxOccupancy;
        students = new ArrayList<>(maxStudents);
    }

    /**
     * @param id new course ID
     */
    public void setCourseID(@Nonnull String id) throws IllegalArgumentException{
        if(id == "")
            throw new IllegalArgumentException();
        courseID = id;
    }
    /**
     * Get the unique course code for the Course.
     * @return Non-{@code null} or empty code for the course.
     */
    @Override
    public String getCourseCode(){
       return courseID;
    }

    /**
     * Set the name of the course
     * @param name Name of the course
     *
     * @throws NullPointerException if {@code name} is {@code null}.
     * @throws IllegalArgumentException if {@code name} is empty or null.
     */
    @Override
    public void setName(@Nonnull String name) throws IllegalArgumentException{
       if(name == "")
            throw new IllegalArgumentException();
        cname = name;
    }

    /**
     * Get the current name of a course
     * @return Current name
     */
    @Override
    public String getName(){
        return cname;
    }

    /**
     * Change the {@link Department} for a {@code Course}
     * @param newDepartment New department a course resides in.
     */
    @Override
    public void setDepartment(Department newDepartment){
        if(dpt == newDepartment)
            return;
        if(dpt != null && newDepartment == null){
            dpt.removeCourse(this);
            dpt = newDepartment;
            return;
        }
        else if(newDepartment != null && dpt != null){
            dpt.removeCourse(this);
            dpt = newDepartment;
            dpt.addCourse(this);
            return;
        }
        else if(newDepartment != null && dpt == null){
            dpt = newDepartment;
            dpt.addCourse(this);
            return;
        }
    }

    /**
     * Get the current {@link Department}
     * @return Possibly {@code null} department.
     */
    @Override
    public Department getDepartment(){
        return this.dpt;
    }

    /**
     * Get the maximum occupancy of the course
     * @return Maximum number of students in the course.
     */
    @Override
    public int getMaximumOccupancy(){
        return maxStudents;
    }

    /**
     * Enroll a student in the course.
     * @param student Non-{@code null} student to add.
     *
     * @throws IllegalStateException if there is no room or if student param is null
     */
    @Override
    public void enrollStudent(@Nonnull Student student) throws IllegalArgumentException{
        if(students.size() >= maxStudents+1){
            students.remove(student);
            throw new CourseMaxCapacityStoreException(this, student);
        }
        if(students.contains(student)){
            return;
        }
        students.add(student);
        student.addCourse(this);
    }

    /**
     * Remove a {@link Student} from a {@code Course}
     * @param student Removed student
     * @throws IllegalArgumentException if the student is null.
     * @return The Student instance, if they were removed, otherwise {@code null}.
     */
    @Override
    public Student removeStudent(@Nonnull Student student) throws IllegalArgumentException{
        if(students.remove(student)){
            student.removeCourse(this);
            return student;
        }
        return null;
    }

    /**
     * Get all of the currently enrolled students
     * @return Non-{@code null} {@code List} of {@link Student}s, it may be empty.
     */
    @Override
    public List<Student> getEnrolledStudents(){
        return Collections.unmodifiableList(students);
    }

    /**
     *
     * @return a string version of a course which consists of the course name, capacity, course ID, course dept.
     *          and the students enrolled in the course
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Name", getName())
                .add("Capacity", this.maxStudents)
                .add("Code", getCourseCode())
                .add("Department", (Objects.equals(dpt,null))? null :dpt.getName())
                .add("Students", students.stream().map(Student::getID).collect(Collectors.toList()))
                .toString();
    }

    /**
     *
     * @param o the object that we are comparing it to
     * @return true if they have the same course ID as it is the unique factor in the course, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealCourse that = (RealCourse) o;
        if (courseID != null ? !courseID.equals(that.courseID) : that.courseID != null) return false;
        return true;
    }
}
