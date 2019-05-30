import org.junit.rules.ExternalResource;
import org.sql2o.Sql2o;

public class DatabaseRule extends ExternalResource {

    @Override
    protected void before() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/site_maintenance_test", "elvis", "elvis");  //Those with linux or windows use two strings for username and password
    }

}
