package ca.uwo.eng.se2205b.lab2.model;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the {@link Course} implementation
 */
public class RealCourseTest {

    /**
     * Test the name property
     */
    @Test
    public void name() {
        Course c = new RealCourse("Random", "AM1231", 10);
        c.setName("SE2205");
        assertEquals("SE2205", c.getName());
        try{
            c.setName(null);
        }catch(IllegalArgumentException e){
            assertEquals("Argument for @Nonnull parameter 'name' of ca/uwo/eng/se2205b/lab2/model/RealCourse.setName must not be null", e.getMessage());
        }
    }

    @Test
    public void nameNull() {
        Course c = new RealCourse("Random", "AM1231", 10);
        try{
            c.setName(null);
        }catch(IllegalArgumentException e){
            assertEquals("Argument for @Nonnull parameter 'name' of ca/uwo/eng/se2205b/lab2/model/RealCourse.setName must not be null", e.getMessage());
        }
    }

    /**
     * Test department interactions
     */
    @Test
    public void department() {
        Course AM1413 = new RealCourse("Calculus 1", "AM1413", 10);
        Department AM = new RealDepartment("AM");
        AM1413.setDepartment(AM);
        assertTrue(AM.getCourses().contains(AM1413));
    }

    @Test
    public void setDepartment() {
        Course c = new RealCourse("Random", "AM1231", 10);
        c.setDepartment(new RealDepartment("CEE"));
        assertEquals("CEE", c.getDepartment().getName());
    }

    @Test
    public void DepartmentInteraction() {
        Course c = new RealCourse("Random", "AM1231", 10);
        Department oldDpt = new RealDepartment("AM");
        c.setDepartment(oldDpt);
        c.setDepartment(null);
        assertFalse(oldDpt.getCourses().contains(c));
    }

    /**
     * Test that the maximum occupancy behaves properly
     */
    @Test
    public void maximumOccupancy() {
        Course c = new RealCourse("C1", "AM1413", 2);
        Student s1 = new RealStudent("John","Smith",1111);
        Student s2 = new RealStudent("Sarah","McLachlan",2222);
        Student s3 = new RealStudent("Gene","Wilder",3333);
        c.enrollStudent(s1);
        c.enrollStudent(s2);
        try{
            c.enrollStudent(s3);
        }catch(CourseMaxCapacityStoreException e){
            assertEquals(c.getName() + " can not store " + s3.getFirstName() + ", maximum capacity reached.", e.getMessage());
        }
    }

    @Test
    public void enrollStudent(){
        Course c = new RealCourse("Calc", "AM1413", 1);
        Student s1 = new RealStudent("John","Smith",1111);
        c.enrollStudent(s1);
        assertTrue(s1.getCourses().contains(c));
    }

    @Test
    public void enrollNullStudent(){
        Course c = new RealCourse("Calc", "AM1413", 1);
        try{
            c.enrollStudent(null);
        }catch(IllegalArgumentException e){
            assertEquals("Argument for @Nonnull parameter 'student' of ca/uwo/eng/se2205b/lab2/model/RealCourse.enrollStudent must not be null", e.getMessage());
        }
    }

    /**
     * Test that adding/removing students behaves
     */
    @Test
    public void removeNonExistantStudent() {
        Course c = new RealCourse("Calc", "AM1413", 1);
        try{
            c.removeStudent(null);
        }catch(IllegalArgumentException e){
            assertEquals("Argument for @Nonnull parameter 'student' of ca/uwo/eng/se2205b/lab2/model/RealCourse.removeStudent must not be null", e.getMessage());
        }
    }

    @Test
    public void removeStudent() {
        Course c = new RealCourse("Calc", "AM1413", 1);
        Student s1 = new RealStudent("John","Smith",1111);
        c.removeStudent(s1);
        assertFalse(s1.getCourses().contains(c));
    }

    @Test
    public void TestString() {
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

        assertEquals("RealCourse{Name=Calculus 1, Capacity=10, Code=AM1413, Department=null, Students=[1111, 2222, 3333, 5555, 6666]}", AM1413.toString());

    }
}