package by.urban.web_project.dao;

import by.urban.web_project.model.NewsCategory;

public interface INewsCategoryDAO {
    void addCategory(NewsCategory category) throws DAOException;
    void changeCategory(NewsCategory category) throws DAOException;
    boolean deleteCategory(int newsCategoryId) throws DAOException;
}