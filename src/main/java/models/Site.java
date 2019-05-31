package models;

import java.time.LocalDateTime;

public class Site {
    private String name;
    private boolean completed;
    private LocalDateTime createdAt;

    public void setId(int id) { this.id = id; }

    private int id;
    private int engineerId;
    private String siteNumber;

    public Site(String name, boolean completed, LocalDateTime createdAt, int id, int engineerId, String siteNumber) {
        this.name = name;
        this.completed = completed;
        this.createdAt = createdAt;
        this.id = id;
        this.engineerId = engineerId;
        this.siteNumber = siteNumber;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public int getEngineerId() {
        return engineerId;
    }

    public String getSiteNumber() {
        return siteNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site= (Site) o;
        if (completed != site.completed) return false;
        if (id != site.id) return false;
        if (engineerId != site.engineerId) return false;
        return name != null ? name.equals(site.name) : site.name== null;
    }
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (completed ? 1 : 0);
        result = 31 * result + id;
        result = 31 * result + engineerId;
        return result;
    }
}
