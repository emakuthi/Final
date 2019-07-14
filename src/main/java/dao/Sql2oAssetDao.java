package dao;

import models.Asset;
import models.Visitor;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oAssetDao implements AssetDao {
    private final Sql2o sql2o;

    public Sql2oAssetDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<Asset> getAll() {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM assets")
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Asset.class);
        }
    }

    @Override
    public void add(Asset asset) {

        String sql = "INSERT INTO assets () " +
                "VALUES ();";

        try(Connection con = DB.sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(asset)
                    .executeUpdate()
                    .getKey();
            asset.setId(id);
            System.out.println(sql);
        } catch (Sql2oException ex) {
            System.out.println();
        }
    }

    @Override
    public Asset findById(int id) {
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT * FROM assets WHERE id = :id")
                    .throwOnMappingFailure(false)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Asset.class);
        }
    }
}