package by.urban.web_project.service;

public interface IChangeProfileService {
    void updateBio(int id, String newBio) throws ServiceException;

    void updateName(int id, String newName) throws ServiceException;

    void updatePassword(int id, String newPassword) throws ServiceException;
}
