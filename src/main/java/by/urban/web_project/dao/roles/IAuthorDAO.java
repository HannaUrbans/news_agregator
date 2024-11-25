package by.urban.web_project.dao.roles;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.model.roles.Author;
import by.urban.web_project.model.roles.User;

import java.time.LocalDate;

public interface IAuthorDAO {
    Author logAuthorIn(String email, String password) throws DAOException;

    boolean doesEmailExistInAuthorDB(String email) throws DAOException;

    boolean registerAuthorInDatabase(String name, String email, String password, String bio, String authorKey) throws DAOException;

    void updateAuthorBio(int authorId, String newBio) throws DAOException;

    void updateAuthorName(int authorId, String newName) throws DAOException;

    void updateAuthorPassword(int authorId, String newPassword) throws DAOException;
}
