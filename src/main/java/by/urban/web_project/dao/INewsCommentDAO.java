package by.urban.web_project.dao;

import by.urban.web_project.model.NewsComment;

//НЕ БУДЕТ РЕАЛИЗОВАН
public interface INewsCommentDAO {
    void addComment(NewsComment comment) throws DAOException;
    boolean deleteComment(int newsCommentId) throws DAOException;
    boolean deleteAllComments(String loggedVisitorEmail) throws DAOException;
}
