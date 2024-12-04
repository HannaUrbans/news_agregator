package by.urban.web_project.dao.impl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.IDatabaseConnectionDAO;
import by.urban.web_project.dao.IUserDAO;
import by.urban.web_project.bean.User;
import by.urban.web_project.bean.UserRole;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
    public User logIn(String email, String password) throws DAOException {
        //для сохранения в Объекте User выбираем не все поля
        //u и r - это псевдонимы
        String query = "SELECT u.id, u.name AS user_name, u.email, r.name AS role_name " +
                "FROM news_management.users u " +
                "JOIN news_management.roles r ON u.role_id = r.id " +
                "WHERE u.email = ? AND u.password = ?";

        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("user_name");  // Используем псевдоним user_name, который ранее задавали с помощью AS
                    String roleName = resultSet.getString("role_name");  // Используем псевдоним role_name, который ранее задавали с помощью AS
                    UserRole userRole = UserRole.valueOf(roleName.toUpperCase());
                    return new User(id, name, userRole);
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
    public boolean doesEmailExistInDB(String email) throws DAOException {
        String query = "SELECT 1 FROM news_management.users WHERE email = ?";
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
     * Метод для добавления в БД пользователя с ролью user
     *
     * @param name     передан по цепочке "форма регистрации -> слой сервисов"
     * @param email    передан по цепочке "форма регистрации -> слой сервисов"
     * @param password передан по цепочке "форма регистрации -> слой сервисов"
     * @return сгенерированный в БД ID добавленного пользователя
     */
    @Override
    public int registerUserInDatabase(String name, String email, String password) throws DAOException {
        //вставляем данные
        String query = "INSERT INTO news_management.users (registration_date, name, email, password, role_id, reg_keys_id) VALUES (?, ?, ?, ?, (SELECT id FROM news_management.roles WHERE name = ? LIMIT 1), ?)";
        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            Date regDate = Date.valueOf(LocalDate.now());
            preparedStatement.setDate(1, regDate);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, UserRole.USER.name());
            preparedStatement.setNull(6, java.sql.Types.INTEGER);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return 0;
    }

    @Override
    public int registerExclusiveUserInDatabase(String name, String email, String password, String regKey, UserRole userRole) throws DAOException {
        //вставляем данные
        String query = "INSERT INTO news_management.users (registration_date, name, email, password, role_id, reg_keys_id) VALUES (?, ?, ?, ?, (SELECT id FROM news_management.roles WHERE name = ? LIMIT 1), (SELECT id FROM news_management.reg_keys WHERE value = ? LIMIT 1))";
        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            Date regDate = Date.valueOf(LocalDate.now());
            preparedStatement.setDate(1, regDate);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, userRole.name());
            preparedStatement.setString(6, regKey);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return 0;
    }

    /**
     * Метод обновляет поле "имя" (информация добавляется в личном кабинете)
     */
    @Override
    public void updateName(int id, String newName) throws DAOException {
        String query = "UPDATE news_management.users SET name = ? WHERE id=?";
        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Не удалось обновить имя пользователя с ID " + id);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Метод обновляет поле "email" (информация добавляется в личном кабинете)
     */
    @Override
    public void updateEmail(int id, String newEmail) throws DAOException {
        String query = "UPDATE news_management.users SET email = ? WHERE id=?";
        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newEmail);
            preparedStatement.setInt(2, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Не удалось обновить email пользователя с ID " + id);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Метод обновляет поле "пароль" (информация добавляется в личном кабинете)
     */
    @Override
    public void updatePassword(int id, String newPassword) throws DAOException {
        String query = "UPDATE news_management.users SET password = ? WHERE id=?";
        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Не удалось обновить пароль пользователя с ID " + id);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Метод обновляет поле "биография" (информация добавляется в личном кабинете)
     */
    @Override
    public void updateBio(int id, String newBio) throws DAOException {
        String query = "UPDATE news_management.user_details SET bio = ? WHERE user_id=?";
        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newBio);
            preparedStatement.setInt(2, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Не удалось обновить био автора с ID " + id);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public UserRole specifyKeyTypeIfItIsNotReserved(String registrationKey) throws DAOException {
        String query = "SELECT r.name FROM news_management.reg_keys rk JOIN news_management.roles r ON rk.roles_id = r.id WHERE rk.value = ? && rk.is_reserved = ?";
        UserRole userRole = null;
        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, registrationKey);
            preparedStatement.setBoolean(2, false);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return UserRole.valueOf(resultSet.getString("name").toUpperCase());
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return null;
    }

    public Map<String, String> getUserProfileById(int id) throws DAOException {
        String query = "SELECT u.email, u.password, ud.bio FROM news_management.users u LEFT JOIN news_management.user_details ud ON u.id = ud.user_id WHERE u.id = ?";

        Map<String, String> userProfile = new HashMap<>();
        System.out.println("это метод userProfile");

        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String bio = resultSet.getString("bio");

                System.out.println("email: " + email);
                System.out.println("password: " + password);
                System.out.println("bio: " + bio);

                userProfile.put("email", email);
                userProfile.put("password", password);
                userProfile.put("bio", bio);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return userProfile;
    }


}
