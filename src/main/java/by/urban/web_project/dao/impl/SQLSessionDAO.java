package by.urban.web_project.dao.impl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.SessionDAO;

public class SQLSessionDAO implements SessionDAO {
    public boolean addSession(String loggedVisitorEmail) throws DAOException {
        return true;
    }

    public boolean deleteSession(String loggedVisitorEmail) throws DAOException {
        return true;
    }

}
