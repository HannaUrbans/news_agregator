package by.urban.web_project.bean;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private String name;
    private UserRole userRole;


    public User(int id, String name, UserRole userRole) {
        this.id = id;
        this.name = name;
        this.userRole = userRole;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && userRole == user.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userRole);
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
