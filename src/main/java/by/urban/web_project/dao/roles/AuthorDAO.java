package by.urban.web_project.dao.roles;

import by.urban.web_project.model.roles.User;

public interface AuthorDAO extends UserDAO {
    void addBio(String bio);

    void changeBio(String bio);

    boolean registerAuthorWithKey(User user, String registrationKey);
}
