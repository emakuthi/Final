package models;

import java.util.Objects;

public class Article {
    int id;
    String content;
    String departmentId;

    public Article(String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article aArticle = (Article) o;
        return getId() == aArticle.getId() &&
                Objects.equals(getContent(), aArticle.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContent());
    }
}
