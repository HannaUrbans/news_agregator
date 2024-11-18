package by.urban.web_project.dao.impl.rolesImpl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.roles.UserDAO;
import by.urban.web_project.model.roles.User;

public class SQLUserDAO implements UserDAO {
    @Override
    public String logIn(String email, String password) throws DAOException {
        return email.concat(" ").concat(password);
    }

    @Override
    public void logOut(String loggedVisitorEmail) throws DAOException {
        System.out.println(loggedVisitorEmail);
    }

    @Override
    public void addProfilePic(String loggedVisitorEmail, String profilePicUrl) throws DAOException {
        System.out.println(loggedVisitorEmail);
        System.out.println(profilePicUrl);
    }

    @Override
    public void changeName(String loggedVisitorEmail, String name) throws DAOException {
        System.out.println(loggedVisitorEmail);
        System.out.println(name);
    }

    @Override
    public void changeProfilePic(String loggedVisitorEmail, String profilePicUrl) throws DAOException {
        System.out.println(loggedVisitorEmail);
        System.out.println(profilePicUrl);
    }

    @Override
    public boolean deleteProfilePic(String loggedVisitorEmail, String profilePicUrl) throws DAOException {
        return true;
    }

    @Override
    public boolean registerUser(User user) throws DAOException {
        return true;
    }

    @Override
    public boolean deleteProfile(String loggedVisitorEmail) throws DAOException {
        return true;
    }

    @Override
    public void resetPassword(String loggedVisitorEmail) throws DAOException {
        System.out.println(loggedVisitorEmail);
    }

}
