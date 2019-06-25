package models;

import java.util.Objects;

public class Content {
    int id;
    String content;
    int coarseid;

    public Content(String content, int coarseid) {
        this.id = id;
        this.content = content;
        this.coarseid = coarseid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCoarseId() {
        return coarseid;
    }

    public void setCoarseId(int coarseid) {
        this.coarseid = coarseid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content that = (Content) o;
        return getId() == that.getId() &&
                getCoarseId() == that.getCoarseId() &&
                getContent().equals(that.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContent(), getCoarseId());
    }
}
