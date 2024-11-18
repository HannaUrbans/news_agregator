package by.urban.web_project.dao.impl.rolesImpl;

import by.urban.web_project.model.roles.User;
import by.urban.web_project.dao.roles.UserDAO;

public class SQLUserDAO implements UserDAO {
    @Override
    public String logIn(String email, String password) {
        return email.concat(" ").concat(password);
    }

    @Override
    public void logOut(String loggedVisitorEmail) {
        System.out.println(loggedVisitorEmail);
    }

    @Override
    public void addProfilePic(String loggedVisitorEmail, String profilePicUrl) {
        System.out.println(loggedVisitorEmail);
        System.out.println(profilePicUrl);
    }

    @Override
    public void changeName(String loggedVisitorEmail, String name) {
        System.out.println(loggedVisitorEmail);
        System.out.println(name);
    }

    @Override
    public void changeProfilePic(String loggedVisitorEmail, String profilePicUrl) {
        System.out.println(loggedVisitorEmail);
        System.out.println(profilePicUrl);
    }

    @Override
    public boolean deleteProfilePic(String loggedVisitorEmail, String profilePicUrl) {
        return true;
    }

    @Override
    public boolean registerUser(User user) {
        return true;
    }

    @Override
    public boolean deleteProfile(String loggedVisitorEmail) {
        return true;
    }

    @Override
    public void resetPassword(String loggedVisitorEmail) {
        System.out.println(loggedVisitorEmail);
    }

}
