package dao;


import models.Site;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.time.LocalDateTime;
import java.util.List;

import static dao.DB.sql2o;

public class Sql2oSiteDao implements SiteDao {

    public Sql2oSiteDao(Sql2o sql2o) {

    }

    @Override
    public List<Site> getAll() {
        try(Connection con = sql2o.open()){
            return  con.createQuery("SELECT * FROM sites")
                    .executeAndFetch(Site.class);
        }
    }
    @Override
    public void add(Site site) {
        String sql = "INSERT INTO sites (String name,LocalDateTime createdAt,int engineerId, String siteNumber)";
       try(Connection con = sql2o.open()){
        int id = (int) con.createQuery(sql, true)
                .bind(site)
                .executeUpdate()
                .getKey();
        site.setId(id);
        }catch (Sql2oException ex){
       }
    }

    @Override
    public Site findById(int id) {
        try(Connection con = sql2o.open()){

            return con.createQuery("SELECT * FROM sites WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Site.class); //fetch an individual item
        }
    }
    @Override
    public void update(String newName, boolean completed, LocalDateTime createdAt, int id, int newEngineerId, String newSiteNumber) {
        String sql = "UPDATE sites SET (name, createdAt, engineerId, siteNumber)";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", newName)
                    .addParameter("engineerId", newEngineerId)
                    .addParameter("id",id)
                    .addParameter("createdAt", createdAt)
                    .addParameter("siteNumber", newSiteNumber)
                    .executeUpdate();}catch(Sql2oException ex){
        }
    }

    @Override
    public void deleteById(String name, boolean completed, LocalDateTime createdAt, int id, int engineerId, String siteNumber) {

        String sql = "DELETE from sites WHERE id=:id";

        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();}catch(Sql2oException ex){
        }

    }

    @Override
    public void clearAllSites() {
        String sql ="DELETE from sites";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        }catch (Sql2oException ex){
        }

    }
}
