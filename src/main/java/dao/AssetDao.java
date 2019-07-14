package dao;

import models.Asset;

import java.util.List;

public interface AssetDao {//LIST
    List<Asset> getAll();
    //CREATE
    void add (Asset asset);

    //READ
    Asset findById(int id);
}
