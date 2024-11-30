package by.urban.web_project.service.impl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.IUserDAO;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.ServiceException;

public class ChangeProfileServiceImpl implements IChangeProfileService {

    private final IUserDAO userDAO;

    //ранняя инициализация в конструкторе
    public ChangeProfileServiceImpl() throws ServiceException {
        try {
            this.userDAO = DAOFactory.getInstance().getUserDAO();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateBio(int id, String newBio) throws ServiceException {
        try {
            userDAO.updateBio(id, newBio);
        } catch (DAOException e) {
            throw new ServiceException("Ошибка при обновлении био автора", e);
        }
    }

    @Override
    public void updateName(int id, String newName) throws ServiceException {
        try {
            userDAO.updateName(id, newName);
        } catch (DAOException e) {
            throw new ServiceException("Ошибка при обновлении имени пользователя", e);
        }
    }

    @Override
    public void updatePassword(int id, String newPassword) throws ServiceException {
        try {
            userDAO.updatePassword(id, newPassword);
        } catch (DAOException e) {
            throw new ServiceException("Ошибка при обновлении пароля пользователя", e);
        }
    }
}
