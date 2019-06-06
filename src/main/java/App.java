
import dao.DB;
import models.Department;
import models.Employee;
import dao.Sql2ODepartmentDao;
import dao.Sql2OEmployeeDao;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
//        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
//        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2OEmployeeDao employeeDao = new Sql2OEmployeeDao(DB.sql2o);
        Sql2ODepartmentDao departmentDao = new Sql2ODepartmentDao(DB.sql2o);


        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);


        //get: delete all Departments and all employees
        get("/departments/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            departmentDao.clearAllDepartments();
            employeeDao.clearAllEmployees();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all employees
        get("/employees/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            employeeDao.clearAllEmployees();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show all employees in all departments and show all Departments
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> allDepartments = departmentDao.getAll();
            model.put("departments", allDepartments);
            List<Employee> employees = employeeDao.getAll();
            model.put("employees", employees);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //show new Department form
        get("/departments/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> departments = departmentDao.getAll();
            model.put("departments", departments);
            return new ModelAndView(model, "department-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process new Department form
        post("/departments", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> departments = departmentDao.getAll();
            String name = req.queryParams("name");
            String description = req.queryParams("description");
            String region = req.queryParams("region");
            Department newDepartment = new Department(name, description);
            System.out.println(name);
            departmentDao.add(newDepartment);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

//        get: show an individual Department and employees it contains

        get("/departments/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfDepartmentToFind = Integer.parseInt(req.params("id")); //new
            Department foundDepartment = departmentDao.findById(idOfDepartmentToFind);
            model.put("department", foundDepartment);
            List<Employee> allEmployeesByDepartment = departmentDao.getAllEmployeesByDepartment(idOfDepartmentToFind);
            model.put("employees", allEmployeesByDepartment);
            model.put("departments", departmentDao.getAll());
            return new ModelAndView(model, "department-detail.hbs");
        }, new HandlebarsTemplateEngine());


        //get: show a form to update a department
        get("/departments/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editDepartment", true);
            Department department = departmentDao.findById(Integer.parseInt(req.params("id")));
            model.put("department", department);
            model.put("departments", departmentDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "department-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a department
        post("/departments/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfDepartmentToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("newName");
            String newDescription = req.queryParams("newDescription");
            departmentDao.update(idOfDepartmentToEdit, newName, newDescription);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        //get: show new employee form
        get("/employees/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> departments = departmentDao.getAll();
            model.put("departments", departments);
            return new ModelAndView(model, "employee-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process new employee form
        post("/employees/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> allDepartments = departmentDao.getAll();
            model.put("departments", allDepartments);
            int newId = Integer.parseInt(req.params("id"));
            String employeeName = req.queryParams("employee_name");
            String employeeNumber = req.queryParams("employee_number");
            int DepartmentId = Integer.parseInt(req.queryParams("Departmentid"));
            Employee newEmployee = new Employee(employeeName, employeeNumber,DepartmentId );
            employeeDao.add(newEmployee);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a employee
        get("/employees/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> allDepartments = departmentDao.getAll();
            model.put("departments", allDepartments);
            Employee employee = employeeDao.findById(Integer.parseInt(req.params("id")));
            model.put("employee", employee);
            model.put("editSite", true);
            return new ModelAndView(model, "employee-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a employee
        post("/employees/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
//            int employeeToEditId = Integer.parseInt(req.params("id"));
            String newSiteName = req.queryParams("employee_name");
            String newSiteNumber = req.queryParams("employee_number");
            int newDepartmentId = Integer.parseInt(req.queryParams("Departmentid"));
            employeeDao.update(newSiteName, newSiteNumber,newDepartmentId);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete an individual employee
        get("/departments/:Department_id/employees/:employee_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSiteToDelete = Integer.parseInt(req.params("employee_id"));
            employeeDao.deleteById(idOfSiteToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual employee that is nested in a department
        get("/departments/:Department_id/employees/:employee_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSiteToFind = Integer.parseInt(req.params("employee_id"));
            Employee foundEmployee = employeeDao.findById(idOfSiteToFind);
            int idOfDepartmentToFind = Integer.parseInt(req.params("Department_id"));
            Department foundDepartment = departmentDao.findById(idOfDepartmentToFind);
            model.put("employee", foundEmployee);
            model.put("department", foundDepartment);
            model.put("departments", departmentDao.getAll());
            return new ModelAndView(model, "employee-detail.hbs");
        }, new HandlebarsTemplateEngine());



    }


}
