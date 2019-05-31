package models;

import org.junit.Test;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class SiteTest {
    @Test
    public void NewTaskObjectGetsCorrectlyCreated_true() throws Exception {
        Site site = setupNewTask();
        assertEquals(true, site instanceof Site);
    }

    @Test
    public void TaskInstantiatesWithDescription_true() throws Exception {
        Site site = setupNewTask();
        assertEquals("Mow the lawn", site.getDescription());
    }

    @Test
    public void isCompletedPropertyIsFalseAfterInstantiation() throws Exception {
        Site site = setupNewTask();
        assertEquals(false, site.getCompleted()); //should never start as completed
    }

    @Test
    public void getCreatedAtInstantiatesWithCurrentTimeToday() throws Exception {
        Site site = setupNewTask();
        assertEquals(LocalDateTime.now().getDayOfWeek(), site.getCreatedAt().getDayOfWeek());
    }

    //helper methods
    public Site setupNewTask(){
        return new Site("Mow the lawn", 1);
    }
}
