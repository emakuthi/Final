package dao;

import models.Employee;

import java.util.List;

public interface EmployeeDao {

    // LIST
    List<Employee> getAll();

    // CREATE
    void add(Employee employee);

    // READ
    Employee findById(int id);

    // UPDATE
    void update(String employee_name, String employee_number,int departmentid);

    // DELETE
    void deleteById(int id);

    void clearAllEmployees();
}