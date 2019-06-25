package models;

import java.util.Objects;

public class Staff {

    private String name;
    private String ekNumber;
    private int id;
    private int coarseid;

    public Staff(String name, String ekNumber, int coarseid) {
        this.name = name;
        this.ekNumber = ekNumber;
        this.id = id;
        this.coarseid = coarseid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEkNumber() {
        return ekNumber;
    }

    public void setEkNumber(String ekNumber) {
        this.ekNumber = ekNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoarseid() {
        return coarseid;
    }

    public void setCoarseid(int coarseid) {
        this.coarseid = coarseid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return getId() == staff.getId() &&
                getCoarseid() == staff.getCoarseid() &&
                getName().equals(staff.getName()) &&
                getEkNumber().equals(staff.getEkNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getEkNumber(), getId(), getCoarseid());
    }
}