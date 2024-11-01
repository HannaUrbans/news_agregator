package by.urban.web_project.bean;

import java.io.Serializable;
import java.util.Objects;

public class News implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private NewsImportance importance;
	private String title;
	private String imageUrl;
	private String brief;
	private String newsText;

	public News() {
	}

	public News(int id, NewsImportance importance, String title, String imageUrl, String brief, String newsText) {
		this.id = id;
		this.importance = importance;
		this.title = title;
		this.imageUrl = imageUrl;
		this.brief = brief;
		this.newsText = newsText;
	}

	public int getId() {
		return id;
	}

	public NewsImportance getImportance() {
		return importance;
	}

	public String getTitle() {
		return title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getBrief() {
		return brief;
	}

	public String getNewsText() {
		return newsText;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, importance, title, brief);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		News other = (News) obj;
		return id == other.id && importance == other.importance && Objects.equals(title, other.title)
				&& Objects.equals(brief, other.brief);
	}

	@Override
	public String toString() {
		return "News {" + "id='" + id + '\'' + ", importance=" + importance + ", title='" + title + '\'' + ", brief='"
				+ brief + '\'' + '}';
	}
}
