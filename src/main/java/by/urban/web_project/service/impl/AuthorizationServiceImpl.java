package by.urban.web_project.service.impl;

import by.urban.web_project.bean.Token;
import by.urban.web_project.bean.User;
import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.IUserDAO;
import by.urban.web_project.service.IAuthorizationService;
import by.urban.web_project.service.ICheckService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;

import java.security.SecureRandom;
import java.util.Base64;

public class AuthorizationServiceImpl implements IAuthorizationService {

    private final DAOFactory daoFactory;
    private final IUserDAO authorizationLogic;
    private final ICheckService checkTool;

    //ранняя инициализация в конструкторе
    public AuthorizationServiceImpl() throws ServiceException {
        try {
            this.daoFactory = DAOFactory.getInstance();
            this.authorizationLogic = daoFactory.getUserDAO();
            this.checkTool = ServiceFactory.getInstance().getCheckService();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User checkAuth(String email, String password) throws ServiceException {

        // Проверка формата email
        if (checkTool.checkInvalidEmail(email)) {
            throw new ServiceException("Неверный формат email");
        }

        // проверка, авторизирован ли посетитель
        try {
            User authorizedUser = authorizationLogic.logIn(email, password);
            if (authorizedUser != null) {
                return authorizedUser;
            }
        } catch (DAOException e) {
            System.err.println("Ошибка при авторизации в качестве пользователя: " + email);
            throw new ServiceException(e);
        }

        //если не удалось авторизовать
        return null;
    }

    public String generateToken() {
        int tokenLength = 15;
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[tokenLength];
        random.nextBytes(tokenBytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    public Token saveToken(int id, String token) throws ServiceException {

        try {
            return authorizationLogic.saveTokenInDb(id, token);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Token returnTokenIfPresent(int userId) throws ServiceException {
        try {
            if (authorizationLogic.checkTokenPresence(userId)) {
                return (authorizationLogic.getFullTokenByUsersId(userId));
            }
            return null;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    public boolean deleteToken(int id) throws ServiceException{
        try{
            return (authorizationLogic.deleteTokenFromDb(id));
        }
        catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public User findUserByToken(String token) throws ServiceException {
        try {
            return authorizationLogic.findUserByTokenInDb(token);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
