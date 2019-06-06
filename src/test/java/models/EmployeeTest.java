package models;

import org.junit.Test;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class EmployeeTest {
    @Test
    public void NewEmployeeObjectGetsCorrectlyCreated_true() throws Exception {
        Employee employee = setupNewEmployee();
        assertEquals(true, employee instanceof Employee);
    }

    @Test
    public void EmployeeInstantiatesWithDescription_true() throws Exception {
        Employee employee = setupNewEmployee();
        assertEquals("elvis", employee.getEmployee_name());
    }

    @Test
    public void isCompletedPropertyIsFalseAfterInstantiation() throws Exception {
        Employee employee = setupNewEmployee();
        assertEquals(false, employee.isCompleted()); //should never start as completed
    }

//    @Test
//    public void getCreatedAtInstantiatesWithCurrentTimeToday() throws Exception {
//        Employee employee = setupNewEmployee();
//        assertEquals(LocalDateTime.now().getDayOfWeek(), employee.getCreatedAt().getDayOfWeek());
//    }

    //helper methods
    public Employee setupNewEmployee(){
        return new Employee("elvis", "ek219", 1);
    }
}
