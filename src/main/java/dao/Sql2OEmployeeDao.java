package dao;

import models.Employee;
import org.sql2o.*;
import java.util.List;

public class Sql2OEmployeeDao implements EmployeeDao { //implementing our interface

    private final Sql2o sql2o;

    public Sql2OEmployeeDao(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }

    @Override
    public void add(Employee employee) {
        String sql = "INSERT INTO employees (employee_name, employee_number, departmentid) VALUES (:employee_name, :employee_number, :departmentid)"; //raw sql
        try(Connection con = DB.sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .bind(employee)
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            employee.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(); //oops we have an error!
        }
    }

    @Override
    public List<Employee> getAll() {
        try(Connection con =DB.sql2o.open()){
            return con.createQuery("SELECT * FROM employees") //raw sql
                    .executeAndFetch(Employee.class); //fetch a list
        }
    }


    @Override
    public Employee findById(int id) {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM employees WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Employee.class); //fetch an individual item
        }
    }

    @Override
    public void update(String newEmployee_Name, String newEmployee_Number ,int newDepartmentId){
        String sql = "UPDATE employees SET (employee_name, employee_number, departmentid) = (:employee_name, :employee_number, :departmentid) WHERE id=:id"; //raw sql
        try(Connection con = DB.sql2o.open()){
            con.createQuery(sql)
                    .addParameter("employee_name", newEmployee_Name)
                    .addParameter("employee_number", newEmployee_Number)
                    .addParameter("departmentid", newDepartmentId)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println();
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from employees WHERE id=:id";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println();
        }
    }

    @Override
    public void clearAllEmployees() {
        String sql = "DELETE from employees";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println();
        }
    }
}