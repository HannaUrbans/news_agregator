package by.urban.web_project.dao;

/**
 * при регистрации пользователь, который хочет быть автором, в поле "ключ" вводит ранее полученный ключ
 */
public interface AuthorRegistrationKeyDAO {
    boolean isValidKey(String registrationKey);
}
