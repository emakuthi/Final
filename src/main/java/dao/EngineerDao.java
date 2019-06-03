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
    List<Site> getAllSitesByEngineer(int engineerId);

    //UPDATE
    void update(int id, String name, String ek_number, String region);

    //DELETE
    void deleteById(int id);
    void clearAllEngineers();
}