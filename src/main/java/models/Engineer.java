package models;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;

public class Engineer {
    private  int id;
    private String name;
    private String ekNumber;
    private LocalDateTime createdAt;
    private boolean completed;


    public Engineer(int id, String name, String ekNumber, LocalDateTime createdAt, boolean completed) {
        this.id = id;
        this.name = name;
        this.ekNumber = ekNumber;
        this.createdAt = createdAt;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEkNumber() {
        return ekNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Engineer engineer = (Engineer) o;
        if (completed != engineer.completed) return false;
        if (id != engineer.id) return false;
        if (ekNumber!= engineer.ekNumber) return false;
        return name != null ? name.equals(engineer.name) : engineer.name== null;
    }
}
