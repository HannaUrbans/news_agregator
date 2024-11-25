package by.urban.web_project.dao.roles;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.model.roles.User;

import java.time.LocalDate;

public interface IUserDAO {

    //Сначала я хотела сделать Object logIn, а потом решила, что будет морока с тем, чтобы кастовать результат
    //Или всё-таки лучше не дублировать код путем использования User logUserIn и Author logAuthorIn? ведь отличаться будет только query

    User logUserIn(String email, String password) throws DAOException;

    boolean doesEmailExistInUserDB(String email) throws DAOException;

    boolean registerUserInDatabase(String name, String email, String password) throws DAOException;

    //void logOut(String loggedVisitorEmail) throws DAOException;
    //void addProfilePic(String loggedVisitorEmail, String profilePicUrl) throws DAOException;
    //void changeName(String loggedVisitorEmail, String name) throws DAOException;
    //void changeProfilePic(String loggedVisitorEmail, String profilePicUrl) throws DAOException;
    //boolean deleteProfilePic(String loggedVisitorEmail, String profilePicUrl) throws DAOException;
    //boolean deleteProfile(String loggedVisitorEmail) throws DAOException;
    //void resetPassword(String loggedVisitorEmail) throws DAOException;

}
