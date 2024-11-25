package by.urban.web_project.service;

public interface IChangeProfileService {
    void updateBio(int authorId, String newBio) throws ServiceException;

    void updateAuthorName(int authorId, String newName) throws ServiceException;

    void updateUserName(int userId, String newName) throws ServiceException;

    void updateAuthorPassword(int authorId, String newPassword) throws ServiceException;
}
