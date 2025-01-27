package by.urban.web_project.service.impl;

import by.urban.web_project.bean.ProfileDataField;
import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.IUserDAO;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.ServiceException;

import java.util.Map;

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
    public String getFieldData(int id, ProfileDataField profileDataField) throws ServiceException {
        try {
            Map<String, String> fields = userDAO.getUserProfileById(id);
            String fieldKey = (profileDataField.name()).toLowerCase();
            String fieldValue = fields.get(fieldKey);
            if (fieldValue == null) {
                throw new IllegalArgumentException("Такого поля не существует");
            }

            return fieldValue;
        } catch (DAOException e) {
            throw new ServiceException(e);
        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    //переделац
    @Override
    public boolean updateBio(int id, String newBio) throws ServiceException {
        try {
            return userDAO.addOrUpdateBio(id, newBio);
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
    public void updateEmail(int id, String newEmail) throws ServiceException {
        try {
            userDAO.updateEmail(id, newEmail);
        } catch (DAOException e) {
            throw new ServiceException("Ошибка при обновлении почты пользователя", e);
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
