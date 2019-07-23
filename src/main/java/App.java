
import com.google.gson.Gson;
import dao.*;
import models.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;
public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        Sql2oVisitorDao visitorDao = new Sql2oVisitorDao(DB.sql2o);
        Sql2oAssetDao assetDao = new Sql2oAssetDao(DB.sql2o);
        Gson gson = new Gson();

        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4569;
        }
        port(port);


        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "home.hbs");
        }, new HandlebarsTemplateEngine());

//        get: show new visitor form
        get("/home", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Visitor> visitors = visitorDao.getAll();
            model.put("logs", visitors);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/visitors", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Visitor> visitors = visitorDao.getAll();
            model.put("logs", visitors);
            return new ModelAndView(model, "visitors_form");
        }, new HandlebarsTemplateEngine());

        //task: process new staff form
        post("/visitor/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Visitor> allVisitor = visitorDao.getAll();
            String fullName = req.queryParams("fullName");
            String company = req.queryParams("company");
            String idNumber = req.queryParams("idNumber");
            String phoneNumber = req.queryParams("phonenumber");
            String location = req.queryParams("location");
            String crqNumber = req.queryParams("crqNumber");
            String reason = req.queryParams("reason");
            Visitor newVisitor = new Visitor(fullName, company, idNumber,phoneNumber,location,crqNumber,reason);
            visitorDao.add(newVisitor);
            model.put("logs", allVisitor);
            res.redirect("/home");

            return null;
        }, new HandlebarsTemplateEngine());

        // locating visitor by id

        get("/visitor/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfVisitorToFind = Integer.parseInt(request.params("id"));
            Visitor foundVisitor = visitorDao.findById(idOfVisitorToFind);
            model.put("visitor", foundVisitor);   //add it to model for template to display
            return new ModelAndView(model, "visitor_details.hbs");  //individual post page.
        }, new HandlebarsTemplateEngine());

        //checkin Visitor
        post("/visitorIn/:id", (req, res) -> {
            int idOfCategoryToEdit = Integer.parseInt(req.params(":id"));
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            visitorDao.updateTimeIn(idOfCategoryToEdit, timestamp);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //checkout the visitor;

        post("/visitor/:id", (req, res) -> {
            int idOfCategoryToEdit = Integer.parseInt(req.params(":id"));
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            visitorDao.updateTimeOut(idOfCategoryToEdit, timestamp);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/requests", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Visitor> visitors = visitorDao.getAllRequests();
            model.put("logs", visitors);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/checkedIn", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Visitor> visitors = visitorDao.getAllCheckedIn();
            model.put("logs", visitors);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/checkedOut", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Visitor> visitors = visitorDao.getAllCheckedOut();
            model.put("logs", visitors);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());


/*
API routes to communicate with the database

 */
        //READ
        get("/getVisitor", "application/json", (req, res) -> {
            System.out.println(visitorDao.getAll());

            if(visitorDao.getAll().size() > 0){
                return gson.toJson(visitorDao.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no logs are currently listed in the database.\"}";
            }
        });
        //CREATE
        post("/postVisitor", "application/json", (req, res) -> {
            Visitor visitor = gson.fromJson(req.body(), Visitor.class);
            System.out.println("CREATE: " +visitor.getFullName());
            visitorDao.add(visitor);
            res.status(201);
            return gson.toJson(visitor);
        });

        // Checking out the visitor
        post("/checkout","application/json", (req, res) -> {
            Visitor visitor = gson.fromJson(req.body(), Visitor.class);
            System.out.println("UPDATE: " +visitor.getFullName());
            int idOfVisitorToCheckout = visitor.getId();
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            visitorDao.updateTimeOut(idOfVisitorToCheckout, timestamp);
            return gson.toJson(visitor);
        });
        post("/checkin","application/json", (req, res) -> {
            Visitor visitor = gson.fromJson(req.body(), Visitor.class);
            System.out.println("UPDATE: " +visitor.getFullName());
            int idOfVisitorToCheckin = visitor.getId();
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            visitorDao.updateTimeIn(idOfVisitorToCheckin, timestamp);
            return gson.toJson(visitor);
        });

        get("/getRequests", "application/json", (req, res) -> {
            System.out.println(visitorDao.getAllRequests());

            if(visitorDao.getAll().size() > 0){
                return gson.toJson(visitorDao.getAllRequests());
            }
            else {
                return "{\"message\":\"I'm sorry, but no logs are currently listed in the database.\"}";
            }
        });
        get("/getCheckedIn", "application/json", (req, res) -> {
            System.out.println(visitorDao.getAllCheckedIn());

            if(visitorDao.getAll().size() > 0){
                return gson.toJson(visitorDao.getAllCheckedIn());
            }
            else {
                return "{\"message\":\"I'm sorry, but no logs are currently listed in the database.\"}";
            }
        });
        get("/getCheckedOut", "application/json", (req, res) -> {
            System.out.println(visitorDao.getAllCheckedOut());

            if(visitorDao.getAll().size() > 0){
                return gson.toJson(visitorDao.getAllCheckedOut());
            }
            else {
                return "{\"message\":\"I'm sorry, but no logs are currently listed in the database.\"}";
            }
        });
    }
}
