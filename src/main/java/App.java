
import models.Engineer;
import models.Site;
import dao.Sql2OEngineerDao;
import dao.Sql2OSiteDao;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2OSiteDao taskDao = new Sql2OSiteDao(sql2o);
        Sql2OEngineerDao categoryDao = new Sql2OEngineerDao(sql2o);


        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);




        //get: show all tasks in all categories and show all categories
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Engineer> allCategories = categoryDao.getAll();
            model.put("engineers", allCategories);
            List<Site> sites = taskDao.getAll();
            model.put("sites", sites);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //show new category form
        get("/engineers/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Engineer> categories = categoryDao.getAll(); //refresh list of links for navbar
            model.put("engineers", categories);
            return new ModelAndView(model, "engineer-form.hbs"); //new
        }, new HandlebarsTemplateEngine());

        //post: process new category form
        post("/engineers", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            Engineer newEngineer = new Engineer(name);
            categoryDao.add(newEngineer);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all categories and all tasks
        get("/engineers/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            categoryDao.clearAllEngineers();
            taskDao.clearAllSites();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all tasks
        get("/sites/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            taskDao.clearAllSites();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual category and tasks it contains
        get("/engineers/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCategoryToFind = Integer.parseInt(req.params("id")); //new
            Engineer foundEngineer = categoryDao.findById(idOfCategoryToFind);
            model.put("engineer", foundEngineer);
            List<Site> allTasksByCategory = categoryDao.getAllSitesByEngineer(idOfCategoryToFind);
            model.put("sites", allTasksByCategory);
            model.put("engineers", categoryDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "engineer-detail.hbs"); //new
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a category
        get("/engineers/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editEngineer", true);
            Engineer engineer = categoryDao.findById(Integer.parseInt(req.params("id")));
            model.put("engineer", engineer);
            model.put("engineers", categoryDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "engineer-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a category
        post("/engineers/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCategoryToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("newCategoryName");
            categoryDao.update(idOfCategoryToEdit, newName);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete a category and tasks it contains
        //  /categories/:id/delete

        //get: delete an individual task
        get("/engineers/:engineer_id/sites/:site_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToDelete = Integer.parseInt(req.params("task_id"));
            taskDao.deleteById(idOfTaskToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show new task form
        get("/sites/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Engineer> categories = categoryDao.getAll();
            model.put("engineers", categories);
            return new ModelAndView(model, "site-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process new task form
        post("/sites", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Engineer> allCategories = categoryDao.getAll();
            model.put("engineers", allCategories);
            String description = req.queryParams("description");
            int categoryId = Integer.parseInt(req.queryParams("engineerId"));
            Site newSite = new Site(description, categoryId ); //ignore the hardcoded categoryId
            taskDao.add(newSite);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual task that is nested in a category
        get("/engineers/:engineer_id/sites/:site_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToFind = Integer.parseInt(req.params("site_id"));
            Site foundSite = taskDao.findById(idOfTaskToFind);
            int idOfCategoryToFind = Integer.parseInt(req.params("engineer_id"));
            Engineer foundEngineer = categoryDao.findById(idOfCategoryToFind);

            model.put("site", foundSite);
            model.put("engineer", foundEngineer);
            model.put("engineers", categoryDao.getAll()); //refresh list of links for navbar

            return new ModelAndView(model, "site-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a task
        get("/sites/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Engineer> allCategories = categoryDao.getAll();
            model.put("engineers", allCategories);
            Site site = taskDao.findById(Integer.parseInt(req.params("id")));
            model.put("site", site);
            model.put("editSite", true);
            return new ModelAndView(model, "site-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a task
        post("/sites/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int taskToEditId = Integer.parseInt(req.params("id"));
            String newContent = req.queryParams("description");
            int newCategoryId = Integer.parseInt(req.queryParams("engineerId"));
            taskDao.update(taskToEditId, newContent, newCategoryId);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

    }

}
