package by.urban.web_project.model;

import java.io.Serializable;
import java.util.Objects;

public class News implements Serializable {
	private static final long serialVersionUID = 1L;

	private int newsId;
	private String newsAuthor;
	private NewsImportance importance;
	private String title;
	private String imageUrl;
	private String brief;
	private String newsText;

	public News() {
	}

	public News(int newsId, String newsAuthor, NewsImportance importance, String title, String imageUrl, String brief, String newsText) {
		this.newsId = newsId;
		this.newsAuthor = newsAuthor;
		this.importance = importance;
		this.title = title;
		this.imageUrl = imageUrl;
		this.brief = brief;
		this.newsText = newsText;
	}

	public int getNewsId() {
		return newsId;
	}

	public String getNewsAuthor() {
		return newsAuthor;
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

	public void setNewsAuthor(String newsAuthor) {
		this.newsAuthor = newsAuthor;
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		News news = (News) o;
		return newsId == news.newsId && Objects.equals(newsAuthor, news.newsAuthor) && importance == news.importance && Objects.equals(title, news.title) && Objects.equals(imageUrl, news.imageUrl) && Objects.equals(brief, news.brief) && Objects.equals(newsText, news.newsText);
	}

	@Override
	public int hashCode() {
		return Objects.hash(newsId, newsAuthor, importance, title, imageUrl, brief, newsText);
	}

	@Override
	public String toString() {
		return getClass().getName() +"{" +
				"newsId=" + newsId +
				", newsAuthor='" + newsAuthor + '\'' +
				", importance=" + importance +
				", title='" + title + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				", brief='" + brief + '\'' +
				", newsText='" + newsText + '\'' +
				'}';
	}
}
