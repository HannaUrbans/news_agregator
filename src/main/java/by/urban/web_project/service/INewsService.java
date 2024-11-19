package by.urban.web_project.service;

import by.urban.web_project.model.News;

import java.util.List;

public interface INewsService {
    void addRegularNews(News regularNews) throws ServiceException;

    void addTopNews(News topNews) throws ServiceException;

    void addBreakingNews(News breakingNews) throws ServiceException;

    News getBreakingNews() throws ServiceException;

    List<News> getAllBreakingNews() throws ServiceException;

    News getTopNews() throws ServiceException;

    List<News> getAllTopNews() throws ServiceException;

    List<News> getRegularNews() throws ServiceException;

}