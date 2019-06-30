//package dao;
//import models.Staff;
//import org.sql2o.*;
//import org.junit.*;
//import static org.junit.Assert.*;
//
//public class Sql2OStaffDaoTest {
//    private Sql2OStaffDao employeeDao; //ignore me for now. We'll create this soon.
//    private Connection conn; //must be sql2o class conn
//
//    @Before
//    public void setUp() throws Exception {
//        String connectionString = "jdbc:h2:mem:test;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
//        Sql2o sql2o = new Sql2o(connectionString, "", "");
//        employeeDao = new Sql2OStaffDao(sql2o); //ignore me for now
//        conn = sql2o.open(); //keep connection open through entire test so it does not get erased
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        conn.close();
//    }
//
//    @Test
//    public void addingEmployeeSetsId() throws Exception {
//        Staff staff = setupNewEmployee();
//        int originalEmployeeId = staff.getId();
//        employeeDao.add(staff);
//        assertNotEquals(originalEmployeeId, staff.getId()); //how does this work?
//    }
//
//    @Test
//    public void existingEmployeesCanBeFoundById() throws Exception {
//        Staff staff = setupNewEmployee();
//        employeeDao.add(staff); //add to dao (takes care of saving)
//        Staff foundStaff = employeeDao.findById(staff.getId()); //retrieve
//        assertEquals(staff, foundStaff); //should be the same
//    }
//
//    @Test
//    public void addedEmployeesAreReturnedFromgetAll() throws Exception {
//        Staff staff = setupNewEmployee();
//        employeeDao.add(staff);
//        assertEquals(1, employeeDao.getAll().size());
//    }
//
//    @Test
//    public void noEmployeesReturnsEmptyList() throws Exception {
//        assertEquals(0, employeeDao.getAll().size());
//    }
//
//    @Test
//    public void updateChangesEmployeeContent() throws Exception {
//        String initialDescription = "elvis";
//        Staff staff = new Staff(initialDescription, "ek213", 1);
//        employeeDao.add(staff);
//        employeeDao.update("elvis","ek213",1);
//        Staff updatedStaff = employeeDao.findById(staff.getId()); //why do I need to refind this?
//        assertNotEquals(initialDescription, updatedStaff.getEmployee_name());
//    }
//
//    @Test
//    public void deleteByIdDeletesCorrectEmployee() throws Exception {
//        Staff staff = setupNewEmployee();
//        employeeDao.add(staff);
//        employeeDao.deleteById(staff.getId());
//        assertEquals(0, employeeDao.getAll().size());
//    }
//
//    @Test
//    public void clearAllClearsAll() throws Exception {
//        Staff staff = setupNewEmployee();
//        Staff otherStaff = new Staff("elvis", "ek213",2);
//        employeeDao.add(staff);
//        employeeDao.add(otherStaff);
//        int daoSize = employeeDao.getAll().size();
//        employeeDao.clearAllEmployees();
//        assertTrue(daoSize > 0 && daoSize > employeeDao.getAll().size()); //this is a little overcomplicated, but illustrates well how we might use `assertTrue` in a different way.
//    }
//
//    @Test
//    public void departmentIdIsReturnedCorrectly() throws Exception {
//        Staff staff = setupNewEmployee();
//        int originalCatId = staff.getDepartmentid();
//        employeeDao.add(staff);
//        assertEquals(originalCatId, employeeDao.findById(staff.getId()).getDepartmentid());
//    }
//
//    public Staff setupNewEmployee(){
//        return new Staff("elvis", "ek123", 2);
//    }
//}