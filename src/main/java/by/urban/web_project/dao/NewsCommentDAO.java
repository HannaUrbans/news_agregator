package by.urban.web_project.dao;

import by.urban.web_project.model.NewsComment;

public interface NewsCommentDAO{
    void addComment(NewsComment comment) throws DAOException;
    boolean deleteComment(int newsCommentId) throws DAOException;
    boolean deleteAllComments(String loggedVisitorEmail) throws DAOException;
}
