package by.urban.web_project.dao.roles;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.model.roles.User;

public interface UserDAO {
    String logIn(String email, String password) throws DAOException;

    void logOut(String loggedVisitorEmail) throws DAOException;

    void addProfilePic(String loggedVisitorEmail, String profilePicUrl) throws DAOException;

    void changeName(String loggedVisitorEmail, String name) throws DAOException;

    void changeProfilePic(String loggedVisitorEmail, String profilePicUrl) throws DAOException;

    boolean deleteProfilePic(String loggedVisitorEmail, String profilePicUrl) throws DAOException;

    boolean registerUser(User user) throws DAOException;

    boolean deleteProfile(String loggedVisitorEmail) throws DAOException;

    void resetPassword(String loggedVisitorEmail) throws DAOException;

}
