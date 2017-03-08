import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by Bryan1 on 2/28/17.
 */
public class TestStudent {

    private IStudent iStudent;
    private IAdmin iAdmin;
    private IInstructor iInstructor;

    @Before
    public void setupStudent() {

        iStudent = new Student();
        iAdmin = new Admin();
        iInstructor = new Instructor();

        iAdmin.createClass("Class", 2017, "Instructor", 3);

    } // set up environment

    @Test
    public void testRegisterForClass() {

        this.iStudent.registerForClass("Student", "Class", 2017);
        assertTrue(this.iStudent.isRegisteredFor("Student", "Class", 2017));

    } // registerForClass(), standard usage

    @Test
    public void testRegisterForClass2() {

        this.iStudent.registerForClass("Student1", "Class", 2017);
        this.iStudent.registerForClass("Student2", "Class", 2017);
        this.iStudent.registerForClass("Student3", "Class", 2017);
        this.iStudent.registerForClass("Student4", "Class", 2017);

        assertFalse(this.iStudent.isRegisteredFor("Student4", "Class", 2017));

    } // registerForClass(), tests when too many students try to register for a class (with capacity of 3)


    @Test
    public void testRegisterForClass3() {

        this.iStudent.registerForClass("Student", "Class2", 2017);

        assertFalse(this.iStudent.isRegisteredFor("Student2", "Class2", 2017));

    } // registerForClass(), registers a student for a non-existent class

    @Test
    public void testRegisterForClass4() {

        this.iAdmin.createClass("Class2", 3000, "Instructor2", 50);

        this.iStudent.registerForClass("Student", "Class2", 3000);

        assertFalse(this.iStudent.isRegisteredFor("Student", "Class2", 3000));

    } // registerForClass(), registers for a class from the future

    @Test
    public void testDropClass() {

        this.iStudent.registerForClass("Student", "Class", 2017);
        this.iStudent.dropClass("Student", "Class", 2017);

        assertFalse(this.iStudent.isRegisteredFor("Student", "Class", 2017));

    } // dropClass(), standard usage

    @Test
    public void testDropClass1() {

        this.iAdmin.createClass("Class2", 2017, "Instructor2", 3);
        this.iStudent.registerForClass("Student", "Class", 2017);

        this.iStudent.dropClass("Student", "Class2", 2017);

        assertTrue(this.iStudent.isRegisteredFor("Student", "Class", 2017));

    } // dropClass(), tests making a student drop a class he/she is not enrolled for

    @Test
    public void testDropClass2() {

        this.iAdmin.createClass("Class2", 3000, "Instructor2", 3);
        this.iStudent.registerForClass("Student", "Class", 3000);

        this.iStudent.dropClass("Student", "Class2", 3000);

    } // dropClass(), tests to drop a class from the future

    @Test
    public void testSubmitHomework() {

        this.iStudent.registerForClass("Student", "Class", 2017);
        this.iInstructor.addHomework("Instructor", "Class", 2017, "program1", "first program");

        this.iStudent.submitHomework("Student", "program1", "42", "Class", 2017);

        assertTrue(this.iStudent.hasSubmitted("Student", "program1", "Class", 2017));

    } // submitHomework(), standard usage

    @Test
    public void testSubmitHomework2() {

        this.iAdmin.createClass("Class2", 2017, "Instructor2", 5);
        this.iStudent.registerForClass("Student", "Class", 2017);

        this.iInstructor.addHomework("Instructor", "Class", 2017, "program1", "first program");
        this.iInstructor.addHomework("Instructor2", "Class2", 2017, "essay1", "first essay");

        this.iStudent.submitHomework("Student", "essay1", "the", "Class2", 2017);

        assertFalse(this.iStudent.hasSubmitted("Student", "essay1", "Class2", 2017));


    } // submitHomework(), submit homework to another class

    @Test
    public void testSubmitHomework3() {

        this.iAdmin.createClass("Class2", 2017, "Instructor2", 5);
        this.iStudent.registerForClass("Student", "Class", 2017);

        this.iInstructor.addHomework("Instructor", "Class", 2017, "program1", "first program");
        this.iInstructor.addHomework("Instructor2", "Class2", 2017, "essay1", "first essay");

        this.iStudent.submitHomework("Student", "essay1", "the", "Class", 2017);

        assertFalse(this.iStudent.hasSubmitted("Student", "essay1", "Class", 2017));

    }  // submitHomework(), submit homework from another class

    @Test
    public void testSubmitHomework4() {

        this.iAdmin.createClass("Class2", 3000, "Instructor2", 5);
        this.iInstructor.addHomework("Instructor2", "Class2", 3000, "program1", "first program");

        this.iStudent.registerForClass("Student", "Class2", 3000);

        this.iStudent.submitHomework("Student", "program1", "42", "Class2", 3000);

        assertFalse(this.iStudent.hasSubmitted("Student", "program1", "Class2", 3000));

    } // submitHomework(), submit homework to a class from the future
}
