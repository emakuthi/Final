package dao;

import models.Content;
import models.SubCourse;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2OSubCourseDao implements SubCourseDao {

    private final Sql2o sql2o;

    public Sql2OSubCourseDao(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }


    @Override
    public List<SubCourse> getAll() {

        try(Connection con =DB.sql2o.open()){
            return con.createQuery("SELECT * FROM subcourses") //raw sql
                    .executeAndFetch(SubCourse.class); //fetch a list
        }
    }

    @Override
    public List<Content> getAllSubCoarsesByCoarse(String coarse_name) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM subcourses WHERE coarse_name = :coarse_name")
                    .addParameter("coarseid", coarse_name)
                    .executeAndFetch(Content.class);
        }

    }

    @Override
    public void add(SubCourse subCourse) {

        String sql = "INSERT INTO subcourses(coarse_name, name, url) VALUES (:coarse_name, :name, :url)"; //raw sql
        try(Connection con = DB.sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .bind(subCourse)
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            subCourse.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(); //oops we have an error!
        }

    }

    @Override
    public SubCourse findById(int id) {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM subcourses WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(SubCourse.class); //fetch an individual item
        }
    }

    @Override
    public void update(String newName, String newUrl,String newCoarse_Name) {

        String sql = "UPDATE subcourses SET (coarse_name, name, url) = (:coarse_name, :name, :url) WHERE id=:id"; //raw sql
        try(Connection con = DB.sql2o.open()){
            con.createQuery(sql)
                    .addParameter("coarse_name",newCoarse_Name)
                    .addParameter("name", newName)
                    .addParameter("url", newUrl)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println();
        }

    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from subcourses WHERE id=:id";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println();
        }

    }

    @Override
    public void clearAllsubCourses() {
        String sql = "DELETE from subcourses";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println();
        }

    }
}
