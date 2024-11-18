package by.urban.web_project.model.roles;

import java.io.Serializable;
import java.util.Objects;

public class Author extends User implements Serializable{
    private static final long serialVersionUID = 1L;

    private String bio;

    public Author(String name, String email, String password, String bio) {
        super(name, email, password);
        this.setAuthor(true);
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Author author = (Author) o;
        return Objects.equals(bio, author.bio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bio);
    }

    @Override
    public String toString() {
        return super.toString() + ", {" + getClass().getName() +
                "bio='" + bio + '\'' +
                '}';
    }
}
