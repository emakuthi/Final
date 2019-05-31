package dao;

import models.Engineer;
import models.Site;
import java.util.List;

public interface EngineerDao {

    //LIST
    List<Engineer> getAll();

    //CREATE
    void add (Engineer engineer);

    //READ
    Engineer findById(int id);
    List<Site> getAllTasksByCategory(int categoryId);

    //UPDATE
    void update(int id, String name);

    //DELETE
    void deleteById(int id);
    void clearAllCategories();
}