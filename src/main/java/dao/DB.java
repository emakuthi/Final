package dao;
import org.sql2o.*;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class DB {
    private static URI dbUri;
    public static Sql2o sql2o;

    static {
        Logger logger = LoggerFactory.getLogger(DB.class);

        try {
            if (System.getenv("DATABASE_URL") == null) {
                dbUri = new URI("postgres://localhost:5432/mlinzi");
                logger.info("Using local database.");
            } else {
                dbUri = new URI(System.getenv("DATABASE_URL"));
            }

//            if (System.getenv("DATABASE_URL") == postgres://hblmlwsdetxodl:e0366f4db7be83033060e9a2669e9e1058044b88a6f5b2f91176ac22c5d13401@ec2-50-19-221-38.compute-1.amazonaws.com:5432/d1k8a0ol8eueri
//) {
//                dbUri = new URI("postgres://localhost:5432/data_center_access");
//                logger.info("Using local database.");
//            } else {
//                dbUri = new URI(System.getenv("DATABASE_URL"));
//            }
            int port = dbUri.getPort();
            String host = dbUri.getHost();
            String path = dbUri.getPath();
//            String username = (dbUri.getUserInfo() == null) ? "postgres" : dbUri.getUserInfo().split(":")[0];
//            String password = (dbUri.getUserInfo() == null) ? "postgres" : dbUri.getUserInfo().split(":")[1];
            String username = (dbUri.getUserInfo() == null) ? "postgres" : dbUri.getUserInfo().split(":")[0];
            String password = (dbUri.getUserInfo() == null) ? "postgres" : dbUri.getUserInfo().split(":")[1];

            sql2o = new Sql2o("jdbc:postgresql://" + host + ":" + port + path, username, password);
        } catch (URISyntaxException e ) {
            logger.error("Unable to connect to database.");
        }
    }
}
