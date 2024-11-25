package by.urban.web_project.service.impl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.roles.IAuthorDAO;
import by.urban.web_project.dao.roles.IUserDAO;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.ServiceException;

public class ChangeProfileServiceImpl implements IChangeProfileService {

    private final IAuthorDAO authorDAO;
    private final IUserDAO userDAO;

    //ранняя инициализация в конструкторе
    public ChangeProfileServiceImpl() throws ServiceException, DAOException {
        this.authorDAO = DAOFactory.getInstance().getAuthorDAO();
        if (this.authorDAO == null) {
            throw new ServiceException("AuthorDAO is not initialized");
        }

        this.userDAO = DAOFactory.getInstance().getUserDAO();
        if (this.userDAO == null) {
            throw new ServiceException("UserDAO is not initialized");
        }
    }

    @Override
    public void updateBio(int authorId, String newBio) throws ServiceException {
        try {
            authorDAO.updateAuthorBio(authorId, newBio);
        } catch (DAOException e) {
            throw new ServiceException("Ошибка при обновлении био автора", e);
        }
    }

    @Override
    public void updateAuthorName(int authorId, String newName) throws ServiceException {
        try {
            authorDAO.updateAuthorName(authorId, newName);
        } catch (DAOException e) {
            throw new ServiceException("Ошибка при обновлении имени автора", e);
        }
    }

    @Override
    public void updateUserName(int userId, String newName) throws ServiceException {
        try {
            userDAO.updateUserName(userId, newName);
        } catch (DAOException e) {
            throw new ServiceException("Ошибка при обновлении имени пользователя", e);
        }
    }

    @Override
    public void updateAuthorPassword(int authorId, String newPassword) throws ServiceException {
        try {
            authorDAO.updateAuthorPassword(authorId, newPassword);
        } catch (DAOException e) {
            throw new ServiceException("Ошибка при обновлении имени автора", e);
        }
    }

    @Override
    public void updateUserPassword(int userId, String newPassword) throws ServiceException {
        try {
            userDAO.updateUserPassword(userId, newPassword);
        } catch (DAOException e) {
            throw new ServiceException("Ошибка при обновлении пароля пользователя", e);
        }
    }
}
