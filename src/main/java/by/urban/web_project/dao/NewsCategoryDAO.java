package by.urban.web_project.dao;

import by.urban.web_project.model.NewsCategory;

public interface NewsCategoryDAO {
    void addCategory(NewsCategory category);
    void changeCategory(NewsCategory category);
    boolean deleteCategory(int newsCategoryId);
}
