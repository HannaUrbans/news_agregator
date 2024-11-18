package by.urban.web_project.dao.roles;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.model.roles.User;

public interface AuthorDAO extends UserDAO {
    void addBio(String bio) throws DAOException;

    void changeBio(String bio) throws DAOException;

    boolean registerAuthorWithKey(User user, String registrationKey) throws DAOException;
}
