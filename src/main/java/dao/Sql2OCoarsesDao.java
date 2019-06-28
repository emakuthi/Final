package dao;

import models.Content;
import models.Coarses;
import models.Staff;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.List;

public class Sql2OCoarsesDao implements CoarsesDao {

    private final Sql2o sql2o;

    public Sql2OCoarsesDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Coarses coarses) {
        String sql = "INSERT INTO coarses (coarse_name, description, duration, iconurl) VALUES (:coarse_name, :description, :duration, :iconUrl);";

        try(Connection con = DB.sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(coarses)
                    .executeUpdate()
                    .getKey();
            coarses.setId(id);
            System.out.println(sql);
        } catch (Sql2oException ex) {
            System.out.println();
        }
    }

    @Override
    public List<Coarses> getAll() {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM coarses")
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Coarses.class);
        }
    }

    @Override
    public Coarses findById(int id) {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM coarses WHERE id = :id")
                    .throwOnMappingFailure(false)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Coarses.class);
        }
    }

    @Override
    public void update(int id, String newName,String newDuration, String newDescription){
        String sql = "UPDATE coarses SET (coarse_name, description, duration, iconUrl) = (:coarse_name, :description, :duration, :iconUrl) WHERE id=:id";
        try(Connection con = DB.sql2o.open()){
            con.createQuery(sql)
                    .addParameter("coarse_name", newName)
                    .addParameter("id", id)
                    .addParameter("description", newDescription)
                    .addParameter("duration", newDuration)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println();
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from coarses WHERE id=:id"; //raw sql
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println();
        }
    }

    @Override
    public void clearAllCoarses() {
        String sql = "DELETE from coarses"; //raw sql
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println();
        }
    }

    @Override
    public List<Staff> getAllStaffByCoarse(int coarseid) {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM staff WHERE coarseid = :coarseid")
                    .addParameter("coarseid", coarseid)
                    .executeAndFetch(Staff.class);
        }
    }


    @Override
    public List<Content> getAllCoarseContentByCoarse(int coarseid) {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM coarse_content WHERE coarseid = :coarseid")
                    .addParameter("coarseid", coarseid)
                    .executeAndFetch(Content.class);
        }
    }
}