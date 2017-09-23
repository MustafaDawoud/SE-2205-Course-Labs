package ca.uwo.eng.se2205b.lab2.model;

import org.junit.Test;
import org.junit.Assert;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the {@link Department} implementation.
 */
public class RealDepartmentTest {

    /**
     * Test the name property
     */
    @Test
    public void name() {
        Department dpt = new RealDepartment("ECE");
        dpt.setName("AM");
        assertEquals("AM", dpt.getName());
    }

    @Test
    public void nameNull() {
        Department dpt = new RealDepartment("ECE");
        try{
            dpt.setName(null);
        }catch(IllegalArgumentException e){
            assertEquals("Argument for @Nonnull parameter 'name' of ca/uwo/eng/se2205b/lab2/model/RealDepartment.setName must not be null", e.getMessage());
        }
    }

    /**
     * Test course changes
     */
    @Test
    public void coursesNormalDepartment() {
        Course c1 = new RealCourse("Calc", "AM1413", 2);
        Department d1 = new RealDepartment("AM");
        c1.setDepartment(d1);
        assertTrue(d1.getCourses().contains(c1));
    }

    @Test
    public void coursesChangeOldDpt() {
        Course c1 = new RealCourse("Calc", "AM1413", 2);
        Department d1 = new RealDepartment("AM");
        Department d2 = new RealDepartment("ECE");
        c1.setDepartment(d1);
        c1.setDepartment(d2);
        assertTrue(!(d1.getCourses().contains(c1)));
        assertTrue(d2.getCourses().contains(c1));
    }

    /**
     * Test student changes
     */
    @Test
    public void students() {
        Student s1 = new RealStudent("bob", "long", 11111);
        Department d1 = new RealDepartment("AM");
        s1.setDepartment(d1);
        assertTrue(d1.getEnrolledStudents().contains(s1));
    }

    @Test
    public void studentsEdgeCase() {
        Student s1 = new RealStudent("bob", "long", 11111);
        Department d1 = new RealDepartment("AM");
        Department d2 = new RealDepartment("ECE");
        s1.setDepartment(d1);
        s1.setDepartment(d2);
        assertTrue(!(d1.getEnrolledStudents().contains(s1)));
        assertTrue(d2.getEnrolledStudents().contains(s1));
    }

    @Test
    public void studentsNullCase() {
        Student s1 = new RealStudent("bob", "long", 11111);
        Department d1 = new RealDepartment("AM");
        s1.setDepartment(d1);
        s1.setDepartment(null);
        assertTrue(!d1.getEnrolledStudents().contains(s1));
    }

    @Test
    public void TesttoString() {
        Department CEE = new RealDepartment("CEE");
        Department ECE = new RealDepartment("ECE");
        Department AM = new RealDepartment("AM");

        Course AM1413 = new RealCourse("Calculus 1", "AM1413", 10);
        AM1413.setDepartment(AM);
        Course ES1022 = new RealCourse("Statics", "ES1022", 10);
        ES1022.setDepartment(CEE);
        Course ES1036 = new RealCourse("Intro to Programming", "ES1036", 10);
        ES1036.setDepartment(ECE);
        Course SE2205 = new RealCourse("Data Structures and Algorithms", "SE2205", 10);
        SE2205.setDepartment(ECE);

        Student s1 = new RealStudent("John","Smith",1111);
        Student s2 = new RealStudent("Sarah","McLachlan",2222);
        Student s3 = new RealStudent("Gene","Wilder",3333);
        Student s4 = new RealStudent("Ron","Weasley",4444);
        Student s5 = new RealStudent("Minh","Pham",5555);
        Student s6 = new RealStudent("George","Takei",6666);
        Student s7 = new RealStudent("Ralph","Nader",7777);
        Student s8 = new RealStudent("Jane","Tarzan",8888);

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

        assertEquals("RealDepartment{Name=CEE, Students=[1111], Course=[ES1022]}", CEE.toString());

    }

}