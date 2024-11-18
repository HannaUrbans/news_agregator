package by.urban.web_project.dao;

import by.urban.web_project.model.News;

public interface NewsDAO {
    void addNews(News news);

    boolean deleteNews(int newsId);

    void changeNewsImageUrl(int newsId, String imageUrl);

    void changeNewsBrief(int newsId, String brief);

    void changeNewsText(int newsId, String newsText);

}
