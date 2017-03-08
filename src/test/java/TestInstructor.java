import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

/**
 * Created by Bryan1 on 2/28/17.
 */
public class TestInstructor {

    private IInstructor iInstructor;
    private IAdmin iAdmin;
    private IStudent iStudent;

    @Before
    public void setupInstructor() {

        this.iAdmin = new Admin();
        this.iInstructor = new Instructor();
        this.iStudent = new Student();

        this.iAdmin.createClass("Class", 2017, "Instructor", 50);

        // add a student to the class for base testing purposes. (Assumption made that this works. )
        this.iStudent.registerForClass("Student", "Class", 2017);

    } // set up admin + instructor

    @Test
    public void testAddHomework() {

        this.iInstructor.addHomework("Instructor", "Class", 2017, "program1", "Very first program");
        assertTrue(this.iInstructor.homeworkExists("Class", 2017, "program1"));

    } // addHomework(), standard test

    @Test
    public void testAddHomework2() {

        this.iAdmin.createClass("Class2", 2017, "Instructor2", 50);
        this.iInstructor.addHomework("Instructor2", "Class", 2017, "program1", "very first program");

        // make sure that this homework assignment doesn't exist with Class2
        assertFalse(this.iInstructor.homeworkExists("Class", 2017, "program1"));

    } // addHomework(), with instructor that doesn't belong to a class

    @Test
    public void testAddHomework2_1() {

        this.iAdmin.createClass("Class2", 2017, "Instructor2", 50);
        this.iInstructor.addHomework("Instructor2", "Class", 2017, "program1", "very first program");

        // make sure that this homework assignment doesn't exist with Class2
        assertFalse(this.iInstructor.homeworkExists("Class2", 2017, "program1"));

    } // addHomework(), duplicate of testAddHomework2_1() to check if the homework exists in Class2

    @Test
    public void testAddHomework3() {

        this.iAdmin.createClass("Class2", 3000, "Instructor2", 50);
        this.iInstructor.addHomework("Instructor2", "Class2", 3000, "program1", "very first program");

        assertTrue(this.iInstructor.homeworkExists("Class2", 3000, "program1"));

    } // addHomework(), adds homework to a class from the future

    @Test
    public void testAssignGrade() {

        this.iInstructor.addHomework("Instructor", "Class", 2017, "program1", "Very first program");
        this.iInstructor.assignGrade("Instructor", "Class", 2017, "program1", "Student", 50);

        assertEquals(Math.toIntExact(this.iInstructor.getGrade("Class", 2017, "program1", "Student")), 50);

    } // assignGrade(), standard usage

    @Test
    public void testAssignGrade2() {

        // add Student2 to some unrelated class
        IStudent Student2 = new Student();
        this.iAdmin.createClass("Class2", 2017, "Instructor2", 100);
        Student2.registerForClass("Student2", "Class2", 2017);

        this.iInstructor.addHomework("Instructor", "Class", 2017, "program1", "Very first program");
        this.iInstructor.assignGrade("Instructor", "Class", 2017, "program1", "Student2", 50);

        assertNull(this.iInstructor.getGrade("Class", 2017, "program1", "Student2"));
    } // assignGrade(), tests assignment of grade to a non-registered student

    @Test
    public void testAssignGrade3() {

        this.iInstructor.addHomework("Instructor", "Class", 2017, "program1", "Very first program");
        this.iInstructor.assignGrade("Instructor", "Class", 2017, "program1", "Student", -50);

        assertNull(this.iInstructor.getGrade("Class", 2017, "program1", "Student"));

    } // assignGrade(), test assignment of negative grade percentage (shouldn't be possible)


    @Test
    public void testAssignGrade4() {

        this.iInstructor.addHomework("Instructor", "Class", 2017, "program1", "Very first program");
        this.iInstructor.assignGrade("Instructor", "Class", 2017, "program1", "Student", 100);

        assertEquals(Math.toIntExact(this.iInstructor.getGrade("Class", 2017, "program1", "Student")), 100);

    } // assignGrade(), test assignment of 100% to an assignment

    @Test
    public void testAssignGrade5() {

        this.iInstructor.addHomework("Instructor", "Class", 2017, "program1", "Very first program");
        this.iInstructor.assignGrade("Instructor", "Class", 2017, "program1", "Student", 150);

        assertEquals(Math.toIntExact(this.iInstructor.getGrade("Class", 2017, "program1", "Student")), 150);


    } // assignGrade(), extra credit!! Tests assignment of 100+ % to an assignment



}
