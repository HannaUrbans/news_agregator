package by.urban.web_project.dao;

import by.urban.web_project.model.News;

public interface NewsDAO {
    void addNews(News news) throws DAOException;

    boolean deleteNews(int newsId) throws DAOException;

    void changeNewsImageUrl(int newsId, String imageUrl) throws DAOException;

    void changeNewsBrief(int newsId, String brief) throws DAOException;

    void changeNewsText(int newsId, String newsText) throws DAOException;

}
