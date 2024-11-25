package by.urban.web_project.service.impl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.roles.IAuthorDAO;
import by.urban.web_project.dao.roles.IUserDAO;
import by.urban.web_project.model.roles.Author;
import by.urban.web_project.model.roles.User;
import by.urban.web_project.service.IAuthorizationService;
import by.urban.web_project.service.ICheckService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;

public class AuthorizationServiceImpl implements IAuthorizationService {

    private final DAOFactory daoFactory;
    private final IUserDAO userAuthorizationLogic;
    private final IAuthorDAO authorAuthorizationLogic;
    private final ICheckService checkTool;

    //ранняя инициализация в конструкторе
    public AuthorizationServiceImpl() throws ServiceException, DAOException {

        this.daoFactory = DAOFactory.getInstance();
        if (daoFactory == null) {
            throw new ServiceException("DAOFactory is not initialized");
        }

        this.userAuthorizationLogic = daoFactory.getUserDAO();
        if (userAuthorizationLogic == null) {
            throw new ServiceException("User DAO is not initialized");
        }

        this.authorAuthorizationLogic = daoFactory.getAuthorDAO();
        if (authorAuthorizationLogic == null) {
            throw new ServiceException("AuthorDAO is not initialized");
        }

        this.checkTool = ServiceFactory.getInstance().getCheckService();
        if (checkTool == null) {
            throw new ServiceException("CheckService is not initialized");
        }
    }

    @Override
    public Object checkAuth(String email, String password) throws ServiceException {

        // Проверка формата email
        if (checkTool.checkInvalidEmail(email)) {
            throw new ServiceException("Неверный формат email");
        }

        // Авторизация для автора
        try {
            Author authorizedAuthor = authorAuthorizationLogic.logAuthorIn(email, password);
            if (authorizedAuthor != null) {
                return authorizedAuthor;
            }
        } catch (DAOException e) {
            System.err.println("Ошибка при авторизации в качестве автора: " + email);
            throw new ServiceException(e);
        }

        // Авторизация для пользователя
        try {
            User authorizedUser = userAuthorizationLogic.logUserIn(email, password);
            if (authorizedUser != null) {
                return authorizedUser;
            }
        } catch (DAOException e) {
            System.err.println("Ошибка при авторизации в качестве пользователя: " + email);
            throw new ServiceException(e);
        }

        //если не удалось авторизовать ни как Автор, ни как Пользователь
        return null;
    }
}
