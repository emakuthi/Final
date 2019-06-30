package dao;

import models.Content;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2OContentDao implements ContentDao {


    private final Sql2o sql2o;

    public Sql2OContentDao(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }
    @Override
    public void add(Content content) {
        String sql = "INSERT INTO content (url, subCourseid) VALUES (:url, :subCourseid)"; //raw sql
        try(Connection con = DB.sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .bind(content)
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            content.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(); //oops we have an error!
        }
    }

    @Override
    public List<Content> getAll() {
        try(Connection con =DB.sql2o.open()){
            return con.createQuery("SELECT * FROM content") //raw sql
                    .executeAndFetch(Content.class); //fetch a list
        }
    }


    @Override
    public Content findById(int id) {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM content WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Content.class); //fetch an individual item
        }
    }

    @Override
    public void update(String newContent, int newCoarseId){
        String sql = "UPDATE content SET (url, subcourseid) = (:url, :subcoarseid) WHERE id=:id"; //raw sql
        try(Connection con = DB.sql2o.open()){
            con.createQuery(sql)
                    .addParameter("content", newContent)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println();
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from content WHERE id=:id";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println();
        }
    }

    @Override
    public void clearAllsubCourseContent() {
        String sql = "DELETE from coarse_content";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println();
        }
    }
    @Override
    public List<Content> getAllCoarseContentByCoarse(int coarseid) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM coarse_content WHERE coarseid = :coarseid")
                    .addParameter("coarseid", coarseid)
                    .executeAndFetch(Content.class);
        }
    }


}
