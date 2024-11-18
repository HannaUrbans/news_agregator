package by.urban.web_project.service;

import by.urban.web_project.model.News;

import java.util.List;

public interface INewsService {
    News getBreakingNews();

    News getTopNews();

    List<News> getRegularNews();
}