package by.urban.web_project.dao;

public interface SessionDAO {
    boolean addSession(String loggedVisitorEmail);
    boolean deleteSession(String loggedVisitorEmail);

}
