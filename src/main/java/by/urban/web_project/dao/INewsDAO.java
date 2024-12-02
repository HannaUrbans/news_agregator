package by.urban.web_project.dao;

import by.urban.web_project.model.News;
import by.urban.web_project.model.NewsImportance;
import by.urban.web_project.model.User;

import java.util.List;

public interface INewsDAO {
    News addNews(News news) throws DAOException;

    boolean deleteNews(int newsId) throws DAOException;

    List<News> getAllNews() throws DAOException;

    List<User> getNewsAuthorsByNewsId(int newsId) throws DAOException;

    News getNewsById(int id) throws DAOException;

    List<News> getAllNewsByAuthor(int authorId) throws DAOException;

    boolean changeNewsArticle(int newsId, News news) throws DAOException;

    List <News> findNewsByType(NewsImportance newsImportance) throws DAOException;

}
