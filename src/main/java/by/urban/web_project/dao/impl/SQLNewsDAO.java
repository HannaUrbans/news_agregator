package by.urban.web_project.dao.impl;

import by.urban.web_project.model.News;
import by.urban.web_project.dao.NewsDAO;

public class SQLNewsDAO implements NewsDAO {
    public void addNews(News news) {
        System.out.println(news.toString());
    }

    public boolean deleteNews(int newsId) {
        return true;
    }

    public void changeNewsImageUrl(int newsId, String imageUrl) {
        System.out.println(newsId);
        System.out.println(imageUrl);
    }

    public void changeNewsBrief(int newsId, String brief) {
        System.out.println(newsId);
        System.out.println(brief);
    }

    public void changeNewsText(int newsId, String newsText) {
        System.out.println(newsId);
        System.out.println(newsText);
    }

}
