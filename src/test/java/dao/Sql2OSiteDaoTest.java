//package dao;
//import models.Site;
//import org.sql2o.*;
//import org.junit.*;
//import static org.junit.Assert.*;
//
//public class Sql2OSiteDaoTest {
//    private Sql2OSiteDao taskDao; //ignore me for now. We'll create this soon.
//    private Connection conn; //must be sql2o class conn
//
//    @Before
//    public void setUp() throws Exception {
//        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
//        Sql2o sql2o = new Sql2o(connectionString, "", "");
//        taskDao = new Sql2OSiteDao(sql2o); //ignore me for now
//        conn = sql2o.open(); //keep connection open through entire test so it does not get erased
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        conn.close();
//    }
//
//    @Test
//    public void addingTaskSetsId() throws Exception {
//        Site site = setupNewTask();
//        int originalTaskId = site.getId();
//        taskDao.add(site);
//        assertNotEquals(originalTaskId, site.getId()); //how does this work?
//    }
//
//    @Test
//    public void existingTasksCanBeFoundById() throws Exception {
//        Site site = setupNewTask();
//        taskDao.add(site); //add to dao (takes care of saving)
//        Site foundSite = taskDao.findById(site.getId()); //retrieve
//        assertEquals(site, foundSite); //should be the same
//    }
//
//    @Test
//    public void addedTasksAreReturnedFromgetAll() throws Exception {
//        Site site = setupNewTask();
//        taskDao.add(site);
//        assertEquals(1, taskDao.getAll().size());
//    }
//
//    @Test
//    public void noTasksReturnsEmptyList() throws Exception {
//        assertEquals(0, taskDao.getAll().size());
//    }
//
//    @Test
//    public void updateChangesTaskContent() throws Exception {
//        String initialDescription = "mow the lawn";
//        Site site = new Site(initialDescription, 1);
//        taskDao.add(site);
//        taskDao.update(site.getId(),"brush the cat", 1);
//        Site updatedSite = taskDao.findById(site.getId()); //why do I need to refind this?
//        assertNotEquals(initialDescription, updatedSite.getDescription());
//    }
//
//    @Test
//    public void deleteByIdDeletesCorrectTask() throws Exception {
//        Site site = setupNewTask();
//        taskDao.add(site);
//        taskDao.deleteById(site.getId());
//        assertEquals(0, taskDao.getAll().size());
//    }
//
//    @Test
//    public void clearAllClearsAll() throws Exception {
//        Site site = setupNewTask();
//        Site otherSite = new Site("brush the cat", 2);
//        taskDao.add(site);
//        taskDao.add(otherSite);
//        int daoSize = taskDao.getAll().size();
//        taskDao.clearAllTasks();
//        assertTrue(daoSize > 0 && daoSize > taskDao.getAll().size()); //this is a little overcomplicated, but illustrates well how we might use `assertTrue` in a different way.
//    }
//
//    @Test
//    public void categoryIdIsReturnedCorrectly() throws Exception {
//        Site site = setupNewTask();
//        int originalCatId = site.getCategoryId();
//        taskDao.add(site);
//        assertEquals(originalCatId, taskDao.findById(site.getId()).getCategoryId());
//    }
//
//    public Site setupNewTask(){
//        return new Site("mow the lawn", 1);
//    }
//}