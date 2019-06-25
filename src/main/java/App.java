
import com.google.gson.Gson;
import dao.DB;
import dao.Sql2OContentDao;
import models.Content;
import models.Coarses;
import models.Staff;
import dao.Sql2OCoarsesDao;
import dao.Sql2OStaffDao;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String docs_location = "/src/main/resources/public/docs";
        String docs_uri = System.getProperty("user.dir") + docs_location;
        System.out.println(docs_uri);
        Sql2OStaffDao staffDao = new Sql2OStaffDao(DB.sql2o);
        Sql2OCoarsesDao coarsesDao = new Sql2OCoarsesDao(DB.sql2o);
        Sql2OContentDao contentDao = new Sql2OContentDao(DB.sql2o);

        Gson gson = new Gson();

        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }
        port(port);

        //get: delete all Coarses and all staff
        get("/coarses/delete", (req, res) -> {
//            getInstance().getPaths();
            Map<String, Object> model = new HashMap<>();
            coarsesDao.clearAllCoarses();
            staffDao.clearAllStaff();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all staff
        get("/staff/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            staffDao.clearAllStaff();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show all staff in all coarses and show all Coarses
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Coarses> allCoarses = coarsesDao.getAll();
            model.put("coarses", allCoarses);
            List<Staff> staff = staffDao.getAll();
            model.put("staff", staff);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //show new Coarses form
        get("/coarses/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Coarses> coarses = coarsesDao.getAll();
            model.put("coarses", coarses);
            return new ModelAndView(model, "coarse-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process new Coarses form
        post("/coarses", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
//            List<Coarses> coarses = coarsesDao.getAll();
            String name = req.queryParams("coarse_name");
            String description = req.queryParams("description");
            String duration = req.queryParams("duration");
            Coarses newCoarses = new Coarses(name, duration, description);
            System.out.println(name);
            coarsesDao.add(newCoarses);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

//        get: show an individual Coarses and staff it contains

        get("/coarses/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCoarseToFind = Integer.parseInt(req.params("id")); //new
            Coarses foundCoarses = coarsesDao.findById(idOfCoarseToFind);
            model.put("coarse", foundCoarses);
            List<Staff> allStaffByCoarse = coarsesDao.getAllStaffByCoarse(idOfCoarseToFind);
            model.put("staff", allStaffByCoarse);
//            List<Content> allCoarseContentByCoarse = coarsesDao.getAllCoarseContentByCoarse(idOfCoarseToFind);
//            model.put("content", allCoarseContentByCoarse);
            model.put("coarses", coarsesDao.getAll());
            return new ModelAndView(model, "coarse-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a coarse
        get("/coarses/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editCoarse", true);
            Coarses coarses = coarsesDao.findById(Integer.parseInt(req.params("id")));
            model.put("coarses", coarses);
            model.put("coarses", coarsesDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "coarses-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a coarse
        post("/coarses/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCoarseToEdit = Integer.parseInt(req.params("id"));
            String newCoarseName= req.queryParams("newCoarseName");
            String newDescription = req.queryParams("newDescription");
            String newDuration = req.queryParams("newDuration");
            coarsesDao.update(idOfCoarseToEdit, newCoarseName, newDescription, newDuration);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show new staff form
        get("/staff/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Coarses> coarses = coarsesDao.getAll();
            model.put("coarses", coarses);
            return new ModelAndView(model, "staff-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process new staff form
        post("/staff/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Coarses> allCoarses = coarsesDao.getAll();
            model.put("coarses", allCoarses);
//            int newId = Integer.parseInt(req.params("id"));
            String staffName = req.queryParams("name");
            String staffNumber = req.queryParams("ekNumber");
            int CoarseId = Integer.parseInt(req.queryParams("coarseid"));
            Staff newStaff = new Staff(staffName, staffNumber,CoarseId );
            staffDao.add(newStaff);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a staff
        get("/staff/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Coarses> allCoarses = coarsesDao.getAll();
            model.put("coarses", allCoarses);
            Staff staff = staffDao.findById(Integer.parseInt(req.params("id")));
            model.put("staff", staff);
            model.put("editSite", true);
            return new ModelAndView(model, "staff-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a staff
        post("/staff/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
//            int staffToEditId = Integer.parseInt(req.params("id"));
            String newSiteName = req.queryParams("name");
            String newSiteNumber = req.queryParams("ekNumber");
            int newCoarseId = Integer.parseInt(req.queryParams("coarseid"));
            staffDao.update(newSiteName, newSiteNumber,newCoarseId);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete an individual staff
        get("/coarses/:Coarse_id/staff/:staff_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSiteToDelete = Integer.parseInt(req.params("staff_id"));
            staffDao.deleteById(idOfSiteToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual staff that is nested in a coarse
        get("/coarses/:Coarse_id/staff/:staff_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSiteToFind = Integer.parseInt(req.params("staff_id"));
            Staff foundStaff = staffDao.findById(idOfSiteToFind);
            int idOfCoarseToFind = Integer.parseInt(req.params("Coarseid"));
            Coarses foundCoarses = coarsesDao.findById(idOfCoarseToFind);
            model.put("staff", foundStaff);
            model.put("coarse", foundCoarses);
            model.put("coarses", coarsesDao.getAll());
            return new ModelAndView(model, "staff-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show new content form
        get("/content/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Coarses> coarses = coarsesDao.getAll();
            model.put("coarses", coarses);
            return new ModelAndView(model, "content-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process new content formString	uri()
        post("/content/new", "multipart/form-data" , (req, res)->{
            String location = "/home/developer/IdeaProjects/SafNewsBanner/src/main/resources/public/docs";
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(location);
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
            Collection<Part> parts = req.raw().getParts();
            for(Part part : parts){
                System.out.println("Name:");
                System.out.println(part.getName());
                System.out.println("Size:");
                System.out.println(part.getSize());
                System.out.println("Filename:");
                System.out.println(part.getSubmittedFileName());
            }
            String fName = req.raw().getPart("content").getSubmittedFileName();
            Part uploadedFile = req.raw().getPart("content");
            System.out.println(uploadedFile);
            Path out = Paths.get(location + "/" + fName);
            try(final InputStream in = uploadedFile.getInputStream()){
                Files.copy(in, out);
                Map<String, Object> model = new HashMap<>();
            List<Coarses> allCoarses = coarsesDao.getAll();
            model.put("coarses", allCoarses);
            int coarseid = Integer.parseInt(req.queryParams("coarseid"));
            Content newContent = new Content(out.toString(), coarseid);
            contentDao.add(newContent);
            System.out.println(out.toString());
            }
            res.redirect("/");
            return null;
        });

        get("/coarses/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCoarseToFind = Integer.parseInt(req.params("id")); //new
            Coarses foundCoarses = coarsesDao.findById(idOfCoarseToFind);
            model.put("coarse", foundCoarses);
            List<Content> allCoarseContentBy = coarsesDao.getAllCoarseContentByCoarse(idOfCoarseToFind);
            model.put("content", allCoarseContentBy);
            model.put("coarses", coarsesDao.getAll());
            return new ModelAndView(model, "coarse-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a content
        get("/content/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Coarses> allCoarses = coarsesDao.getAll();
            model.put("coarses", allCoarses);
            Content content = contentDao.findById(Integer.parseInt(req.params("id")));
            model.put("content", content);
            model.put("editContent", true);
            return new ModelAndView(model, "content-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a content
//        post("/content/:id", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            String newContent = req.queryParams("content");
//            int newCoarseId = Integer.parseInt(req.queryParams("coarseid"));
//            contentDao.update(newContent, newCoarseId);
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());

        //get: delete an individual content
        get("/coarses/:Coarse_id/content/:content_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfContentToDelete = Integer.parseInt(req.params("content_id"));
            contentDao.deleteById(idOfContentToDelete);
            res.redirect("content-detail.hbs");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual content that is nested in a coarse
        get("/coarses/:Coarse_id/content/:content_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfContentToFind = Integer.parseInt(req.params("content_id"));
            Content foundContent = contentDao.findById(idOfContentToFind);
            int idOfCoarseToFind = Integer.parseInt(req.params("Coarse_id"));
            Coarses foundCoarses = coarsesDao.findById(idOfCoarseToFind);
            model.put("content", foundContent);
            model.put("coarse", foundCoarses);
            model.put("coarses", coarsesDao.getAll());
            return new ModelAndView(model, "content-detail.hbs");
        }, new HandlebarsTemplateEngine());

/*
API routes to communicate with the database

 */
        //READ
        get("/coarses/api", "application/json", (req, res) -> {
            System.out.println(coarsesDao.getAll());

            if(coarsesDao.getAll().size() > 0){
                return gson.toJson(coarsesDao.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no coarses are currently listed in the database.\"}";
            }
        });

        //CREATE
        post("/coarses/api/new", "application/json", (req, res) -> {
            Coarses coarses = gson.fromJson(req.body(), Coarses.class);
            coarsesDao.add(coarses);
            res.status(201);
            return gson.toJson(coarses);
        });


        //READ
        get("/staff/api", "application/json", (req, res) -> {
            System.out.println(staffDao.getAll());

            if(staffDao.getAll().size() > 0){
                return gson.toJson(staffDao.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no staff are currently listed in the database.\"}";
            }
        });

        //CREATE
        post("/staff/api/new", "application/json", (req, res) -> {
            Staff staff = gson.fromJson(req.body(), Staff.class);
            staffDao.add(staff);
            res.status(201);
            return gson.toJson(staff);
        });


        get("/coarses/api/:id", "application/json", (req, res) -> {
            int coarseId = Integer.parseInt(req.params("id"));
            return gson.toJson(coarsesDao.findById(coarseId));
        });

        get("/content/api", "application/json", (req, res) -> {
            System.out.println(contentDao.getAll());

            if(contentDao.getAll().size() > 0){
                return gson.toJson(contentDao.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no coarses are currently listed in the database.\"}";
            }
        });

        post("/content/api/new", "application/json", (req, res) -> {
            Content content = gson.fromJson(req.body(), Content.class);
            contentDao.add(content);
            res.status(201);
            return gson.toJson(content);
        });

        get("/coarses/:id/content", "application/json", (req, res) -> {
            int coarseId = Integer.parseInt(req.params("id"));

//            Coarses coarseToFind = coarsesDao.findById(coarseId);
            List<Content> allContents;

//            if (coarseToFind == null){
//                throw new ApiException(404, String.format("No restaurant with the id: \"%s\" exists", req.params("id")));
//            }

            allContents = contentDao.getAllCoarseContentByCoarse(coarseId);

            return gson.toJson(allContents);
        });

//
//        //CREATE
//        post("/departments/:departmentId/user/:userId", "application/json", (req, res) -> {
//
//            int departmentId = Integer.parseInt(req.params("departmentId"));
//            int userId = Integer.parseInt(req.params("userId"));
//            Department department = departmentDao.findById(departmentId);
//            User user = userDao.findById(userId);
//
//
//            if (department != null && user != null){
//                //both exist and can be associated
//                userDao.addUserToDepartment(user, department);
//                res.status(201);
//                return gson.toJson(String.format("User'%s' and Department'%s' have been associated", user.getName(), department.getName()));
//            }
//            else {
//                throw new ApiException(404, String.format("Department or User does not exist"));
//            }
//        });
//
//        get("/departments/:id/users", "application/json", (req, res) -> {
//            int departmentId = Integer.parseInt(req.params("id"));
//            Department departmentToFind = departmentDao.findById(departmentId);
//            if (departmentToFind == null){
//                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
//            }
//            else if (departmentDao.getAllUsersByDepartment(departmentId).size()==0){
//                return "{\"message\":\"I'm sorry, but no users are listed for this department.\"}";
//            }
//            else {
//                return gson.toJson(departmentDao.getAllUsersByDepartment(departmentId));
//            }
//        });
//
//        get("/users/:id/departments", "application/json", (req, res) -> {
//            int userId = Integer.parseInt(req.params("id"));
//            User userToFind = userDao.findById(userId);
//            if (userToFind == null){
//                throw new ApiException(404, String.format("No user with the id: \"%s\" exists", req.params("id")));
//            }
//            else if (userDao.getAllDepartmentsForAUser(userId).size()==0){
//                return "{\"message\":\"I'm sorry, but no departments are listed for this user.\"}";
//            }
//            else {
//                return gson.toJson(userDao.getAllDepartmentsForAUser(userId));
//            }
//        });
//
//
//        post("/departments/:departmentId/articles/new", "application/json", (req, res) -> {
//            int departmentId = Integer.parseInt(req.params("departmentId"));
//            Article article = gson.fromJson(req.body(), Article.class);
//
//            article.setDepartmentId(departmentId);
//            articleDao.add(article);
//            res.status(201);
//            return gson.toJson(article);
//        });


    }

}
