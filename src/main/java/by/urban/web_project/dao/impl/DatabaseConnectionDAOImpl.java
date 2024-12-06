package by.urban.web_project.dao.impl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.IDatabaseConnectionDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionDAOImpl implements IDatabaseConnectionDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/news_management?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public DatabaseConnectionDAOImpl() throws DAOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DAOException("Ошибка при загрузке MySQL драйвера", e);
        }
    }

    @Override
    public Connection getConnection() throws DAOException {

        try {
            // Получаем соединение с базой данных
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Устанавливаем автоматический коммит в true
            //connection.setAutoCommit(true);

            return connection;
        }catch (SQLException e) {
            throw new DAOException("Ошибка при установлении соединения с базой данных", e);
        }
    }
}
