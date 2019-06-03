package models;

import java.util.Objects;
import java.util.ArrayList;


public class Engineer {
    private String name;
    private int id;
    private String  ek_number;
    private String region;
    private ArrayList<Site> sites;


    public Engineer(){}


    public Engineer(String name, String ek_number, String region) {
        this.name = name;
        this.ek_number = ek_number;
        this.region = region;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEk_number() {
        return ek_number;
    }
    public void setEk_number(String ek_number) {
        this.ek_number = ek_number;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Engineer engineer = (Engineer) o;
        return getId() == engineer.getId() &&
                Objects.equals(getName(), engineer.getName()) &&
                Objects.equals(getEk_number(), engineer.getEk_number()) &&
                Objects.equals(getRegion(), engineer.getRegion()) &&
                Objects.equals(sites, engineer.sites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getId(), getEk_number(), getRegion(), sites);
    }



}