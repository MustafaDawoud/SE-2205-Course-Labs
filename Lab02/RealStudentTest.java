package ca.uwo.eng.se2205b.lab2.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by musda on 2/7/2017.
 */
public class RealStudentTest {

    @Test
    public void setFirstNameNormalInput() {
        Student s1 = new RealStudent("bob", "long", 11111);
        s1.setFirstName("Bob");
        assertEquals("Bob", s1.getFirstName());
    }

    @Test
    public void setFirstNameNullInput() {
        Student s1 = new RealStudent("bob", "long", 11111);
        try{
            s1.setFirstName(null);
        }catch(IllegalArgumentException e){
            assertEquals("Argument for @Nonnull parameter 'fName' of ca/uwo/eng/se2205b/lab2/model/RealStudent.setFirstName must not be null", e.getMessage());
        }
    }

    @Test
    public void setLastNameNormalInput() {
        Student s1 = new RealStudent("bob", "long", 11111);
        s1.setLastName("Bob");
        assertEquals("Bob", s1.getLastName());
        try{
            s1.setLastName(null);
        }catch(IllegalArgumentException e){
            assertEquals("Argument for @Nonnull parameter 'lName' of ca/uwo/eng/se2205b/lab2/model/RealStudent.setLastName must not be null", e.getMessage());
        }
    }

    @Test
    public void setLastNameNullInput() {
        Student s1 = new RealStudent("bob", "long", 11111);
        try{
            s1.setLastName(null);
        }catch(IllegalArgumentException e){
            assertEquals("Argument for @Nonnull parameter 'lName' of ca/uwo/eng/se2205b/lab2/model/RealStudent.setLastName must not be null", e.getMessage());
        }
    }

    @Test
    public void setIDTesterWrongInput() {
        Student s1 = new RealStudent("bob", "long", 11111);
        try{
            s1.setID(0);
        }catch(IllegalArgumentException e){
            assertEquals(11111, s1.getID());
        }
    }

    @Test
    public void setIDTesterNormalInput() {
        Student s1 = new RealStudent("bob", "long", 11111);
        s1.setID(1111);
        assertEquals(1111, s1.getID());
        long temp = 0;
        try{
            s1.setID(temp);
        }catch(IllegalArgumentException e){
            assertEquals(1111, s1.getID());
        }
    }

    @Test
    public void addCourseTesterNullInput() {
        Student s1 = new RealStudent("bob", "long", 11111);
        Course temp = null;
        try{
            s1.addCourse(temp);
        }catch(IllegalArgumentException e){
            assertEquals("Argument for @Nonnull parameter 'course' of ca/uwo/eng/se2205b/lab2/model/RealStudent.addCourse must not be null", e.getMessage());
        }
    }

    @Test
    public void addCourseNormal() {
        Student s1 = new RealStudent("bob", "long", 11111);
        Course c = new RealCourse("Random", "AM1231", 10);
        s1.addCourse(c);
        assertTrue(s1.getCourses().contains(c));
    }

    @Test
    public void removeCourseTesterNullInput() {
        Student s1 = new RealStudent("bob", "long", 11111);
        try{
            s1.removeCourse(null);
        }catch(IllegalArgumentException e){
            assertEquals("Argument for @Nonnull parameter 'course' of ca/uwo/eng/se2205b/lab2/model/RealStudent.removeCourse must not be null", e.getMessage());
        }
    }

    @Test
    public void removeCourseTesterCourseThatDoesNotExist() {
        Student s1 = new RealStudent("bob", "long", 11111);
        Course c = new RealCourse("Random", "AM1231", 10);
        s1.removeCourse(c);
    }

    @Test
    public void removeCourseTesterNormalRemove() {
        Student s1 = new RealStudent("bob", "long", 11111);
        Course c = new RealCourse("Random", "AM1231", 10);
        s1.addCourse(c);
        s1.removeCourse(c);
        assertFalse(s1.getCourses().contains(c));
    }

    @Test
    public void setDepartmentTester() {
        Student s1 = new RealStudent("bob", "long", 11111);
        Department dp = new RealDepartment("ECE");
        s1.setDepartment(dp);
        assertEquals("ECE", s1.getDepartment().getName());
    }

    @Test
    public void toStringTester(){
        Department CEE = new RealDepartment("CEE");
        Department ECE = new RealDepartment("ECE");
        Department AM = new RealDepartment("AM");

        Course AM1413 = new RealCourse("Calculus 1", "AM1413", 10);
        Course ES1022 = new RealCourse("Statics", "ES1022", 10);
        Course ES1036 = new RealCourse("Intro to Programming", "ES1036", 10);
        Course SE2205 = new RealCourse("Data Structures and Algorithms", "SE2205", 10);

        Student s1 = new RealStudent("John","Smith",1111);
        Student s2 = new RealStudent("Sarah","McLachlan",2222);
        Student s3 = new RealStudent("Gene","Wilder",3333);
        Student s4 = new RealStudent("Ron","Weasley",4444);
        Student s5 = new RealStudent("Minh","Pham",5555);
        Student s6 = new RealStudent("George","Takei",6666);

        s1.setDepartment(CEE);
        s1.addCourse(AM1413);
        s1.addCourse(ES1022);

        s2.setDepartment(ECE);
        s2.addCourse(AM1413);
        s2.addCourse(ES1036);
        s2.addCourse(SE2205);

        s3.setDepartment(ECE);
        s3.addCourse(AM1413);
        s3.addCourse(SE2205);

        s4.setDepartment(ECE);
        s4.addCourse(ES1022);
        s4.addCourse(SE2205);

        s5.setDepartment(ECE);
        s5.addCourse(AM1413);
        s5.addCourse(ES1022);

        s6.setDepartment(AM);
        s6.addCourse(AM1413);
        s6.addCourse(SE2205);

        assertEquals("RealStudent{Name=John, Courses=[AM1413, ES1022], Department=CEE, ID=1111}", s1.toString());
    }
}