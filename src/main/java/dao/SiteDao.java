package dao;

import models.Site;
import java.util.List;

public interface SiteDao {

    // LIST
    List<Site> getAll();

    // CREATE
    void add(Site site);

    // READ
    Site findById(int id);

    // UPDATE
    void update(int id, String description, int engineerId);

    // DELETE
    void deleteById(int id);
    void clearAllSites();
}