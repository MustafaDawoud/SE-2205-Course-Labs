package ca.uwo.eng.se2205b.lab2.model;

import com.google.common.base.MoreObjects;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by musda on 2/7/2017.
 */
class RealDepartment implements Department{
    private String name;
    private ArrayList<Student> students;
    private ArrayList<Course> courses;

    /**
     * Creates a new department.
     * @param name Non-{@code null} or empty name
     * @throws IllegalArgumentException if the name is {@code null} or empty
     */
    public RealDepartment(@Nonnull String name) throws IllegalArgumentException {
        if(name == ""){
            throw new IllegalArgumentException();
        }
        this.name = name;
        students = new ArrayList<>();
        courses = new ArrayList<>();
    }

    /**
     * Get the unique name of the Department
     * @return Non-{@code null} name
     */
    @Override
    public String getName(){ return name; }

    /**
     * Return the unique name of the department.
     * @param name Non-{@code null} name
     * @throws IllegalArgumentException if {@code name} is empty
     */
    @Override
    public void setName(@Nonnull String name) throws IllegalArgumentException{
        if(name == "")
            throw new IllegalArgumentException();
        this.name = name;
    }

    /**
     * Enroll a student in the Department.
     * @param student Non-{@code null} student to add.
     * @throws IllegalArgumentException if student is null
     */
    @Override
    public void enrollStudent(@Nonnull Student student) throws IllegalArgumentException{
        if(students.contains(student))
            return;
        students.add(student);
        student.setDepartment(this);
    }

    /**
     * Remove a {@link Student} from a {@code Department}
     * @param student Removed student
     * @throws IllegalArgumentException if student is null
     * @return The Student instance, if they were removed, otherwise {@code null}.
     */
    @Override
    public Student removeStudent(@Nonnull Student student) throws IllegalArgumentException{
        if(students.contains(student)){
            students.remove(student);
            student.setDepartment(null);
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
     * Adds a course to a department
     * @param course Course to add
     * @throws IllegalArgumentException if course is null
     */
    @Override
    public void addCourse(@Nonnull Course course) throws IllegalArgumentException{
        if(courses.contains(course))
            return;
        courses.add(course);
        course.setDepartment(this);
    }

    /**
     * Removes a course from the {@code Department}
     * @param course Course to remove
     * @throws IllegalArgumentException if course is null
     * @return Instance removed, {@code null} if none are removed
     */
    @Override
    public Course removeCourse(@Nonnull Course course) throws IllegalArgumentException{
        if(courses.contains(course)){
            courses.remove(course);
            course.setDepartment(null);
            return course;
        }
        return null;
    }

    /**
     * Get all of the courses in the Department
     * @return List of all courses, may be empty, but never {@code null}
     */
    @Override
    public List<Course> getCourses(){
        return Collections.unmodifiableList(courses);
    }

    /**
     *
     * @return a string version of the department which consists of the name, the student ID enrolled in the course
     *          and the courseID's in that department
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Name", getName())
                .add("Students" , students.stream().map(Student::getID).collect(Collectors.toList()))
                .add("Course" , courses.stream().map(Course::getCourseCode).collect(Collectors.toList()))
                .toString();
    }

    /**
     *
     * @param o the object that we want to compare it to
     * @return true of they have the same name, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealDepartment that = (RealDepartment) o;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return true;
    }
}
