package by.urban.web_project.dao.impl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.FavoritesDAO;
import by.urban.web_project.model.Favorites;

public class SQLFavoritesDAO implements FavoritesDAO {
    public void addFavorites(Favorites favorites) throws DAOException {
        System.out.println(favorites.toString());
    }

    public boolean deleteFavorites(String loggedVisitorEmail, int newsId) throws DAOException {
        return true;
    }

    public boolean deleteAllFavorites(String loggedVisitorEmail) throws DAOException {
        return true;
    }

}
