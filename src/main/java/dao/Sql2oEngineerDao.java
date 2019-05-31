package dao;

import models.Engineer;
import models.Site;
import org.sql2o.Sql2o;

import java.time.LocalDateTime;
import java.util.List;

public class Sql2oEngineerDao implements  EngineerDao {

    public Sql2oEngineerDao(Sql2o sql2o) {

    }

    @Override
    public List<Engineer> getAll() {
        return null;
    }

    @Override
    public void add(Engineer engineer) {

    }

    @Override
    public Engineer findById(int id) {
        return null;
    }

    @Override
    public List<Site> getAllSitesByEngineer(int engineerId) {
        return null;
    }

    @Override
    public void update(int id, String name, String ekNumber, LocalDateTime createdAt, boolean completed) {

    }

    @Override
    public void deleteById(int id, String name, String ekNumber, LocalDateTime createdAt, boolean completed) {

    }

    @Override
    public void clearAllEngineers() {

    }
}
