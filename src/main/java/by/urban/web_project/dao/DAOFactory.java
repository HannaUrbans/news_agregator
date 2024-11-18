package by.urban.web_project.dao;

import by.urban.web_project.dao.impl.*;
import by.urban.web_project.dao.impl.rolesImpl.SQLAuthorDAO;
import by.urban.web_project.dao.impl.rolesImpl.SQLUserDAO;
import by.urban.web_project.dao.roles.AuthorDAO;
import by.urban.web_project.dao.roles.UserDAO;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private final AuthorDAO sqlAuthorImpl = new SQLAuthorDAO();
    private final UserDAO sqlUserImpl = new SQLUserDAO();
    private final AuthorRegistrationKeyDAO sqlAuthorRegistrationImpl = new SQLAuthorRegistrationKeyDAO();
    private final FavoritesDAO sqlFavoritesImpl = new SQLFavoritesDAO();
    private final NewsCategoryDAO sqlNewsCategoryImpl = new SQLNewsCategoryDAO();
    private final NewsCommentDAO sqlNewsCommentImpl = new SQLNewsCommentDAO();
    private final NewsDAO sqlNewsImpl = new SQLNewsDAO();
    private final SessionDAO sqlSessionImpl = new SQLSessionDAO();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public AuthorDAO getAuthorDAO() {
        return sqlAuthorImpl;
    }

    public UserDAO getUserDAO() {
        return sqlUserImpl;
    }

    public AuthorRegistrationKeyDAO getAuthorRegistrationKeyDAO() {
        return sqlAuthorRegistrationImpl;
    }

    public FavoritesDAO getFavoritesDAO() {
        return sqlFavoritesImpl;
    }

    public NewsCategoryDAO getNewsCategoryDAO() {
        return sqlNewsCategoryImpl;
    }

    public NewsCommentDAO getNewsCommentDAO() {
        return sqlNewsCommentImpl;
    }

    public NewsDAO getNewsDAO() {
        return sqlNewsImpl;
    }

    public SessionDAO getSessionDAO() {
        return sqlSessionImpl;
    }
}
