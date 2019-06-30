package models;

import java.util.Objects;

public class SubCourse {
    private int id;
    private String name;
    private String url;
    private int coarseid;

    public SubCourse(String name, String url, int coarseid) {
        this.id = id;
        this.name = name;
        this.coarseid = coarseid;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getCoarseid() {
        return coarseid;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubCourse subCourse = (SubCourse) o;
        return getId() == subCourse.getId() &&
                getCoarseid() == subCourse.getCoarseid() &&
                getName().equals(subCourse.getName()) &&
                getUrl().equals(subCourse.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getUrl(), getCoarseid());
    }
}
