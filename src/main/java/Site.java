import java.time.LocalDateTime;

public class Site {
    private String name;
    private boolean completed;
    private LocalDateTime createdAt;
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
}
