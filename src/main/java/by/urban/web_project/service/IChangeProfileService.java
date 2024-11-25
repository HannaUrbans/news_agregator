package by.urban.web_project.service;

public interface IChangeProfileService {
    void updateBio(int authorId, String newBio) throws ServiceException;
}
