
package models;

import org.junit.Test;
import static org.junit.Assert.*;

public class DepartmentTest {

    @Test
    public void NewDepartmentObjectGetsCorrectlyCreated_true() throws Exception {
        Department department = setupNewDepartment();
        assertEquals(true, department instanceof Department);
    }

    @Test
    public void DepartmentInstantiatesWithName_school() throws Exception {
        Department department = setupNewDepartment();
        assertEquals("school", department.getName());
    }

    //helper methods
    public Department setupNewDepartment(){
        return new Department("school","hello");
    }
}