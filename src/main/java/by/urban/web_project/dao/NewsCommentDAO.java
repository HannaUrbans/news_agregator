package by.urban.web_project.dao;

import by.urban.web_project.model.NewsComment;

public interface NewsCommentDAO{
    void addComment(NewsComment comment);
    boolean deleteComment(int newsCommentId);
    boolean deleteAllComments(String loggedVisitorEmail);
}
