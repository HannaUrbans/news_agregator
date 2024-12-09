package by.urban.web_project.service;

import by.urban.web_project.bean.ProfileDataField;

public interface IChangeProfileService {

   String getFieldData (int id, ProfileDataField profileDataField) throws ServiceException;

    boolean updateBio(int id, String newBio) throws ServiceException;

    void updateName(int id, String newName) throws ServiceException;

    void updateEmail(int id, String newEmail) throws ServiceException;

    void updatePassword(int id, String newPassword) throws ServiceException;
}
