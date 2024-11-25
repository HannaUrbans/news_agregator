package by.urban.web_project.dao;

/**
 * при регистрации пользователь, который хочет быть автором, в поле "ключ" вводит ключ, полученный при заключении договора
 */
public interface IAuthorRegistrationKeyDAO {
    boolean isKeyValid(String inputAuthorKey) throws DAOException;
}
