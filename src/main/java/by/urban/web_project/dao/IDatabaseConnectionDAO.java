package by.urban.web_project.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabaseConnectionDAO {
    Connection getConnection() throws DAOException;
}
