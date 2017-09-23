package ca.uwo.eng.se2205b.lab2.model;

/**
 * Created by musda on 2/7/2017.
 */
public class CourseMaxCapacityStoreException extends RuntimeException {
    Student studentToBeAdded;
    Course course;

    public Student getStudentToBeAdded(){
        return studentToBeAdded;
    }
    public Course getCourse(){ return course; }

    public CourseMaxCapacityStoreException(Course enrollInCourse, Student studentEnroll) {
        super(enrollInCourse.getName() + " can not store " + studentEnroll.getFirstName() + ", maximum capacity reached.");
        studentToBeAdded = studentEnroll;
        course = enrollInCourse;
    }
}
