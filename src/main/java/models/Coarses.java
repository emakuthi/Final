package models;

import java.util.Objects;
import java.util.ArrayList;


public class Coarses {
    private int id;
    private String coarse_name;
    private String duration;
    private String  description;
    private ArrayList<Staff> staff;
    private ArrayList<Content> contents;

    public Coarses(String coarse_name, String description,  String duration) {
        this.id = id;
        this.coarse_name = coarse_name;
        this.duration = duration;
        this.description = description;
        this.staff = staff;
        this.contents = contents;
    }

    public int getId() {
        return id;
    }

    public String getCoarse_name() {
        return coarse_name;
    }

    public String getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Staff> getStaff() {
        return staff;
    }

    public ArrayList<Content> getContents() {
        return contents;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coarses coarses = (Coarses) o;
        return getId() == coarses.getId() &&
                getCoarse_name().equals(coarses.getCoarse_name()) &&
                getDuration().equals(coarses.getDuration()) &&
                getDescription().equals(coarses.getDescription()) &&
                getStaff().equals(coarses.getStaff()) &&
                getContents().equals(coarses.getContents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCoarse_name(), getDuration(), getDescription(), getStaff(), getContents());
    }
}