
import dao.DB;
import models.Engineer;
import models.Site;
import dao.Sql2OEngineerDao;
import dao.Sql2OSiteDao;
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
        Sql2OSiteDao siteDao = new Sql2OSiteDao(DB.sql2o);
        Sql2OEngineerDao engineerDao = new Sql2OEngineerDao(DB.sql2o);


        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);




        //get: show all sites in all engineers and show all Engineers
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Engineer> allEngineers = engineerDao.getAll();
            model.put("engineers", allEngineers);
            List<Site> sites = siteDao.getAll();
            model.put("sites", sites);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //show new Engineer form
        get("/engineers/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Engineer> engineers = engineerDao.getAll();
            model.put("engineers", engineers);
            return new ModelAndView(model, "engineer-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process new Engineer form
        post("/engineers", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            Engineer newEngineer = new Engineer(name);
            System.out.println(name);
            engineerDao.add(newEngineer);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all Engineers and all tasks
        get("/engineers/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            engineerDao.clearAllEngineers();
            siteDao.clearAllSites();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all sites
        get("/sites/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            siteDao.clearAllSites();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual Engineer and sites it contains
        get("/engineers/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfEngineerToFind = Integer.parseInt(req.params("id")); //new
            Engineer foundEngineer = engineerDao.findById(idOfEngineerToFind);
            model.put("engineer", foundEngineer);
            List<Site> allSitesByEngineer = engineerDao.getAllSitesByEngineer(idOfEngineerToFind);
            model.put("sites", allSitesByEngineer);
            model.put("engineers", engineerDao.getAll());
            return new ModelAndView(model, "engineer-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a engineer
        get("/engineers/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editEngineer", true);
            Engineer engineer = engineerDao.findById(Integer.parseInt(req.params("id")));
            model.put("engineer", engineer);
            model.put("engineers", engineerDao.getAll());
            return new ModelAndView(model, "engineer-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a engineer
        post("/engineers/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfEngineerToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("newCategoryName");
            engineerDao.update(idOfEngineerToEdit, newName);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete a engineer and sites it contains
        //  /categories/:id/delete

        //get: delete an individual site
        get("/engineers/:engineer_id/sites/:site_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSiteToDelete = Integer.parseInt(req.params("task_id"));
            siteDao.deleteById(idOfSiteToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show new site form
        get("/sites/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Engineer> categories = engineerDao.getAll();
            model.put("engineers", categories);
            return new ModelAndView(model, "site-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process new site form
        post("/sites", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Engineer> allEngineers = engineerDao.getAll();
            model.put("engineers", allEngineers);
            String description = req.queryParams("description");
            int categoryId = Integer.parseInt(req.queryParams("engineerId"));
            Site newSite = new Site(description, categoryId );
            siteDao.add(newSite);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual site that is nested in a engineer
        get("/engineers/:engineer_id/sites/:site_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSiteToFind = Integer.parseInt(req.params("site_id"));
            Site foundSite = siteDao.findById(idOfSiteToFind);
            int idOfEngineerToFind = Integer.parseInt(req.params("engineer_id"));
            Engineer foundEngineer = engineerDao.findById(idOfEngineerToFind);

            model.put("site", foundSite);
            model.put("engineer", foundEngineer);
            model.put("engineers", engineerDao.getAll()); 

            return new ModelAndView(model, "site-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a site
        get("/sites/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Engineer> allEngineers = engineerDao.getAll();
            model.put("engineers", allEngineers);
            Site site = siteDao.findById(Integer.parseInt(req.params("id")));
            model.put("site", site);
            model.put("editSite", true);
            return new ModelAndView(model, "site-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a site
        post("/sites/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int siteToEditId = Integer.parseInt(req.params("id"));
            String newContent = req.queryParams("description");
            int newCategoryId = Integer.parseInt(req.queryParams("engineerId"));
            siteDao.update(siteToEditId, newContent, newCategoryId);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

    }

}
