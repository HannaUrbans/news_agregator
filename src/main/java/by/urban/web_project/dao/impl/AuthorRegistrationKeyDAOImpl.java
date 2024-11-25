package by.urban.web_project.dao.impl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.IAuthorRegistrationKeyDAO;
import by.urban.web_project.dao.IDatabaseConnectionDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorRegistrationKeyDAOImpl implements IAuthorRegistrationKeyDAO {
    private final IDatabaseConnectionDAO dbConnectionTool;

    public AuthorRegistrationKeyDAOImpl() throws DAOException {
        this.dbConnectionTool = DAOFactory.getInstance().getDbConnection();
    }

    /**
     * Метод проверяет, есть ли в БД ключ, который вводится при регистрации в качестве автора
     * @return true, если ключ найден в БД
     */
    @Override
    public boolean isKeyValid(String registrationKey) throws DAOException {
        String query = "SELECT 1 FROM news_management.author_registration_keys WHERE reg_key = ?";

        try (Connection connection = dbConnectionTool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, registrationKey);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
