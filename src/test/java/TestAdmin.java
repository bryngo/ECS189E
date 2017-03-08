import api.IAdmin;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Bryan1 on 2/28/17.
 */

public class TestAdmin {

    private IAdmin iAdmin;
    private IStudent iStudent;

    @Before
    public void setupAdmin() {

        this.iAdmin = new Admin();
        this.iStudent = new Student();

        this.iAdmin.createClass("Class", 2017, "Instructor", 50);
    } // we will use this base "Class" object to do all of our testing.

    @Test
    public void testCreateClass() {

        this.iAdmin.createClass("Class2", 2017, "Instructor", 50);
        assertFalse(this.iAdmin.classExists("Class2", 2017));

    } // CreateClass(), attempt to give an instructor two classes in the same year

    @Test
    public void testCreateClass2() {

        this.iAdmin.createClass("Class2", 2017, "Instructor2", -1);
        assertFalse(this.iAdmin.classExists("Class2", 2017));

    } // CreateClass(), attempts to create a class starting with negative capacity

    @Test
    public void testCreateClass3() {

        this.iAdmin.createClass("Class2", 2015, "Instructor2", 50);
        assertFalse(this.iAdmin.classExists("Class2", 2015));

    } // CreateClass(), attempts to create a class in the past


    @Test
    public void testCreateClass4() {

        this.iAdmin.createClass("Class2", -1, "Instructor2", 50);
        assertFalse(this.iAdmin.classExists("Class2", -1));

    } // CreateClass(), attempts to create a class in the negative past

    @Test
    public void testCreateClass5() {

        this.iAdmin.createClass("Class2", 2017, "Instructor2", 50);
        assertTrue(this.iAdmin.classExists("Class2", 2017));

    } // CreateClass() standard usage

    @Test
    public void testCreateClass6() {

        this.iAdmin.createClass("Class2", -1, "Instructor2", 50);
        assertFalse(this.iAdmin.classExists("Class2", -1));

    } // CreateClass() with attempt to create a class of a negative year

    @Test
    public void testCreateClass7() {

        this.iAdmin.createClass("Class2", 2017, "Instructor2", 0);
        assertFalse(this.iAdmin.classExists("Class2", 2017));

    } // CreateClass() with attempt to create a class of 0 capacity

    @Test
    public void testCreateClass8() {

        this.iAdmin.createClass("Class2", 2018, "Instructor", 50);
        assertTrue(this.iAdmin.classExists("Class2", 2018));

    } // CreateClass() with attempt to assign professor to classes of different years

    @Test
    public void testChangeClassCapacity() {

        this.iAdmin.changeCapacity("Class", 2017, 55);
        assertEquals(this.iAdmin.getClassCapacity("Class", 2017), 55);

    } // ChangeClassCapacity() with normal input

    @Test
    public void testChangeClassCapacity2() {

        this.iAdmin.changeCapacity("Class", 2017, 45);
        assertEquals(this.iAdmin.getClassCapacity("Class", 2017), 50);

    } // ChangeClassCapacity() with attempt to lower input to a lower positive number

    @Test
    public void testChangeClassCpacity3() {

        this.iAdmin.changeCapacity("Class", 2017, -1);
        assertEquals(this.iAdmin.getClassCapacity("Class", 2017), 50);

    } // ChangeClassCapacity() with attempt to lower it to a negative number

    @Test
    public void testChangeClassCapacity4() {

        this.iAdmin.changeCapacity("Class", 2017, 50);
        assertEquals(this.iAdmin.getClassCapacity("Class", 2017), 50);

    } // ChangeClassCapacity() with attempt to change capacity to the same number

    @Test
    public void testChangeClassCapacity5() {

        this.iAdmin.createClass("Class2", 2017, "Instructor2", 3);

        iStudent.registerForClass("student1", "Class2", 2017);
        iStudent.registerForClass("student2", "Class2", 2017);
        iStudent.registerForClass("student3", "Class2", 2017);

        this.iAdmin.changeCapacity("Class2", 2017, 4);

        iStudent.registerForClass("student4", "Class2", 2017);

        assertTrue(iStudent.isRegisteredFor("student4", "Class2", 2017));


    } // ChangeClassCapacity() with enrolled students

    @Test
    public void testChangeCapacity6() {

        this.iAdmin.createClass("Class2", 2017, "Instructor2", 3);

        iStudent.registerForClass("student1", "Class2", 2017);
        iStudent.registerForClass("student2", "Class2", 2017);
        iStudent.registerForClass("student3", "Class2", 2017);

        this.iAdmin.changeCapacity("Class2", 2017, 2);

        assertEquals(this.iAdmin.getClassCapacity("Class2", 2017), 3);

    } // ChangeClassCapacity() with attempt to lower capacity to < # of students enrolled

}