package by.urban.web_project.dao.impl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.NewsDAO;
import by.urban.web_project.model.News;

public class SQLNewsDAO implements NewsDAO {
    public void addNews(News news) throws DAOException {
        System.out.println(news.toString());
    }

    public boolean deleteNews(int newsId) throws DAOException {
        return true;
    }

    public void changeNewsImageUrl(int newsId, String imageUrl) throws DAOException {
        System.out.println(newsId);
        System.out.println(imageUrl);
    }

    public void changeNewsBrief(int newsId, String brief) throws DAOException {
        System.out.println(newsId);
        System.out.println(brief);
    }

    public void changeNewsText(int newsId, String newsText) throws DAOException {
        System.out.println(newsId);
        System.out.println(newsText);
    }

}
