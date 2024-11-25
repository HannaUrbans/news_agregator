package by.urban.web_project.dao;

import by.urban.web_project.dao.impl.*;
import by.urban.web_project.dao.impl.rolesImpl.AuthorDAOImpl;
import by.urban.web_project.dao.impl.rolesImpl.UserDAOImpl;
import by.urban.web_project.dao.roles.IAuthorDAO;
import by.urban.web_project.dao.roles.IUserDAO;

public class DAOFactory {
    private static DAOFactory instance;

    // Экземпляры DAO объектов, инициализируются по мере необходимости
    private IDatabaseConnectionDAO sqlDatabaseConnection;
    private IAuthorDAO sqlAuthorImpl;
    private IUserDAO sqlUserImpl;
    private IAuthorRegistrationKeyDAO sqlAuthorRegistrationImpl;
    private IFavoritesDAO sqlFavoritesImpl;
    private INewsCategoryDAO sqlNewsCategoryImpl;
    private INewsCommentDAO sqlNewsCommentImpl;
    private INewsDAO sqlNewsImpl;
    private ISessionDAO sqlSessionImpl;

    private DAOFactory() {
    }

    // Метод для получения синглтон-экземпляра DAOFactory
    public static DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    // Метод для получения экземпляра DatabaseConnectionDAO
    public IDatabaseConnectionDAO getDbConnection() throws DAOException {
        if (sqlDatabaseConnection == null) {
            sqlDatabaseConnection = new DatabaseConnectionDAOImpl();
        }
        return sqlDatabaseConnection;
    }

    // Методы для получения DAO объектов
    public IAuthorDAO getAuthorDAO() throws DAOException {
        if (sqlAuthorImpl == null) {
            sqlAuthorImpl = new AuthorDAOImpl();
        }
        return sqlAuthorImpl;
    }

    public IUserDAO getUserDAO() throws DAOException {
        if (sqlUserImpl == null) {
            sqlUserImpl = new UserDAOImpl();
        }
        return sqlUserImpl;
    }

    public IAuthorRegistrationKeyDAO getAuthorRegistrationKeyDAO() throws DAOException {
        if (sqlAuthorRegistrationImpl == null) {
            sqlAuthorRegistrationImpl = new AuthorRegistrationKeyDAOImpl();
        }
        return sqlAuthorRegistrationImpl;
    }

    public IFavoritesDAO getFavoritesDAO() {
        if (sqlFavoritesImpl == null) {
            sqlFavoritesImpl = new FavoritesDAOImpl();
        }
        return sqlFavoritesImpl;
    }

    public INewsCategoryDAO getNewsCategoryDAO() {
        if (sqlNewsCategoryImpl == null) {
            sqlNewsCategoryImpl = new NewsCategoryDAOImpl();
        }
        return sqlNewsCategoryImpl;
    }

    public INewsCommentDAO getNewsCommentDAO() {
        if (sqlNewsCommentImpl == null) {
            sqlNewsCommentImpl = new NewsCommentDAOImpl();
        }
        return sqlNewsCommentImpl;
    }

    public INewsDAO getNewsDAO() {
        if (sqlNewsImpl == null) {
            sqlNewsImpl = new NewsDAOImpl();
        }
        return sqlNewsImpl;
    }

    public ISessionDAO getSessionDAO() {
        if (sqlSessionImpl == null) {
            sqlSessionImpl = new SessionDAOImpl();
        }
        return sqlSessionImpl;
    }
}
