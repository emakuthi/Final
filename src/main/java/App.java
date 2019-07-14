
import com.google.gson.Gson;
import dao.*;
import models.*;
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
            port = 4563;
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
            res.redirect("/");

            return null;
        }, new HandlebarsTemplateEngine());

        // locating visitor by id

        get("/visitor/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfVisitorToFind = Integer.parseInt(request.params("id"));
            Visitor foundVisitor = visitorDao.findById(idOfVisitorToFind);
            model.put("logs", foundVisitor);   //add it to model for template to display
            return new ModelAndView(model, "visitor_details.hbs");  //individual post page.
        }, new HandlebarsTemplateEngine());


        //get: show assets
        get("/assetslist", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Asset> assets = assetDao.getAll();
            model.put("assets", assets);
            return new ModelAndView(model, "assetslist.hbs");
        }, new HandlebarsTemplateEngine());


        //get: show new ASSET form
        get("/assets/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Asset> assets = assetDao.getAll();
            model.put("assets", assets);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/assets", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Asset> assets = assetDao.getAll();
            model.put("assets", assets);
            return new ModelAndView(model, "asset_form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process new staff form
        post("/asset/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Asset> allAsset = assetDao.getAll();
            String equipmentSerialNumber = req.queryParams("equipmentSerialNumber");
            String equipmentName = req.queryParams("equipmentName");
            String personWithEquipment = req.queryParams("personWithEquipment");
            String moverId = req.queryParams("moverId");
            String typeOfMovement = req.queryParams("typeOfMovement");
            String phonenumber = req.queryParams("phonenumber");
            String destination = req.queryParams("destination");
            String verifier = req.queryParams("verifier");
            Asset newAsset = new Asset(equipmentSerialNumber, equipmentName, personWithEquipment,moverId,typeOfMovement,phonenumber,destination,verifier);
            assetDao.add(newAsset);
            model.put("assets", allAsset);
            res.redirect("/assetslist");

            return null;
        }, new HandlebarsTemplateEngine());

        // locating asset by id

        get("/asset/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfAssetToFind = Integer.parseInt(request.params("id"));
            Asset foundAsset = assetDao.findById(idOfAssetToFind);
            model.put("logs", foundAsset);   //add it to model for template to display
            return new ModelAndView(model, "asset_details.hbs");  //individual post page.
        }, new HandlebarsTemplateEngine());


        get("/dashboard.hbs", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "dashboard.hbs");
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
