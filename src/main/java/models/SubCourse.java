package models;

import java.util.Objects;

public class SubCourse {
    private int id;
    private String name;
    private String url;
    private String coarse_name;

    public SubCourse(String coarse_name,String name, String url) {
        this.id = id;
        this.name = name;
        this.coarse_name = coarse_name;
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

    public String getCoarse_name() { return coarse_name; }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubCourse subCourse = (SubCourse) o;
        return getId() == subCourse.getId() &&
                getName().equals(subCourse.getName()) &&
                getUrl().equals(subCourse.getUrl()) &&
                getCoarse_name().equals(subCourse.getCoarse_name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getUrl(), getCoarse_name());
    }
}
