package by.urban.web_project.dao;

import by.urban.web_project.bean.User;
import by.urban.web_project.bean.UserRole;

import java.util.Map;

public interface IUserDAO {

    User logIn(String email, String password) throws DAOException;

    boolean doesEmailExistInDB(String email) throws DAOException;

    int registerUserInDatabase(String name, String email, String password)  throws DAOException;

    int registerExclusiveUserInDatabase(String name, String email, String password, String regKey, UserRole userRole) throws DAOException;

    void updateName(int id, String newName) throws DAOException;

    void updateEmail(int id, String newEmail) throws DAOException;

    void updatePassword(int id, String newPassword) throws DAOException;

    void updateBio(int id, String newBio) throws DAOException;

    UserRole specifyKeyTypeIfItIsNotReserved(String registrationKey)  throws DAOException;

    Map<String, String> getUserProfileById(int id) throws DAOException;

    //void logOut(String loggedVisitorEmail) throws DAOException;
    //void addProfilePic(String loggedVisitorEmail, String profilePicUrl) throws DAOException;
    //void changeName(String loggedVisitorEmail, String name) throws DAOException;
    //void changeProfilePic(String loggedVisitorEmail, String profilePicUrl) throws DAOException;
    //boolean deleteProfilePic(String loggedVisitorEmail, String profilePicUrl) throws DAOException;
    //boolean deleteProfile(String loggedVisitorEmail) throws DAOException;
    //void resetPassword(String loggedVisitorEmail) throws DAOException;

}
