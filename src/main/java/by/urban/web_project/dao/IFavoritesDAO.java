package by.urban.web_project.dao;

import by.urban.web_project.bean.Favorites;

public interface IFavoritesDAO {
    void addFavorites(Favorites favorites) throws DAOException;
    boolean deleteFavorites(String loggedVisitorEmail, int newsId) throws DAOException;
    boolean deleteAllFavorites(String loggedVisitorEmail) throws DAOException;
}
