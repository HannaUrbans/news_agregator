package by.urban.web_project.dao.impl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.NewsCommentDAO;
import by.urban.web_project.model.NewsComment;

public class SQLNewsCommentDAO implements NewsCommentDAO {
    public void addComment(NewsComment comment) throws DAOException {
        System.out.println(comment);
    }

    public boolean deleteComment(int newsCommentId) throws DAOException {
        return true;
    }

    public boolean deleteAllComments(String loggedVisitorEmail) throws DAOException {
        return true;
    }

}
