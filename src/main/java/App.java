
import dao.Sql2oEngineerDao;
import dao.Sql2oSiteDao;
import models.Engineer;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static dao.DB.sql2o;
import static spark.Spark.*;

public class App {
    public static void main(String[] args){

        staticFileLocation("/public");
       sql2o  = new Sql2o("jdbc:postgresql://localhost:5432/site_maintenance_test", "elvis", "elvis");
        Sql2oEngineerDao engineerDao = new Sql2oEngineerDao(sql2o);
        Sql2oSiteDao siteDao = new Sql2oSiteDao(sql2o);


        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);
        // displaying the form
        get("/engineers/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Engineer> engineers = engineerDao.getAll();
            model.put("engineers", engineers);
            return new ModelAndView(model, "engineer-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process new category form
        post("/engineers", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String ekNumber = req.queryParams("ekNumber");
            Engineer newEngineer = new Engineer(name, ekNumber);
            engineerDao.add(newEngineer);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


    }
}
