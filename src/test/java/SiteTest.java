import models.Engineer;
import models.Site;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class SiteTest {

    //helper methods
    public Site createNewSite(){
        return new Site("kibira", true, LocalDateTime.now(), 0, 0, "NM1078");
    }

    @Rule
    public DatabaseRule database = new DatabaseRule();


    @Test
    public void Site_InstantiatesCorrectly_true() {
        Site testSite = new Site("kibira", true, LocalDateTime.now(), 0, 0, "NM1078");
        assertEquals(true,testSite instanceof Site);
    }
    @Test

    public void getName(){
        Site testSite = new Site("kibira", true, LocalDateTime.now(), 0, 0, "NM1078");
        assertEquals("kibira", testSite.getName());

    }
    @Test
    public void getSiteNumber(){
        Site testSite = new Site("kibira", true, LocalDateTime.now(), 0, 0, "NM1078");
        assertEquals("NM1078", testSite.getSiteNumber());

    }

    @Test
    public void getCreatedAt() throws Exception {
        Site site = createNewSite();
        assertEquals(LocalDateTime.now().getDayOfWeek(), site.getCreatedAt().getDayOfWeek());
    }

    @Test
    public void equals_returnsTrueIfNameAndEmailAreSame_true() {
        Site firstSite = new Site("kibira", true, LocalDateTime.now(), 0, 0, "NM1078");
        Site anotherSite = new Site("kibira", true, LocalDateTime.now(), 0, 0, "NM1078");
        assertTrue(firstSite.equals(anotherSite));
    }
}