package models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Site {



    private String site_name;
    private String site_number;
    private boolean completed;
    private LocalDateTime createdAt;
    private int id;
    private int engineerid;




    public Site(String site_name, String site_number, int id, int engineerid) {
        this.site_name = site_name;
        this.site_number = site_number;
        this.completed = completed;
        this.createdAt = createdAt;
        this.id = id;
        this.engineerid = engineerid;
    }
    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getSite_number() {
        return site_number;
    }

    public void setSite_number(String site_number) {
        this.site_number = site_number;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getEngineerid() {
        return engineerid;
    }

    public void setEngineerid(int engineerid) {
        this.engineerid = engineerid;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        return isCompleted() == site.isCompleted() &&
                getId() == site.getId() &&
                getEngineerid() == site.getEngineerid() &&
                Objects.equals(getSite_name(), site.getSite_name()) &&
                Objects.equals(getSite_number(), site.getSite_number()) &&
                Objects.equals(getCreatedAt(), site.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSite_name(), getSite_number(), isCompleted(), getCreatedAt(), getId(), getEngineerid());
    }


}