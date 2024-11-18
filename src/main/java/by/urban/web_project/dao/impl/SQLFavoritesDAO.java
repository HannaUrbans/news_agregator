package by.urban.web_project.dao.impl;

import by.urban.web_project.model.Favorites;
import by.urban.web_project.dao.FavoritesDAO;

public class SQLFavoritesDAO implements FavoritesDAO {
    public void addFavorites(Favorites favorites) {
        System.out.println(favorites.toString());
    }

    public boolean deleteFavorites(String loggedVisitorEmail, int newsId){
        return true;
    }

    public boolean deleteAllFavorites(String loggedVisitorEmail){
        return true;
    }

}
