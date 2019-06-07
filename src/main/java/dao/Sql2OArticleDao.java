package dao;

import models.Article;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2OArticleDao implements ArticleDao {


    private final Sql2o sql2o;

    public Sql2OArticleDao(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }
    @Override
    public void add(Article article) {
        String sql = "INSERT INTO articles (content, departmentid) VALUES (:content, :departmentid)"; //raw sql
        try(Connection con = DB.sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .bind(article)
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            article.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(); //oops we have an error!
        }
    }

    @Override
    public List<Article> getAll() {
        try(Connection con =DB.sql2o.open()){
            return con.createQuery("SELECT * FROM articles") //raw sql
                    .executeAndFetch(Article.class); //fetch a list
        }
    }


    @Override
    public Article findById(int id) {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM articles WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Article.class); //fetch an individual item
        }
    }

    @Override
    public void update(String newContent, int newDepartmentId){
        String sql = "UPDATE articles SET (content, departmentid) = (:content, :departmentid) WHERE id=:id"; //raw sql
        try(Connection con = DB.sql2o.open()){
            con.createQuery(sql)
                    .addParameter("content", newContent)
                    .addParameter("departmentid", newDepartmentId)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println();
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from articles WHERE id=:id";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println();
        }
    }

    @Override
    public void clearAllArticles() {
        String sql = "DELETE from articles";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println();
        }
    }

}
