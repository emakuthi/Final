//package models;
//
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class StaffTest {
//    @Test
//    public void NewEmployeeObjectGetsCorrectlyCreated_true() throws Exception {
//        Staff staff = setupNewEmployee();
//        assertEquals(true, staff instanceof Staff);
//    }
//
//    @Test
//    public void EmployeeInstantiatesWithDescription_true() throws Exception {
//        Staff staff = setupNewEmployee();
//        assertEquals("elvis", staff.getEmployee_name());
//    }
//
//    @Test
//    public void isCompletedPropertyIsFalseAfterInstantiation() throws Exception {
//        Staff staff = setupNewEmployee();
//        assertEquals(false, staff.isCompleted()); //should never start as completed
//    }
//
////    @Test
////    public void getCreatedAtInstantiatesWithCurrentTimeToday() throws Exception {
////        Staff employee = setupNewEmployee();
////        assertEquals(LocalDateTime.now().getDayOfWeek(), employee.getCreatedAt().getDayOfWeek());
////    }
//
//    //helper methods
//    public Staff setupNewEmployee(){
//        return new Staff("elvis", "ek219", 1);
//    }
//}
