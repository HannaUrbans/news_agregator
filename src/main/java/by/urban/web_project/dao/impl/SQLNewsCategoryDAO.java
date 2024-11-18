package by.urban.web_project.dao.impl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.NewsCategoryDAO;
import by.urban.web_project.model.NewsCategory;

public class SQLNewsCategoryDAO implements NewsCategoryDAO {
    public void addCategory(NewsCategory category) throws DAOException {
        System.out.println(category);
    }

    public void changeCategory(NewsCategory category) throws DAOException {
        System.out.println(category);
    }

    public boolean deleteCategory(int newsCategoryId) throws DAOException {
        return true;
    }
}
