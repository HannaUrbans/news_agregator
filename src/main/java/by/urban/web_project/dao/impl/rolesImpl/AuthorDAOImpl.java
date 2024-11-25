package by.urban.web_project.dao.impl.rolesImpl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.IDatabaseConnectionDAO;
import by.urban.web_project.dao.roles.IAuthorDAO;
import by.urban.web_project.model.roles.Author;

import java.sql.*;
import java.time.LocalDate;

public class AuthorDAOImpl implements IAuthorDAO {

    private final IDatabaseConnectionDAO dbConnectionTool;

    public AuthorDAOImpl() throws DAOException {
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
    public Author logAuthorIn(String email, String password) throws DAOException {
        String query = "SELECT author_id, author_registration_date, author_name, author_email, author_password, author_bio FROM news_management.authors WHERE author_email = ? AND author_password = ?";

        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("author_id");
                    LocalDate registrationDate = resultSet.getDate("author_registration_date").toLocalDate();
                    String name = resultSet.getString("author_name");
                    String bio = resultSet.getString("author_bio");

                    return new Author(id, registrationDate, name, email, password, bio);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        // исключение не выкидывать, иначе после проверки на автора, проверка на юзера не идет
        return null;
    }

    /**
     * Метод для проверки в БД, регистрировался ли данный email ранее. Используется при регистрации
     * @param email  передан по цепочке "форма -> слой сервисов"
     * @return true, если в бд уже есть этот email
     */
    @Override
    public boolean doesEmailExistInAuthorDB(String email) throws DAOException {
        String query = "SELECT 1 FROM news_management.authors WHERE author_email = ?";

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
     * Метод для добавления в БД автора
     * @param name передан по цепочке "форма регистрации -> слой сервисов"
     * @param email передан по цепочке "форма регистрации -> слой сервисов"
     * @param password передан по цепочке "форма регистрации -> слой сервисов"
     * @param bio по дефолту передается пустое значение
     * @param authorKey не добавляется в БД, использовалось для создания объекта класса именно Author
     * @return true, если добавлена запись (row) в БД
     */
    @Override
    public boolean registerAuthorInDatabase(String name, String email, String password, String bio, String authorKey) throws DAOException {
        String query = "INSERT INTO news_management.authors (author_registration_date, author_name, author_email, author_password, author_bio) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Date date = Date.valueOf(LocalDate.now());
            preparedStatement.setDate(1, date);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, bio);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Метод обновляет поле "биография" (информация добавляется в личном кабинете)
     */
    @Override
    public void updateAuthorBio(int authorId, String newBio) throws DAOException {
        String query = "UPDATE news_management.authors SET author_bio = ? WHERE author_id=?";
        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newBio);
            preparedStatement.setInt(2, authorId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Не удалось обновить био автора с ID " + authorId);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Метод обновляет поле "имя" (информация добавляется в личном кабинете)
     */
    @Override
    public void updateAuthorName(int authorId, String newName) throws DAOException {
        String query = "UPDATE news_management.authors SET author_name = ? WHERE author_id=?";
        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, authorId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Не удалось обновить имя автора с ID " + authorId);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Метод обновляет поле "пароль" (информация добавляется в личном кабинете)
     */
    @Override
    public void updateAuthorPassword(int authorId, String newPassword) throws DAOException {
        String query = "UPDATE news_management.authors SET author_password = ? WHERE author_id=?";
        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, authorId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Не удалось обновить пароль автора с ID " + authorId);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
