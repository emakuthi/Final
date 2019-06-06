//package dao;
//
//import models.Department;
//import models.Employee;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.sql2o.Connection;
//import org.sql2o.Sql2o;
//
//import static junit.framework.TestCase.assertEquals;
//import static junit.framework.TestCase.assertFalse;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertTrue;
//
//public class Sql2ODepartmentDaoTest {
//    private Sql2ODepartmentDao departmentDao;
//    private Sql2OEmployeeDao employeeDao;
//    private Connection conn;
//
//    @Before
//    public void setUp() throws Exception {
//        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
//        Sql2o sql2o = new Sql2o(connectionString, "", "");
//        departmentDao = new Sql2ODepartmentDao(sql2o);
//        employeeDao = new Sql2OEmployeeDao(sql2o);
//        conn = sql2o.open();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        conn.close();
//    }
//
//    @Test
//    public void addingDepartmentSetsId() throws Exception {
//        Department department = setupNewDepartment();
//        int originalDepartmentId = department.getId();
//        departmentDao.add(department);
//        assertNotEquals(originalDepartmentId, department.getId());
//    }
//
//    @Test
//    public void existingDepartmentsCanBeFoundById() throws Exception {
//        Department department = setupNewDepartment();
//        departmentDao.add(department);
//        Department foundDepartment = departmentDao.findById(department.getId());
//        assertEquals(department, foundDepartment);
//    }
//
//    @Test
//    public void addedDepartmentsAreReturnedFromGetAll() throws Exception {
//        Department department = setupNewDepartment();
//        departmentDao.add(department);
//        assertEquals(1, departmentDao.getAll().size());
//    }
//
//    @Test
//    public void noDepartmentsReturnsEmptyList() throws Exception {
//        assertEquals(0, departmentDao.getAll().size());
//    }
//
//    @Test
//    public void updateChangesDepartmentContent() throws Exception {
//        String initialDescription = "Yardwork";
//        Department department = new Department(initialDescription, "hello");
//        departmentDao.add(department);
//        departmentDao.update(department.getId(),"Cleaning", "hello");
//        Department updatedDepartment = departmentDao.findById(department.getId());
//        assertNotEquals(initialDescription, updatedDepartment.getName());
//    }
//
//    @Test
//    public void deleteByIdDeletesCorrectDepartment() throws Exception {
//        Department department = setupNewDepartment();
//        departmentDao.add(department);
//        departmentDao.deleteById(department.getId());
//        assertEquals(0, departmentDao.getAll().size());
//    }
//
//    @Test
//    public void clearAllClearsAllDepartments() throws Exception {
//        Department department = setupNewDepartment();
//        Department otherDepartment = new Department("Cleaning", " hello");
//        departmentDao.add(department);
//        departmentDao.add(otherDepartment);
//        int daoSize = departmentDao.getAll().size();
//        departmentDao.clearAllDepartments();
//        assertTrue(daoSize > 0 && daoSize > departmentDao.getAll().size());
//    }
//
//    @Test
//    public void getAllEmployeesByDepartmentReturnsEmployeesCorrectly() throws Exception {
//        Department department = setupNewDepartment();
//        departmentDao.add(department);
//        int departmentId = department.getId();
//        Employee newEmployee = new Employee("elvis", "ek123", departmentId);
//        Employee otherEmployee = new Employee("david", "ek120", departmentId);
//        Employee thirdEmployee = new Employee("robert","ek145", departmentId);
//        employeeDao.add(newEmployee);
//        employeeDao.add(otherEmployee); //we are not adding employee 3 so we can test things precisely.
//        assertEquals(2, departmentDao.getAllEmployeesByDepartment(departmentId).size());
//        assertTrue(departmentDao.getAllEmployeesByDepartment(departmentId).contains(newEmployee));
//        assertTrue(departmentDao.getAllEmployeesByDepartment(departmentId).contains(otherEmployee));
//        assertFalse(departmentDao.getAllEmployeesByDepartment(departmentId).contains(thirdEmployee)); //things are accurate!
//    }
//
//    // helper method
//    public Department setupNewDepartment(){
//        return new Department("Yardwork","hello");
//    }
//}