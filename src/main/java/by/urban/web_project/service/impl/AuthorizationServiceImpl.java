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
    public AuthorizationServiceImpl() throws ServiceException {
        try {
            this.daoFactory = DAOFactory.getInstance();
            this.userAuthorizationLogic = daoFactory.getUserDAO();
            this.authorAuthorizationLogic = daoFactory.getAuthorDAO();
            this.checkTool = ServiceFactory.getInstance().getCheckService();
        } catch (DAOException e) {
            throw new ServiceException(e);
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
