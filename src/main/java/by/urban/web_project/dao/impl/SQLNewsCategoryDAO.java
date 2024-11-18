package by.urban.web_project.dao.impl;

import by.urban.web_project.model.NewsCategory;
import by.urban.web_project.dao.NewsCategoryDAO;

public class SQLNewsCategoryDAO implements NewsCategoryDAO {
    public void addCategory(NewsCategory category) {
        System.out.println(category);
    }

    public void changeCategory(NewsCategory category) {
        System.out.println(category);
    }

    public boolean deleteCategory(int newsCategoryId) {
        return true;
    }
}
