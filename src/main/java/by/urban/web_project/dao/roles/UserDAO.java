package by.urban.web_project.dao.roles;

import by.urban.web_project.model.roles.User;

public interface UserDAO {
    String logIn(String email, String password);

    void logOut(String loggedVisitorEmail);

    void addProfilePic(String loggedVisitorEmail, String profilePicUrl);

    void changeName(String loggedVisitorEmail, String name);

    void changeProfilePic(String loggedVisitorEmail, String profilePicUrl);

    boolean deleteProfilePic(String loggedVisitorEmail, String profilePicUrl);

    boolean registerUser(User user);

    boolean deleteProfile(String loggedVisitorEmail);

    void resetPassword(String loggedVisitorEmail);

}
