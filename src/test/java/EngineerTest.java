import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class EngineerTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void Engineer_instantiatesCorrectly_true() {
        Engineer testEngineer = new Engineer(0, "elvis", "EK6129", LocalDateTime.now().now(), true);
        assertEquals(true, testEngineer instanceof Engineer);
    }

}