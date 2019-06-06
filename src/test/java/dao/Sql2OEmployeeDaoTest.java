package dao;
import models.Employee;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class Sql2OEmployeeDaoTest {
    private Sql2OEmployeeDao employeeDao; //ignore me for now. We'll create this soon.
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:test;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        employeeDao = new Sql2OEmployeeDao(sql2o); //ignore me for now
        conn = sql2o.open(); //keep connection open through entire test so it does not get erased
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingEmployeeSetsId() throws Exception {
        Employee employee = setupNewEmployee();
        int originalEmployeeId = employee.getId();
        employeeDao.add(employee);
        assertNotEquals(originalEmployeeId, employee.getId()); //how does this work?
    }

    @Test
    public void existingEmployeesCanBeFoundById() throws Exception {
        Employee employee = setupNewEmployee();
        employeeDao.add(employee); //add to dao (takes care of saving)
        Employee foundEmployee = employeeDao.findById(employee.getId()); //retrieve
        assertEquals(employee, foundEmployee); //should be the same
    }

    @Test
    public void addedEmployeesAreReturnedFromgetAll() throws Exception {
        Employee employee = setupNewEmployee();
        employeeDao.add(employee);
        assertEquals(1, employeeDao.getAll().size());
    }

    @Test
    public void noEmployeesReturnsEmptyList() throws Exception {
        assertEquals(0, employeeDao.getAll().size());
    }

    @Test
    public void updateChangesEmployeeContent() throws Exception {
        String initialDescription = "elvis";
        Employee employee = new Employee(initialDescription, "ek213", 1);
        employeeDao.add(employee);
        employeeDao.update("elvis","ek213",1);
        Employee updatedEmployee = employeeDao.findById(employee.getId()); //why do I need to refind this?
        assertNotEquals(initialDescription, updatedEmployee.getEmployee_name());
    }

    @Test
    public void deleteByIdDeletesCorrectEmployee() throws Exception {
        Employee employee = setupNewEmployee();
        employeeDao.add(employee);
        employeeDao.deleteById(employee.getId());
        assertEquals(0, employeeDao.getAll().size());
    }

    @Test
    public void clearAllClearsAll() throws Exception {
        Employee employee = setupNewEmployee();
        Employee otherEmployee = new Employee("elvis", "ek213",2);
        employeeDao.add(employee);
        employeeDao.add(otherEmployee);
        int daoSize = employeeDao.getAll().size();
        employeeDao.clearAllEmployees();
        assertTrue(daoSize > 0 && daoSize > employeeDao.getAll().size()); //this is a little overcomplicated, but illustrates well how we might use `assertTrue` in a different way.
    }

    @Test
    public void departmentIdIsReturnedCorrectly() throws Exception {
        Employee employee = setupNewEmployee();
        int originalCatId = employee.getDepartmentid();
        employeeDao.add(employee);
        assertEquals(originalCatId, employeeDao.findById(employee.getId()).getDepartmentid());
    }

    public Employee setupNewEmployee(){
        return new Employee("elvis", "ek123", 2);
    }
}