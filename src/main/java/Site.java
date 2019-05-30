import java.time.LocalDateTime;

public class Site {
    private String name;
    private boolean completed;
    private LocalDateTime createdAt;
    private int id;
    private int categoryId;
    private String siteNumber;

    public Site(String name, boolean completed, LocalDateTime createdAt, int id, int engineerId, String siteNumber) {
        this.name = name;
        this.completed = completed;
        this.createdAt = createdAt;
        this.id = id;
        this.categoryId = engineerId;
        this.siteNumber = siteNumber;
    }
}
