package by.urban.web_project.dao.impl;

import by.urban.web_project.dao.AuthorRegistrationKeyDAO;
import by.urban.web_project.dao.DAOException;

public class SQLAuthorRegistrationKeyDAO implements AuthorRegistrationKeyDAO {
    public boolean isValidKey(String registrationKey) throws DAOException {
        return true;
    }
}
