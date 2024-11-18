package by.urban.web_project.dao;

import by.urban.web_project.model.Favorites;

public interface FavoritesDAO {
    void addFavorites(Favorites favorites) throws DAOException;
    boolean deleteFavorites(String loggedVisitorEmail, int newsId) throws DAOException;
    boolean deleteAllFavorites(String loggedVisitorEmail) throws DAOException;
}
