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
}
