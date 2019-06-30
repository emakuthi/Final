//package dao;


//import static junit.framework.TestCase.assertEquals;
//import static junit.framework.TestCase.assertFalse;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertTrue;
//
//public class Sql2OCoarsesDaoTest {
//    private Sql2OCoarsesDao departmentDao;
//    private Sql2OStaffDao employeeDao;
//    private Connection conn;
//
//    @Before
//    public void setUp() throws Exception {
//        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
//        Sql2o sql2o = new Sql2o(connectionString, "", "");
//        departmentDao = new Sql2OCoarsesDao(sql2o);
//        employeeDao = new Sql2OStaffDao(sql2o);
//        conn = sql2o.open();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        conn.close();
//    }
////
//    @Test
//    public void addingDepartmentSetsId() throws Exception {
//        Coarses coarses = setupNewDepartment();
//        int originalDepartmentId = coarses.getId();
//        departmentDao.add(coarses);
//        assertNotEquals(originalDepartmentId, coarses.getId());
//    }
//
//    @Test
//    public void existingDepartmentsCanBeFoundById() throws Exception {
//        Coarses coarses = setupNewDepartment();
//        departmentDao.add(coarses);
//        Coarses foundCoarses = departmentDao.findById(coarses.getId());
//        assertEquals(coarses, foundCoarses);
//    }
//
//    @Test
//    public void addedDepartmentsAreReturnedFromGetAll() throws Exception {
//        Coarses coarses = setupNewDepartment();
//        departmentDao.add(coarses);
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
//        Coarses coarses = new Coarses(initialDescription, "hello");
//        departmentDao.add(coarses);
//        departmentDao.update(coarses.getId(),"Cleaning", "hello");
//        Coarses updatedCoarses = departmentDao.findById(coarses.getId());
//        assertNotEquals(initialDescription, updatedCoarses.getName());
//    }
//
//    @Test
//    public void deleteByIdDeletesCorrectDepartment() throws Exception {
//        Coarses coarses = setupNewDepartment();
//        departmentDao.add(coarses);
//        departmentDao.deleteById(coarses.getId());
//        assertEquals(0, departmentDao.getAll().size());
//    }
//
//    @Test
//    public void clearAllClearsAllDepartments() throws Exception {
//        Coarses coarses = setupNewDepartment();
//        Coarses otherCoarses = new Coarses("Cleaning", " hello");
//        departmentDao.add(coarses);
//        departmentDao.add(otherCoarses);
//        int daoSize = departmentDao.getAll().size();
//        departmentDao.clearAllDepartments();
//        assertTrue(daoSize > 0 && daoSize > departmentDao.getAll().size());
//    }
//
//    @Test
//    public void getAllEmployeesByDepartmentReturnsEmployeesCorrectly() throws Exception {
//        Coarses coarses = setupNewDepartment();
//        departmentDao.add(coarses);
//        int departmentId = coarses.getId();
//        Staff newStaff = new Staff("elvis", "ek123", departmentId);
//        Staff otherStaff = new Staff("david", "ek120", departmentId);
//        Staff thirdStaff = new Staff("robert","ek145", departmentId);
//        employeeDao.add(newStaff);
//        employeeDao.add(otherStaff); //we are not adding employee 3 so we can test things precisely.
//        assertEquals(2, departmentDao.getAllEmployeesByDepartment(departmentId).size());
//        assertTrue(departmentDao.getAllEmployeesByDepartment(departmentId).contains(newStaff));
//        assertTrue(departmentDao.getAllEmployeesByDepartment(departmentId).contains(otherStaff));
//        assertFalse(departmentDao.getAllEmployeesByDepartment(departmentId).contains(thirdStaff)); //things are accurate!
//    }

//    // helper method
//    public Coarses setupNewDepartment(){
//        return new Coarses("Yardwork","hello");
////    }
//}