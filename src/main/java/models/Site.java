package models;

import java.time.LocalDateTime;

public class Site {

    private String site_name;
    private boolean completed;
    private LocalDateTime createdAt;
    private int id;
    private int engineerid;

    public Site(String description, int categoryId) {
        this.site_name = description;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
        this.categoryId = categoryId;
    }

    public void setDescription(String description) { this.description = description; }
    public String getDescription() {
        return description;
    }

    public void setCompleted(boolean completed) { this.completed = completed; }
    public boolean getCompleted(){
        return this.completed;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(int id) { this.id = id; }
    public int getId() { return id; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        if (completed != site.completed) return false;
        if (id != site.id) return false;
        if (categoryId != site.categoryId) return false;
        return description != null ? description.equals(site.description) : site.description == null;
    }

    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + (completed ? 1 : 0);
        result = 31 * result + id;
        result = 31 * result + categoryId;
        return result;
    }
}