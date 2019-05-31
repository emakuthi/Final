package dao;

import models.Engineer;
import models.Site;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class Sql2OEngineerDaoTest {
    private Sql2OEngineerDao categoryDao;
    private Sql2OSiteDao taskDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        categoryDao = new Sql2OEngineerDao(sql2o);
        taskDao = new Sql2OSiteDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCategorySetsId() throws Exception {
        Engineer engineer = setupNewCategory();
        int originalCategoryId = engineer.getId();
        categoryDao.add(engineer);
        assertNotEquals(originalCategoryId, engineer.getId());
    }

    @Test
    public void existingCategoriesCanBeFoundById() throws Exception {
        Engineer engineer = setupNewCategory();
        categoryDao.add(engineer);
        Engineer foundEngineer = categoryDao.findById(engineer.getId());
        assertEquals(engineer, foundEngineer);
    }

    @Test
    public void addedCategoriesAreReturnedFromGetAll() throws Exception {
        Engineer engineer = setupNewCategory();
        categoryDao.add(engineer);
        assertEquals(1, categoryDao.getAll().size());
    }

    @Test
    public void noCategoriesReturnsEmptyList() throws Exception {
        assertEquals(0, categoryDao.getAll().size());
    }

    @Test
    public void updateChangesCategoryContent() throws Exception {
        String initialDescription = "Yardwork";
        Engineer engineer = new Engineer(initialDescription);
        categoryDao.add(engineer);
        categoryDao.update(engineer.getId(),"Cleaning");
        Engineer updatedEngineer = categoryDao.findById(engineer.getId());
        assertNotEquals(initialDescription, updatedEngineer.getName());
    }

    @Test
    public void deleteByIdDeletesCorrectCategory() throws Exception {
        Engineer engineer = setupNewCategory();
        categoryDao.add(engineer);
        categoryDao.deleteById(engineer.getId());
        assertEquals(0, categoryDao.getAll().size());
    }

    @Test
    public void clearAllClearsAllCategories() throws Exception {
        Engineer engineer = setupNewCategory();
        Engineer otherEngineer = new Engineer("Cleaning");
        categoryDao.add(engineer);
        categoryDao.add(otherEngineer);
        int daoSize = categoryDao.getAll().size();
        categoryDao.clearAllCategories();
        assertTrue(daoSize > 0 && daoSize > categoryDao.getAll().size());
    }

    @Test
    public void getAllTasksByCategoryReturnsTasksCorrectly() throws Exception {
        Engineer engineer = setupNewCategory();
        categoryDao.add(engineer);
        int categoryId = engineer.getId();
        Site newSite = new Site("mow the lawn", categoryId);
        Site otherSite = new Site("pull weeds", categoryId);
        Site thirdSite = new Site("trim hedge", categoryId);
        taskDao.add(newSite);
        taskDao.add(otherSite); //we are not adding task 3 so we can test things precisely.
        assertEquals(2, categoryDao.getAllTasksByCategory(categoryId).size());
        assertTrue(categoryDao.getAllTasksByCategory(categoryId).contains(newSite));
        assertTrue(categoryDao.getAllTasksByCategory(categoryId).contains(otherSite));
        assertFalse(categoryDao.getAllTasksByCategory(categoryId).contains(thirdSite)); //things are accurate!
    }

    // helper method
    public Engineer setupNewCategory(){
        return new Engineer("Yardwork");
    }
}