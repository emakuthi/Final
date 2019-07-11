
import com.cloudinary.Api;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.api.exceptions.ApiException;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import dao.*;
import models.*;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        Sql2oVisitorDao visitorDao = new Sql2oVisitorDao(DB.sql2o);
        Gson gson = new Gson();

        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }
        port(port);

        //get: show new staff form
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Visitor> visitors = visitorDao.getAll();
            model.put("logs", visitors);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/visitors", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Visitor> visitors = visitorDao.getAll();
            model.put("logs", visitors);
            return new ModelAndView(model, "visitor_form");
        }, new HandlebarsTemplateEngine());

        //task: process new staff form
        post("/visitor/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Visitor> allVisitor = visitorDao.getAll();
            model.put("logs", allVisitor);
            String fullName = req.queryParams("fullname");
            String idNumber = req.queryParams("idnumber");
            String crqNumber = req.queryParams("crqnumber");
            String phoneNumber = req.queryParams("phonenumber");
            Visitor newVisitor = new Visitor(fullName, idNumber,crqNumber,phoneNumber );
            visitorDao.add(newVisitor);
            res.redirect("/");
            return null;
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
            visitorDao.add(visitor);
            res.status(201);
            return gson.toJson(visitor);
        });


    }

}
