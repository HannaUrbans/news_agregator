package by.urban.web_project.service.impl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.roles.IAuthorDAO;
import by.urban.web_project.dao.roles.IUserDAO;
import by.urban.web_project.service.IRegistrationService;
import by.urban.web_project.service.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

public class RegistrationServiceImpl implements IRegistrationService {
    private final DAOFactory daoFactory;
    private final IUserDAO userRegistrationLogic;
    private final IAuthorDAO authorRegistrationLogic;

    public RegistrationServiceImpl() throws DAOException {
        daoFactory = DAOFactory.getInstance();
        userRegistrationLogic = daoFactory.getUserDAO();
        authorRegistrationLogic = daoFactory.getAuthorDAO();
    }

    /**
     * Проверяем, вернулось ли true после добавления данных в БД пользователей при регистрации
     */
    @Override
    public boolean checkUserReg(String name, String email, String password) throws ServiceException {
        try {
            return userRegistrationLogic.registerUserInDatabase(name, email, password);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Проверяем, вернулось ли true после добавления данных в БД авторов при регистрации
     */
    @Override
    public boolean checkAuthorReg(String name, String email, String password, String bio, String authorRegKey) throws ServiceException {
        try {
            return authorRegistrationLogic.registerAuthorInDatabase(name, email, password, bio, authorRegKey);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Проверяем, вернулось ли true после поиска email в БД пользователей
     */
    @Override
    public boolean checkUserEmailExistsInDB(HttpServletRequest request, String email) throws ServiceException {
        try {
            return userRegistrationLogic.doesEmailExistInUserDB(email);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Проверяем, вернулось ли true после поиска email в БД авторов
     */
    @Override
    public boolean checkAuthorEmailExistsInDB(HttpServletRequest request, String email) throws ServiceException {
        try {
            return authorRegistrationLogic.doesEmailExistInAuthorDB(email);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
