
package models;

import org.junit.Test;
import static org.junit.Assert.*;

public class EngineerTest {

    @Test
    public void NewCategoryObjectGetsCorrectlyCreated_true() throws Exception {
        Engineer engineer = setupNewCategory();
        assertEquals(true, engineer instanceof Engineer);
    }

    @Test
    public void CategoryInstantiatesWithName_school() throws Exception {
        Engineer engineer = setupNewCategory();
        assertEquals("school", engineer.getName());
    }

    //helper methods
    public Engineer setupNewCategory(){
        return new Engineer("school");
    }
}