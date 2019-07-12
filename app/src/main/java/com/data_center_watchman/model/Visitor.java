package com.data_center_watchman.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.Objects;

public class Visitor {
    private Integer id;
    @SerializedName("fullname")
    private String fullName;
    @SerializedName("idnumber")
    private String idNumber;
    @SerializedName("crqnumber")
    private String crqNumber;
    @SerializedName("company")
    private  String company;
    @SerializedName("phonenumber")
    private String phonenumber;
    @SerializedName("location")
    private String location;
    @SerializedName("reason")
    private String reason;
    @SerializedName("timein")
    private Timestamp timeIn;
    @SerializedName("timeout")
    private Timestamp timeOut;

    public Visitor(String fullName, String idNumber, String crqNumber, String company, String phonenumber, String location, String reason) {
        this.fullName = fullName;
        this.idNumber = idNumber;
        this.crqNumber = crqNumber;
        this.company = company;
        this.phonenumber = phonenumber;
        this.location = location;
        this.reason = reason;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCrqNumber() {
        return crqNumber;
    }

    public void setCrqNumber(String crqNumber) {
        this.crqNumber = crqNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Timestamp timeIn) {
        this.timeIn = timeIn;
    }

    public Timestamp getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Timestamp timeOut) {
        this.timeOut = timeOut;
    }

}
