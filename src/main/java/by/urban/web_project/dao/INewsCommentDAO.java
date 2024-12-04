package by.urban.web_project.dao;

import by.urban.web_project.bean.NewsComment;

public interface INewsCommentDAO {
    void addComment(NewsComment comment) throws DAOException;
    boolean deleteComment(int newsCommentId) throws DAOException;
    boolean deleteAllComments(String loggedVisitorEmail) throws DAOException;
}
