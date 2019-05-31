package dao;

import models.Engineer;
import models.Site;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.List;

public class Sql2OEngineerDao implements EngineerDao {

    private final Sql2o sql2o;

    public Sql2OEngineerDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Engineer engineer) {
        String sql = "INSERT INTO categories (name) VALUES (:name)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(engineer)
                    .executeUpdate()
                    .getKey();
            engineer.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Engineer> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM categories")
                    .executeAndFetch(Engineer.class);
        }
    }

    @Override
    public Engineer findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM categories WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Engineer.class);
        }
    }

    @Override
    public void update(int id, String newName){
        String sql = "UPDATE categories SET name = :name WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", newName)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from categories WHERE id=:id"; //raw sql
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllCategories() {
        String sql = "DELETE from categories"; //raw sql
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Site> getAllTasksByCategory(int categoryId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM tasks WHERE categoryId = :categoryId")
                    .addParameter("categoryId", categoryId)
                    .executeAndFetch(Site.class);
        }
    }
}