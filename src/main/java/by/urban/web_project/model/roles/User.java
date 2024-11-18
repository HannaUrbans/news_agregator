package by.urban.web_project.model.roles;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private final String email;
	private String password;
	private boolean isAuthor;

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.isAuthor = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAuthor() {
		return isAuthor;
	}

	public void setAuthor(boolean author) {
		isAuthor = author;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return isAuthor == user.isAuthor && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, email, password, isAuthor);
	}

	@Override
	public String toString() {
		return getClass().getName() + "{" +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", isAuthor=" + isAuthor +
				'}';
	}
}
