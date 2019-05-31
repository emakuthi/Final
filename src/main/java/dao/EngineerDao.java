package dao;

import models.Engineer;
import models.Site;
import org.sql2o.Sql2o;

import java.time.LocalDateTime;
import java.util.List;

import static dao.DB.*;

public interface EngineerDao {

    Sql2o database = sql2o;
   //List
 List<Engineer> getAll();
 ///CREATE
 void add (Engineer engineer);

    //READ
    Engineer findById(int id);
    List<Site> getAllSitesByEngineer(int engineerId);

    //UPDATE
    void update(int id, String name, String ekNumber, LocalDateTime createdAt, boolean completed);

    //DELETE
    void deleteById(int id, String name, String ekNumber, LocalDateTime createdAt, boolean completed);
    void clearAllEngineers();

}
