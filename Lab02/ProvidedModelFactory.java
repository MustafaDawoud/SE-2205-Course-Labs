package ca.uwo.eng.se2205b.lab2.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test fixture used to create a test model for test cases.
 */
public class ProvidedModelFactory {

    /**
     * Creates the model shown in
     * <a href="https://uwoece-se2205b-2017.github.io/labs/02-oop-serialization#question-0">Q1 Deliverable</a>.
     * @return List of Department values
     */
    public static List<Department> createModel() {
        // see Deliverable for Q1
        Department ECE = new RealDepartment("Electrical And Computer Engineering");
        Department AM = new RealDepartment("Applied Math");
        Department CEE = new RealDepartment("Civil and Environmental Engineering");

        List<Department> model = new ArrayList<>(3);
        model.add(CEE);
        model.add(ECE);
        model.add(AM);

        Course AM1413 = new RealCourse("Calculus", "AM1413", model.get(2), 6);
        Course ES1022 = new RealCourse("Statics", "ES1022", model.get(0), 6);
        Course ES1036 = new RealCourse("Programming Fundamentals I", "ES1036", model.get(1),5);
        Course SE2205 = new RealCourse("Data Structures and Algorithms", "SE2205", model.get(1),10);

        Student s1 = new RealStudent("John","Smith",1111);
        Student s2 = new RealStudent("Sarah","McLachlan",2222);
        Student s3 = new RealStudent("Gene","Wilder",3333);
        Student s4 = new RealStudent("Ron","Weasley",4444);
        Student s5 = new RealStudent("Minh","Pham",5555);
        Student s6 = new RealStudent("George","Takei",6666);
        Student s7 = new RealStudent("Ralph","Nader",7777);
        Student s8 = new RealStudent("Jane","Tarzan",8888);

        s1.setDepartment(model.get(0));
        s1.addCourse(AM1413);
        s1.addCourse(ES1022);

        s2.setDepartment(model.get(1));
        s2.addCourse(AM1413);
        s2.addCourse(ES1036);
        s2.addCourse(SE2205);

        s3.setDepartment(model.get(1));
        s3.addCourse(AM1413);
        s3.addCourse(SE2205);

        s4.setDepartment(model.get(1));
        s4.addCourse(ES1022);
        s4.addCourse(SE2205);

        s5.setDepartment(model.get(1));
        s5.addCourse(AM1413);
        s5.addCourse(ES1022);

        s6.setDepartment(model.get(2));
        s6.addCourse(AM1413);
        s6.addCourse(SE2205);

        s7.setDepartment(model.get(2));
        s7.addCourse(AM1413);
        s7.addCourse(ES1022);
        s7.addCourse(ES1036);
        s7.addCourse(SE2205);

        s8.setDepartment(model.get(1));

        return model;
    }

    @Test
    public void CheckingCoursesInDepartment() {
        assertEquals("ES1022", createModel().get(0).getCourses().get(0).getCourseCode());
        assertEquals("ES1036", createModel().get(1).getCourses().get(0).getCourseCode());
        assertEquals("SE2205", createModel().get(1).getCourses().get(1).getCourseCode());
        assertEquals("AM1413", createModel().get(2).getCourses().get(0).getCourseCode());
    }

    @Test
    public void CheckingStudentID() {
        assertEquals(1111, createModel().get(0).getEnrolledStudents().get(0).getID());
        assertEquals(2222, createModel().get(1).getEnrolledStudents().get(0).getID());
        assertEquals(3333, createModel().get(1).getEnrolledStudents().get(1).getID());
        assertEquals(4444, createModel().get(1).getEnrolledStudents().get(2).getID());
        assertEquals(5555, createModel().get(1).getEnrolledStudents().get(3).getID());
        assertEquals(8888, createModel().get(1).getEnrolledStudents().get(4).getID());
        assertEquals(6666, createModel().get(2).getEnrolledStudents().get(0).getID());
        assertEquals(7777, createModel().get(2).getEnrolledStudents().get(1).getID());
    }

    @Test
    public void RemoveStudentFromCourseNotEnrolledIn() {
        List<Department> modelList = createModel();

        modelList.get(0).getEnrolledStudents().get(0).removeCourse(modelList.get(0).getCourses().get(0));
        assertFalse(modelList.get(0).getEnrolledStudents().get(0).getCourses().contains(createModel().get(0).getCourses().get(0)));
        modelList.get(0).getEnrolledStudents().get(0).addCourse(modelList.get(0).getCourses().get(0));
        assertTrue(modelList.get(0).getEnrolledStudents().get(0).getCourses().contains(createModel().get(0).getCourses().get(0)));
    }

    @Test
    public void testRemovingACourse() {
        List<Department> modelList = createModel();

        modelList.get(0).getCourses().get(0).removeStudent(modelList.get(0).getEnrolledStudents().get(0));
        assertFalse(modelList.get(0).getCourses().get(0).getEnrolledStudents().contains(createModel().get(0).getEnrolledStudents().get(0)));
        modelList.get(0).getCourses().get(0).enrollStudent(modelList.get(0).getEnrolledStudents().get(0));
        assertTrue(modelList.get(0).getCourses().get(0).getEnrolledStudents().contains(createModel().get(0).getEnrolledStudents().get(0)));
    }

    @Test
    public void AddTooManyCourses() {
        List<Department> modelList = createModel();
        Course SE2250 = new RealCourse("Testing Course Using Unity", "SE2250", 0);
        Student s = new RealStudent("Hans", "Smith", 1123);
        try{
            SE2250.enrollStudent(s);
        }catch(CourseMaxCapacityStoreException e){
            assertEquals(SE2250.getName() + " can not store " + s.getFirstName() + ", maximum capacity reached.", e.getMessage());
        }
    }

    @Test
    public void DuplicateStudentDept(){
        List<Department> model = createModel();
        model.get(0).getEnrolledStudents().get(0).setDepartment(model.get(0));
        model.get(0).getEnrolledStudents().get(0).setDepartment(model.get(0));
        assertEquals("RealDepartment{Name=Civil and Environmental Engineering, Students=[1111], Course=[ES1022]}" ,model.get(0).toString());
    }

    @Test
    public void removstudentfromDepartment(){
        List<Department> model = createModel();
        Student temp = model.get(0).getEnrolledStudents().get(0);
        model.get(0).removeStudent(model.get(0).getEnrolledStudents().get(0));
        assertEquals("RealDepartment{Name=Civil and Environmental Engineering, Students=[], Course=[ES1022]}", model.get(0).toString());
        model.get(0).enrollStudent(temp);
    }

    @Test
    public void doubleDeptAdd(){
        List<Department> model = createModel();
        model.get(0).getEnrolledStudents().get(0).setDepartment(model.get(0));
        model.get(0).getEnrolledStudents().get(0).setDepartment(model.get(0));
        assertEquals("RealDepartment{Name=Civil and Environmental Engineering, Students=[1111], Course=[ES1022]}" ,model.get(0).toString());
    }
}

