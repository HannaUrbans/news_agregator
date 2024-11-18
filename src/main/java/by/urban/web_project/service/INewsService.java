package by.urban.web_project.service;

import by.urban.web_project.model.News;

import java.util.List;

public interface INewsService {
    News getBreakingNews() throws ServiceException;

    News getTopNews() throws ServiceException;

    List<News> getRegularNews() throws ServiceException;
}