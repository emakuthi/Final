package dao;

import models.Employee;
import models.Article;

import java.util.List;

public interface ArticleDao {
    // LIST
    List<Employee> getAll();

    // CREATE
    void add(Article article);

    // READ
    Article findById(int id);

    // UPDATE
    void update(String content,int departmentId);

    // DELETE
    void deleteById(int id);

    void clearAllArticles();
}
