package models;

import java.util.Objects;

public class Content {
   private int id;
   private   String url;
   private int subCourseid;

    public Content(String url, int subCourseid) {
        this.id = id;
        this.url = url;
        this.subCourseid = subCourseid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public int getSubCourseid() {
        return subCourseid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return id == content.id &&
                getSubCourseid() == content.getSubCourseid() &&
                getUrl().equals(content.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getUrl(), getSubCourseid());
    }
}
