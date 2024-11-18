package by.urban.web_project.dao;

public interface SessionDAO {
    boolean addSession(String loggedVisitorEmail) throws DAOException;
    boolean deleteSession(String loggedVisitorEmail) throws DAOException;

}
