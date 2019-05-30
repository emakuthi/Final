import models.Engineer;
import models.Site;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EngineerTest {


    //helper methods
    public Engineer createNewEngineer(){
        return new Engineer(1,"elvis","EK6129",LocalDateTime.now(),true);
    }

    @Rule
    public DatabaseRule database = new DatabaseRule();


    @Test
    public void Engineer_InstantiatesCorrectly_true() {
        Engineer testEngineer = new Engineer(1,"elvis","EK6129",LocalDateTime.now(),true);
        assertEquals(true,testEngineer instanceof Engineer);
    }
    @Test

    public void getName(){
        Engineer testEngineer = new Engineer(1,"elvis","EK6129",LocalDateTime.now(),true);
        assertEquals("elvis", testEngineer.getName());

    }
    @Test
    public void getEkNumber(){
        Engineer testEngineer = new Engineer(1,"elvis","EK6129",LocalDateTime.now(),true);
        assertEquals("EK6129", testEngineer.getEkNumber());

    }

    @Test
    public void getCreatedAt() throws Exception {
        Engineer engineer = createNewEngineer();
        assertEquals(LocalDateTime.now().getDayOfWeek(), engineer.getCreatedAt().getDayOfWeek());
    }

    @Test
    public void equals_returnsTrueIfNameAndEkNumberAreSame_true() {
        Engineer firstEngineer = new Engineer(1,"elvis","EK6129",LocalDateTime.now(),true);
        Engineer anotherEngineer = new Engineer(1,"elvis","EK6129",LocalDateTime.now(),true);
        assertTrue(firstEngineer.equals(anotherEngineer));
    }
}
