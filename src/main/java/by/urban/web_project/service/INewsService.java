package by.urban.web_project.service;

import by.urban.web_project.bean.News;
import by.urban.web_project.bean.NewsImportance;

import java.util.List;

public interface INewsService {
    int addNewsToDatabase(News news) throws ServiceException;

    boolean deleteNewsFromDatabase(int newsId) throws ServiceException;

    List<News> getNewsByType(NewsImportance newsImportance) throws ServiceException;

    News getNewsFromDatabaseById(int newsId) throws ServiceException;

    List<News> getNewsList() throws ServiceException;

    List<News> getAuthorNewsList(int authorId) throws ServiceException;

    boolean changeFieldData(int newsId, News news) throws ServiceException;

    //методы для работы с категориями!!!
}