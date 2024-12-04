package by.urban.web_project.bean;

import java.io.Serializable;
import java.util.Objects;

public class Session implements Serializable {
    private static final long serialVersionUID = 1L;

    private int sessionId;
    private String email;
    private String dateCreated;
    private String lastAccessed;    // Дата последнего доступа (для отслеживания активности)


    public Session(int sessionId, String email, String dateCreated, String lastAccessed) {
        this.sessionId = sessionId;
        this.email = email;
        this.dateCreated = dateCreated;
        this.lastAccessed = lastAccessed;
    }


    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(String lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return sessionId == session.sessionId &&
                Objects.equals(email, session.email) &&
                Objects.equals(dateCreated, session.dateCreated) &&
                Objects.equals(lastAccessed, session.lastAccessed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, email, dateCreated, lastAccessed);
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId=" + sessionId +
                ", email='" + email + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", lastAccessed='" + lastAccessed + '\'' +
                '}';
    }
}
