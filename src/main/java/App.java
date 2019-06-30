
import com.cloudinary.Api;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.api.exceptions.ApiException;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import dao.*;
import models.Content;
import models.Coarses;
import models.Staff;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import models.SubCourse;
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
        Sql2OSubCourseDao subCourseDao = new Sql2OSubCourseDao(DB.sql2o);

        Gson gson = new Gson();
        Map config = ObjectUtils.asMap(
                "cloud_name", Constants.cloudName,
                "api_key", Constants.apiKey,
                "api_secret", Constants.apiSecret
        );

        Cloudinary cloudinary = new Cloudinary(config);

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
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Coarses> allCoarses = coarsesDao.getAll();
            model.put("coarses", allCoarses);
            List<Staff> staff = staffDao.getAll();
            model.put("staff", staff);
            String url = cloudinary.url().format("pdf").generate("Sample");
            Transformation transform = new Transformation().width(250).height(168).crop("fit");
            Api cloudinary_api = cloudinary.api();
            List<String> img_urls = new ArrayList<>();
            try {
                Map result = cloudinary_api.resources(ObjectUtils.asMap("type", "upload"));
                ArrayList resources = (ArrayList) result.get("resources");
                // System.out.println(resources);
                for (Object res : resources) {
                    Map img_map = (Map) res;
                    // System.out.println(img_map.get("url"));
                    img_urls.add(img_map.get("secure_url").toString());
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            model.put("images", img_urls);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //show new Coarses form
        get("/coarses/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Coarses> coarses = coarsesDao.getAll();
            model.put("coarses", coarses);
            return new ModelAndView(model, "coarse-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/coarses/new", (req, res)->{

                String name = req.queryParams("coarse_name");
                String description = req.queryParams("description");
                String duration = req.queryParams("duration");
                Coarses newCoarses = new Coarses(name,description, duration);
                coarsesDao.add(newCoarses);
            res.redirect("/subcourses");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/coarses/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCoarseToFind = Integer.parseInt(req.params("id")); //new
            Coarses foundCoarses = coarsesDao.findById(idOfCoarseToFind);
            model.put("coarse", foundCoarses);
            List<Staff> allStaffByCoarse = coarsesDao.getAllStaffByCoarse(idOfCoarseToFind);
            model.put("staff", allStaffByCoarse);
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
        get("/content", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<SubCourse> subCourses = subCourseDao.getAll();
            model.put("subcourses", subCourses);
            return new ModelAndView(model, "content-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process new content formString	uri()
        post("/content/new", "multipart/form-data",(req, res)->{
            String location = docs_uri;
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(location);
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
            Collection<Part> parts = req.raw().getParts();
            String fName = req.raw().getPart("files").getSubmittedFileName();
            Part uploadedFile = req.raw().getPart("files");
            Path out = Paths.get(location + "/" + fName);
            try(final InputStream in = uploadedFile.getInputStream()){
                Files.copy(in, out, StandardCopyOption.REPLACE_EXISTING);
                Map uploadResult = cloudinary.uploader()
                        .upload(out.toFile(), ObjectUtils
                                .asMap("use_filename", true, "unique_filename", false));
                System.out.println(out);
            }
            res.redirect("/");
            return null;
        });

        get("/subcourses", (request, response)-> {
            ///coarses/:Coarse_id/contents/:content_id
            Map<String, Object> model = new HashMap<>();
            List<Coarses> coarses = coarsesDao.getAll();
            model.put("coarses", coarses);
            List<Content> contents = contentDao.getAll();
            String url = cloudinary.url().format("pdf").generate("sample");
            Transformation transform = new Transformation().width(250).height(168).crop("fit");
            Api cloudinary_api = cloudinary.api();
            List<String> img_urls = new ArrayList<>();
            try {
                Map result = cloudinary_api.resources(ObjectUtils.asMap("type", "upload"));
                ArrayList resources = (ArrayList) result.get("resources");
                // System.out.println(resources);
                for (Object res : resources) {
                    Map img_map = (Map) res;
                    // System.out.println(img_map.get("url"));
                    img_urls.add(img_map.get("secure_url").toString());
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            model.put("images", img_urls);
            return new ModelAndView(model, "subcourses-form.hbs");
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


        get("/coarses/:Coarse_id/content/:content_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfContentToFind = Integer.parseInt(req.params("content_id"));
            Content foundContent= contentDao.findById(idOfContentToFind);
            int idOfCoarseToFind = Integer.parseInt(req.params("Coarseid"));
            Coarses foundCoarses = coarsesDao.findById(idOfCoarseToFind);
            model.put("content", foundContent);
            model.put("coarse", foundCoarses);
            model.put("coarses", coarsesDao.getAll());
            return new ModelAndView(model, "staff-detail.hbs");
        }, new HandlebarsTemplateEngine());


        /*Posting ccontent urls into the db*/

        post("/subcourse/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Coarses> allCoarses = coarsesDao.getAll();
            model.put("coarses", allCoarses);
            String subCourseName = req.queryParams("name");
            String newUrl = req.queryParams("url");
            int CoarseId = Integer.parseInt(req.queryParams("coarseid"));
            SubCourse newSubCourse = new SubCourse(subCourseName, newUrl,CoarseId );
            subCourseDao.add(newSubCourse);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/subcourses", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Coarses> coarses = coarsesDao.getAll();
            model.put("coarses", coarses);
            String url = cloudinary.url().format("pdf").generate("sample");
            Transformation transform = new Transformation().width(250).height(168).crop("fit");
            Api cloudinary_api = cloudinary.api();
            List<String> img_urls = new ArrayList<>();
            try {
                Map result = cloudinary_api.resources(ObjectUtils.asMap("type", "upload"));
                ArrayList resources = (ArrayList) result.get("resources");
                // System.out.println(resources);
                for (Object res : resources) {
                    Map img_map = (Map) res;
                    // System.out.println(img_map.get("url"));
                    img_urls.add(img_map.get("secure_url").toString());
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            model.put("images", img_urls);
            return new ModelAndView(model, "sub-courses-form.hbs.hbs");
        }, new HandlebarsTemplateEngine());


/*
API routes to communicate with the database

 */
        //READ
        get("/coarsesApi", "application/json", (req, res) -> {
            System.out.println(coarsesDao.getAll());

            if(coarsesDao.getAll().size() > 0){
                return gson.toJson(coarsesDao.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no coarses are currently listed in the database.\"}";
            }
        });

        //CREATE
        post("/postCoarseApi", "application/json", (req, res) -> {
            Coarses coarses = gson.fromJson(req.body(), Coarses.class);
            coarsesDao.add(coarses);
            res.status(201);
            return gson.toJson(coarses);
        });


        //READ
        get("/staffapi", "application/json", (req, res) -> {
            System.out.println(staffDao.getAll());

            if(staffDao.getAll().size() > 0){
                return gson.toJson(staffDao.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no staff are currently listed in the database.\"}";
            }
        });

        //CREATE
        post("/postStaffApi", "application/json", (req, res) -> {
            Staff staff = gson.fromJson(req.body(), Staff.class);
            staffDao.add(staff);
            res.status(201);
            return gson.toJson(staff);
        });

        get("/coarsesapi/:id", "application/json", (req, res) -> {
            int coarseId = Integer.parseInt(req.params("id"));
            return gson.toJson(coarsesDao.findById(coarseId));
        });
        get("/getContentApi", "application/json", (req, res) -> {
            System.out.println(subCourseDao.getAll());

            if(subCourseDao.getAll().size() > 0){
                return gson.toJson(subCourseDao.getAll());
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

//        get("/subCourse/:id/content", "application/json", (req, res) -> {
//            int subcourseId = Integer.parseInt(req.params("id"));
//
////            Coarses coarseToFind = coarsesDao.findById(coarseId);
//            List<Content> allContents;
//
////            if (coarseToFind == null){
////                throw new ApiException(404, String.format("No restaurant with the id: \"%s\" exists", req.params("id")));
////            }
//
//            allContents = contentDao.getAllCoarseContentByCoarse(coarseId);
//
//            return gson.toJson(allContents);
//        });

//
        //CREATE
        post("/coarses/:coarseId/staff/:staffId", "application/json", (req, res) -> {

            int coarseId = Integer.parseInt(req.params("coarseid"));
            int staffId = Integer.parseInt(req.params("staffid"));
           Coarses coarses= coarsesDao.findById(coarseId);
           Staff staff= staffDao.findById(staffId);

            if (coarses != null && staff != null){
                //both exist and can be associated
               staffDao.addStaffToCoarse(staff, coarses);
                res.status(201);
                return gson.toJson(String.format("staff'%s' and coarse'%s' have been associated", staff.getName(), coarses.getCoarse_name()));
            }
            else {
                throw new ApiException(String.format("Coarse or staff does not exist"));
            }
        });

        get("/coarses/:id/staff", "application/json", (req, res) -> {
            int coarseId = Integer.parseInt(req.params("id"));
            Coarses coarsesToFind = coarsesDao.findById(coarseId);
            if (coarsesToFind == null){
                throw new ApiException(String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            else if (coarsesDao.getAllStaffByCoarse(coarseId).size()==0){
                return "{\"message\":\"I'm sorry, but no users are listed for this department.\"}";
            }
            else {
                return gson.toJson(coarsesDao.getAllStaffByCoarse(coarseId));
            }
        });

    }

}
