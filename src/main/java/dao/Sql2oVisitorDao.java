package dao;
import models.Visitor;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.sql.Timestamp;
import java.util.List;

public class Sql2oVisitorDao implements VisitorDao {

    private final Sql2o sql2o;

    public Sql2oVisitorDao(Sql2o sql2o) {

        this.sql2o = sql2o;
    }
    @Override
    public List<Visitor> getAll() {
        try(Connection con = DB.sql2o.open()){
        return con.createQuery("SELECT * FROM logs")
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Visitor.class);
        }
    }
    @Override
    public List<Visitor> getAllRequests() {
        try(Connection con = DB.sql2o.open()){
        return con.createQuery("SELECT * FROM logs WHERE timeIn is null")
                .throwOnMappingFailure(false)
                .executeAndFetch(Visitor.class);
        }
    }
    @Override
    public List<Visitor> getAllCheckedIn() {
        try(Connection con = DB.sql2o.open()){
        return con.createQuery("SELECT * FROM logs WHERE timeIn is not null")
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Visitor.class);
        }
    }

    @Override
    public List<Visitor> getAllCheckedOut() {
        try(Connection con = DB.sql2o.open()){
        return con.createQuery("SELECT * FROM logs WHERE timeOut is not null")
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Visitor.class);
        }
    }
    @Override
    public void add(Visitor visitor) {
        String sql = "INSERT INTO logs (fullName, company, idNumber, phoneNumber, location, crqNumber, reason, timeIn, timeOut) " +
                "VALUES (:fullName,:company, :idNumber, :phonenumber, :location, :crqNumber, :reason, now(), null);";

        try(Connection con = DB.sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(visitor)
                    .executeUpdate()
                    .getKey();
            visitor.setId(id);
            System.out.println(sql);
        } catch (Sql2oException ex) {
            System.out.println();
        }
    }
    @Override
    public Visitor findById(int id) {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM logs WHERE id = :id")
                    .throwOnMappingFailure(false)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Visitor.class);
        }
    }
    @Override
    public void update(int id,Timestamp newtime) {
        String sql = "UPDATE logs SET timeOut = now() WHERE id = :id";
        try(Connection con = DB.sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

}
