package models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Employee {

    private String employee_name;
    private String employee_number;
    private boolean completed;
    private LocalDateTime createdAt;
    private int id;
    private int departmentid;

    public Employee(String employee_name, String employee_number, int departmentid) {
        this.employee_name = employee_name;
        this.employee_number = employee_number;
        this.completed = completed;
        this.createdAt = createdAt;
        this.id = id;
        this.departmentid = departmentid;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_number() {
        return employee_number;
    }

    public void setEmployee_number(String employee_number) {
        this.employee_number = employee_number;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return isCompleted() == employee.isCompleted() &&
                getId() == employee.getId() &&
                getDepartmentid() == employee.getDepartmentid() &&
                Objects.equals(getEmployee_name(), employee.getEmployee_name()) &&
                Objects.equals(getEmployee_number(), employee.getEmployee_number()) &&
                Objects.equals(getCreatedAt(), employee.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmployee_name(), getEmployee_number(), isCompleted(), getCreatedAt(), getId(), getDepartmentid());
    }
}