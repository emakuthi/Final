package dao;

import jdk.vm.ci.code.site.Site;

import java.time.LocalDateTime;
import java.util.List;

public interface SiteDao {
    // LIST
    List<Site> getAll();

    // CREATE
    void add(Site task);

    // READ
    Site findById(int id);

    // UPDATE
    void update(String name, boolean completed, LocalDateTime createdAt, int id, int engineerId, String siteNumber);

    // DELETE
    void deleteById(String name, boolean completed, LocalDateTime createdAt, int id, int engineerId, String siteNumber);
    void clearAllSites();
}
