package by.urban.web_project.dao.impl.rolesImpl;

import by.urban.web_project.model.roles.User;
import by.urban.web_project.dao.roles.AuthorDAO;

public class SQLAuthorDAO extends SQLUserDAO implements AuthorDAO {
    public void addBio(String bio) {
        System.out.println(bio);
    }

    public void changeBio(String bio) {
        System.out.println(bio);
    }

    public boolean registerAuthorWithKey(User user, String registrationKey) {
        return true;
    }
}
