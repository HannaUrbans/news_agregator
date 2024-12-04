package by.urban.web_project.bean;

import java.io.Serializable;
import java.util.Objects;

public class NewsComment implements Serializable {
    private static final long serialVersionUID = 1L;

    private int commentId;
    private int commentedNewsId;
    //это может быть как автор, так и пользователь
    private String loggedVisitorEmail;
    private String commentText;
    private String dateCreated;

    public NewsComment(int commentId, int commentedNewsId, String loggedVisitorEmail, String commentText, String dateCreated) {
        this.commentId = commentId;
        this.commentedNewsId = commentedNewsId;
        this.loggedVisitorEmail = loggedVisitorEmail;
        this.commentText = commentText;
        this.dateCreated = dateCreated;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getCommentedNewsId() {
        return commentedNewsId;
    }

    public void setCommentedNewsId(int commentedNewsId) {
        this.commentedNewsId = commentedNewsId;
    }

    public String getLoggedVisitorEmail() {
        return loggedVisitorEmail;
    }

    public void setLoggedVisitorEmail(String loggedVisitorEmail) {
        this.loggedVisitorEmail = loggedVisitorEmail;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsComment that = (NewsComment) o;
        return commentId == that.commentId && commentedNewsId == that.commentedNewsId && Objects.equals(loggedVisitorEmail, that.loggedVisitorEmail) && Objects.equals(commentText, that.commentText) && Objects.equals(dateCreated, that.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, commentedNewsId, loggedVisitorEmail, commentText, dateCreated);
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "commentId=" + commentId +
                ", commentedNewsId=" + commentedNewsId +
                ", loggedVisitorEmail='" + loggedVisitorEmail + '\'' +
                ", commentText='" + commentText + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }
}
