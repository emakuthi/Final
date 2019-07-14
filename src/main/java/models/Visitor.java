package models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class Visitor {
    private int id;
    private String fullName;
    private String idNumber;
    private String crqNumber;
    private  String company;
    private String phonenumber;
    private String location;
    private String reason;
    private Timestamp timeIn;
    private Timestamp timeOut;

    public Visitor(String fullName, String company, String idNumber, String phonenumber, String location, String crqNumber,  String reason) {
        this.fullName = fullName;
        this.idNumber = idNumber;
        this.crqNumber = crqNumber;
        this.company = company;
        this.phonenumber = phonenumber;
        this.location = location;
        this.reason = reason;
        this.timeIn = Timestamp.valueOf(LocalDateTime.now());
        this.timeOut = Timestamp.valueOf(LocalDateTime.now());

    }
    public int getId() { return id; }

    public String getFullName() { return fullName; }

    public String getIdNumber() { return idNumber; }

    public String getCrqNumber() { return crqNumber; }

    public String getCompany() { return company; }

    public String getPhonenumber() { return phonenumber; }

    public String getLocation() { return location; }

    public String getReason() { return reason; }

    public Timestamp getTimeIn() { return timeIn; }

    public Timestamp getTimeOut() { return timeOut; }

    public void setId(int id) { this.id = id; }

    public void setTimeIn(Timestamp timeIn) { this.timeIn = timeIn; }

    public void setTimeOut(Timestamp timeOut) { this.timeOut = timeOut; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visitor visitor = (Visitor) o;
        return getId() == visitor.getId() &&
                getFullName().equals(visitor.getFullName()) &&
                getIdNumber().equals(visitor.getIdNumber()) &&
                getCrqNumber().equals(visitor.getCrqNumber()) &&
                getCompany().equals(visitor.getCompany()) &&
                getPhonenumber().equals(visitor.getPhonenumber()) &&
                getLocation().equals(visitor.getLocation()) &&
                getReason().equals(visitor.getReason()) &&
                getTimeIn().equals(visitor.getTimeIn()) &&
                getTimeOut().equals(visitor.getTimeOut());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFullName(), getIdNumber(), getCrqNumber(), getCompany(), getPhonenumber(), getLocation(), getReason(), getTimeIn(), getTimeOut());
    }
}
