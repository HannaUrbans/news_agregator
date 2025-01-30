package by.urban.web_project.service.impl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.IUserDAO;
import by.urban.web_project.model.ProfileDataField;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.ServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

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

    //здесь был свич
    @Override
    public void updateProfile(int id, ProfileDataField fieldToUpdate, String newValue) throws ServiceException {
        Map<String, BiConsumer<Integer, String>> updateMethods = new HashMap<>();
        updateMethods.put(ProfileDataField.BIO.name().toLowerCase(), (authId, newBio) -> {

            try {
                userDAO.addOrUpdateBio(authId, newBio);
            } catch (DAOException e) {
                throw new RuntimeException("Ошибка при обновлении био" + e);
            }
        });
        updateMethods.put(ProfileDataField.NAME.name().toLowerCase(), (authId, newName) -> {
            try {
                userDAO.updateName(authId, newName);
            } catch (DAOException e) {
                throw new RuntimeException("Ошибка при обновлении имени" + e);
            }
        });
        updateMethods.put(ProfileDataField.EMAIL.name().toLowerCase(), (authId, newEmail) -> {
            try {
                userDAO.updateEmail(authId, newEmail);
            } catch (DAOException e) {
                throw new RuntimeException("Ошибка при обновлении email" + e);
            }
        });
        updateMethods.put(ProfileDataField.PASSWORD.name().toLowerCase(), (authId, newPassword) -> {
            try {
                userDAO.updatePassword(authId, newPassword);
            } catch (DAOException e) {
                throw new RuntimeException("Ошибка при обновлении пароля" + e);
            }
        });

        BiConsumer<Integer, String> updateMethod = updateMethods.get(fieldToUpdate.name().toLowerCase());
        if (updateMethod != null) {
            updateMethod.accept(id, newValue);
        } else {
            System.out.println("fieldToUpdate "+ fieldToUpdate);
            System.out.println("updateMethod " + updateMethod);
            throw new ServiceException("Неизвестный тип обновления");
        }

    }

//    //переделац
//    @Override
//    public boolean updateBio(int id, String newBio) throws ServiceException {
//        try {
//            return userDAO.addOrUpdateBio(id, newBio);
//        } catch (DAOException e) {
//            throw new ServiceException("Ошибка при обновлении био автора", e);
//        }
//    }

//    @Override
//    public void updateName(int id, String newName) throws ServiceException {
//        try {
//            userDAO.updateName(id, newName);
//        } catch (DAOException e) {
//            throw new ServiceException("Ошибка при обновлении имени пользователя", e);
//        }
//    }
//
//    @Override
//    public void updateEmail(int id, String newEmail) throws ServiceException {
//        try {
//            userDAO.updateEmail(id, newEmail);
//        } catch (DAOException e) {
//            throw new ServiceException("Ошибка при обновлении почты пользователя", e);
//        }
//    }
//
//    @Override
//    public void updatePassword(int id, String newPassword) throws ServiceException {
//        try {
//            userDAO.updatePassword(id, newPassword);
//        } catch (DAOException e) {
//            throw new ServiceException("Ошибка при обновлении пароля пользователя", e);
//        }
//    }

}
