package models;

import java.sql.Timestamp;
import java.util.Objects;

public class Asset {
    private int id;
    private String equipmentSerialNumber;
    private String equipmentName;
    private String personWithEquipment;
    private String moverId;
    private String typeOfMovement;
    private String phonenumber;
    private String destination;
    private String verifier;
    private Timestamp time;

    public Asset(String equipmentSerialNumber, String equipmentName, String personWithEquipment, String moverId, String typeOfMovement, String phonenumber, String destination, String verifier) {
        this.equipmentSerialNumber = equipmentSerialNumber;
        this.equipmentName = equipmentName;
        this.personWithEquipment = personWithEquipment;
        this.moverId = moverId;
        this.typeOfMovement = typeOfMovement;
        this.phonenumber = phonenumber;
        this.destination = destination;
        this.verifier = verifier;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquipmentSerialNumber() {
        return equipmentSerialNumber;
    }

    public void setEquipmentSerialNumber(String equipmentSerialNumber) {
        this.equipmentSerialNumber = equipmentSerialNumber;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getPersonWithEquipment() {
        return personWithEquipment;
    }

    public void setPersonWithEquipment(String personWithEquipment) {
        this.personWithEquipment = personWithEquipment;
    }

    public String getMoverId() {
        return moverId;
    }

    public void setMoverId(String moverId) {
        this.moverId = moverId;
    }

    public String getTypeOfMovement() {
        return typeOfMovement;
    }

    public void setTypeOfMovement(String typeOfMovement) {
        this.typeOfMovement = typeOfMovement;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getVerifier() {
        return verifier;
    }

    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }

    public Timestamp getTime() { return time; }

    public void setTime(Timestamp time) { this.time = time; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Asset)) return false;
        Asset asset = (Asset) o;
        return getId() == asset.getId() &&
                Objects.equals(getEquipmentSerialNumber(), asset.getEquipmentSerialNumber()) &&
                getEquipmentName().equals(asset.getEquipmentName()) &&
                getPersonWithEquipment().equals(asset.getPersonWithEquipment()) &&
                Objects.equals(getMoverId(), asset.getMoverId()) &&
                Objects.equals(getTypeOfMovement(), asset.getTypeOfMovement()) &&
                Objects.equals(getPhonenumber(), asset.getPhonenumber()) &&
                Objects.equals(getDestination(), asset.getDestination()) &&
                getTime().equals(asset.getTime()) &&
                Objects.equals(getVerifier(), asset.getVerifier());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEquipmentSerialNumber(), getEquipmentName(), getPersonWithEquipment(), getMoverId(), getTypeOfMovement(), getPhonenumber(), getDestination(), getVerifier(), getTime());
    }
}


