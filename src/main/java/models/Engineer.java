package models;

import java.util.Objects;
import java.util.ArrayList;


public class Engineer {
    private String name;
    private int id;
    private ArrayList<Site> sites;


    public Engineer(){}

    public Engineer(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Engineer engineer = (Engineer) o;
        return id == engineer.id &&
                Objects.equals(name, engineer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}