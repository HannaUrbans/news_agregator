package by.urban.web_project.model;

import java.io.Serializable;
import java.util.Objects;

public class News implements Serializable {
	private static final long serialVersionUID = 1L;

	private int newsId;
	private NewsImportance importance;
	private String title;
	private String imageUrl;
	private String brief;
	private String newsText;

	public News() {
	}

	public News(int newsId, NewsImportance importance, String title, String imageUrl, String brief, String newsText) {
		this.newsId = newsId;
		this.importance = importance;
		this.title = title;
		this.imageUrl = imageUrl;
		this.brief = brief;
		this.newsText = newsText;
	}

	public int getNewsId() {
		return newsId;
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

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public void setImportance(NewsImportance importance) {
		this.importance = importance;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public void setNewsText(String newsText) {
		this.newsText = newsText;
	}

	@Override
	public int hashCode() {
		return Objects.hash(newsId, importance, title, brief);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		News other = (News) obj;
		return newsId == other.newsId && importance == other.importance && Objects.equals(title, other.title)
				&& Objects.equals(brief, other.brief);
	}

	@Override
	public String toString() {
		return getClass().getName() + "{" + "id='" + newsId + '\'' + ", importance=" + importance + ", title='" + title + '\'' + ", brief='"
				+ brief + '\'' + '}';
	}
}
