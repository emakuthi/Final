package dao;

import models.Article;
import models.Department;
import models.Employee;

import java.util.List;

public interface DepartmentDao {

    //LIST
    List<Department> getAll();

    //CREATE
    void add (Department department);

    //READ
    Department findById(int id);
    List<Employee> getAllEmployeesByDepartment(int departmentId);
    List<Article> getAllArticlesByDepartment(int departmentId);


    //UPDATE
    void update(int id, String name, String description);

    //DELETE
    void deleteById(int id);
    void clearAllDepartments();
}