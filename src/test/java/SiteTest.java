import models.Site;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class SiteTest {
    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void Site_InstantiatesCorrectly_true() {
        Site testSite = new Site("kibira", true, LocalDateTime.now(), 0, 0, "NM1078");
        assertEquals(true,testSite instanceof Site);
    }
}