package dao;

import models.Content;
import models.Coarses;
import models.Staff;

import java.util.List;

public interface CoarsesDao {

    //LIST
    List<Coarses> getAll();

    //CREATE
    void add (Coarses coarses);

    //READ
    Coarses findById(int id);
    List<Staff> getAllStaffByCoarse(int coarseid);
    List<Content> getAllCoarseContentByCoarse(int coarseid);


    //UPDATE
    void update(int id, String Coarse_name, String duration, String description);

    //DELETE
    void deleteById(int id);
    void clearAllCoarses();
}