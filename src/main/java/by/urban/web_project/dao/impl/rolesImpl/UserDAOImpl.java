package by.urban.web_project.dao.impl.rolesImpl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.IDatabaseConnectionDAO;
import by.urban.web_project.dao.roles.IUserDAO;
import by.urban.web_project.model.roles.User;

import java.sql.*;
import java.time.LocalDate;

public class UserDAOImpl implements IUserDAO {

    private final IDatabaseConnectionDAO dbConnectionTool;

    public UserDAOImpl() throws DAOException {
        this.dbConnectionTool = DAOFactory.getInstance().getDbConnection();
    }

    /**
     * Метод авторизации
     *
     * @param email    передан по цепочке "форма авторизации -> слой сервисов"
     * @param password передан по цепочке "форма авторизации -> слой сервисов"
     * @return сформированный из БД объект, который был авторизован в сессии по переданным логину и паролю
     */
    @Override
    public User logUserIn(String email, String password) throws DAOException {
        String query = "SELECT user_id, user_registration_date, user_name, user_email, user_password FROM news_management.users WHERE user_email = ? AND user_password = ?";

        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("user_id");
                    LocalDate registrationDate = resultSet.getDate("user_registration_date").toLocalDate();
                    String name = resultSet.getString("user_name");

                    return new User(id, registrationDate, name, email, password);
                } else {
                    throw new DAOException("Пользователь с email " + email + " и паролем " + password + " не найден.");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Метод для проверки в БД, регистрировался ли данный email ранее. Используется при регистрации
     *
     * @param email передан по цепочке "форма -> слой сервисов"
     * @return true, если в бд уже есть этот email
     */
    @Override
    public boolean doesEmailExistInUserDB(String email) throws DAOException {
        String query = "SELECT 1 FROM news_management.users WHERE user_email = ?";
        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Метод для добавления в БД пользователя
     *
     * @param name     передан по цепочке "форма регистрации -> слой сервисов"
     * @param email    передан по цепочке "форма регистрации -> слой сервисов"
     * @param password передан по цепочке "форма регистрации -> слой сервисов"
     * @return true, если добавлена запись (row) в БД
     */
    @Override
    public boolean registerUserInDatabase(String name, String email, String password) throws DAOException {
        String query = "INSERT INTO news_management.users (user_registration_date, user_name, user_email, user_password) VALUES (?, ?, ?, ?)";
        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            Date date = Date.valueOf(LocalDate.now());
            preparedStatement.setDate(1, date);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);

            preparedStatement.setString(4, password);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Метод обновляет поле "имя" (информация добавляется в личном кабинете)
     */
    @Override
    public void updateUserName(int userId, String newName) throws DAOException {
        String query = "UPDATE news_management.users SET user_name = ? WHERE user_id=?";
        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, userId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Не удалось обновить имя пользователя с ID " + userId);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Метод обновляет поле "пароль" (информация добавляется в личном кабинете)
     */
    @Override
    public void updateUserPassword(int userId, String newPassword) throws DAOException {
        String query = "UPDATE news_management.users SET author_password = ? WHERE users_id=?";
        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, userId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Не удалось обновить пароль пользователя с ID " + userId);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
