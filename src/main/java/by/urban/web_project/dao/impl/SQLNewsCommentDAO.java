package by.urban.web_project.dao.impl;

import by.urban.web_project.model.NewsComment;
import by.urban.web_project.dao.NewsCommentDAO;

public class SQLNewsCommentDAO implements NewsCommentDAO {
    public void addComment(NewsComment comment) {
        System.out.println(comment);
    }

    public boolean deleteComment(int newsCommentId) {
        return true;
    }

    public boolean deleteAllComments(String loggedVisitorEmail) {
        return true;
    }

}
