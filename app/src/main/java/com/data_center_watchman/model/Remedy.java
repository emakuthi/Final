package com.data_center_watchman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Remedy {
    @SerializedName("ApprovalPhase")
    @Expose
    private String approvalPhase;
    @SerializedName("RequestStatus")
    @Expose
    private String requestStatus;
    @SerializedName("Summary")
    @Expose
    private String summary;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Reason For Change")
    @Expose
    private String reasonChange;

    public Remedy(String approvalPhase, String requestStatus, String summary, String firstName, String lastName, String reasonChange) {
        this.approvalPhase = approvalPhase;
        this.requestStatus = requestStatus;
        this.summary = summary;
        this.firstName = firstName;
        this.lastName = lastName;
        this.reasonChange = reasonChange;
    }

    public String getApprovalPhase() {
        return approvalPhase;
    }

    public void setApprovalPhase(String approvalPhase) {
        this.approvalPhase = approvalPhase;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getReasonChange() {
        return reasonChange;
    }

    public void setReasonChange(String reasonChange) {
        this.reasonChange = reasonChange;
    }
}
