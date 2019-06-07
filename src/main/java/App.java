
import com.google.gson.Gson;
import dao.DB;
import dao.Sql2OArticleDao;
import exceptions.ApiException;
import models.Article;
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

        Sql2OEmployeeDao employeeDao = new Sql2OEmployeeDao(DB.sql2o);
        Sql2ODepartmentDao departmentDao = new Sql2ODepartmentDao(DB.sql2o);
        Sql2OArticleDao articleDao = new Sql2OArticleDao(DB.sql2o);

        Gson gson = new Gson();

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
//            List<Article> allArticlesByDepartment = departmentDao.getAllArticlesByDepartment(idOfDepartmentToFind);
//            model.put("articles", allArticlesByDepartment);
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
//            int newId = Integer.parseInt(req.params("id"));
            String employeeName = req.queryParams("employee_name");
            String employeeNumber = req.queryParams("employee_number");
            int DepartmentId = Integer.parseInt(req.queryParams("departmentid"));
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
        post("/employees/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
//            int employeeToEditId = Integer.parseInt(req.params("id"));
            String newSiteName = req.queryParams("employee_name");
            String newSiteNumber = req.queryParams("employee_number");
            int newDepartmentId = Integer.parseInt(req.queryParams("departmentid"));
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



        //get: show new article form
        get("/articles/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> departments = departmentDao.getAll();
            model.put("departments", departments);
            return new ModelAndView(model, "article-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process new article form
        post("/articles/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> allDepartments = departmentDao.getAll();
            model.put("departments", allDepartments);
            String content = req.queryParams("content");
            int departmentid = Integer.parseInt(req.queryParams("departmentid"));
            Article newArticle = new Article(content, departmentid);
            articleDao.add(newArticle);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        //get: show a form to update a article
        get("/articles/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> allDepartments = departmentDao.getAll();
            model.put("departments", allDepartments);
            Article article = articleDao.findById(Integer.parseInt(req.params("id")));
            model.put("article", article);
            model.put("editArticle", true);
            return new ModelAndView(model, "article-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a article
        post("/articles/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String newContent = req.queryParams("content");
            int newDepartmentId = Integer.parseInt(req.queryParams("Departmentid"));
            articleDao.update(newContent, newDepartmentId);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete an individual article
        get("/departments/:Department_id/articles/:article_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfArticleToDelete = Integer.parseInt(req.params("article_id"));
            articleDao.deleteById(idOfArticleToDelete);
            res.redirect("article-detail.hbs");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual article that is nested in a department
        get("/departments/:Department_id/articles/:article_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfArticleToFind = Integer.parseInt(req.params("article_id"));
            Article foundArticle = articleDao.findById(idOfArticleToFind);
            int idOfDepartmentToFind = Integer.parseInt(req.params("Department_id"));
            Department foundDepartment = departmentDao.findById(idOfDepartmentToFind);
            model.put("article", foundArticle);
            model.put("department", foundDepartment);
            model.put("departments", departmentDao.getAll());
            return new ModelAndView(model, "article-detail.hbs");
        }, new HandlebarsTemplateEngine());

/*
API routes to communicate with the database





 */
        //READ
        get("/departments/api", "application/json", (req, res) -> {
            System.out.println(departmentDao.getAll());

            if(departmentDao.getAll().size() > 0){
                return gson.toJson(departmentDao.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no departments are currently listed in the database.\"}";
            }
        });

        //CREATE
        post("/departments/api/new", "application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            res.status(201);
            return gson.toJson(department);
        });


        //READ
        get("/employees/api", "application/json", (req, res) -> {
            System.out.println(employeeDao.getAll());

            if(employeeDao.getAll().size() > 0){
                return gson.toJson(employeeDao.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no employees are currently listed in the database.\"}";
            }
        });

        //CREATE
        post("/employees/api/new", "application/json", (req, res) -> {
            Employee employee = gson.fromJson(req.body(), Employee.class);
            employeeDao.add(employee);
            res.status(201);
            return gson.toJson(employee);
        });



    }

}
