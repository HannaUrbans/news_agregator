package by.urban.web_project.dao;

import by.urban.web_project.model.Favorites;

public interface FavoritesDAO {
    void addFavorites(Favorites favorites);
    boolean deleteFavorites(String loggedVisitorEmail, int newsId);
    boolean deleteAllFavorites(String loggedVisitorEmail);
}
