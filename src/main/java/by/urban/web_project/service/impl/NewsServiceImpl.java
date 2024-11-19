package by.urban.web_project.service.impl;

import by.urban.web_project.mockdb.NewsDatabase;
import by.urban.web_project.model.News;
import by.urban.web_project.model.NewsImportance;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceException;

import java.util.ArrayList;
import java.util.List;

//Logic Stub For Adding News To Main Page
public class NewsServiceImpl implements INewsService {
    private List<News> regularNewsList = new ArrayList<>();
    private List<News> topNewsList = new ArrayList<>();
    private List<News> breakingNewsList = new ArrayList<>();

    public void addRegularNews(News regularNews) {
        regularNewsList.add(regularNews);
    }

    public void addTopNews(News topNews) {
        topNewsList.add(topNews);
    }

    public void addBreakingNews(News breakingNews) {
        breakingNewsList.add(breakingNews);
    }

    //только одна breaking новость для отображения на главной
    public News getBreakingNews() throws ServiceException {
        // вытягиваем новости из класса-имитации базы данных
        List<News> news = NewsDatabase.getAllNews();
        News lastBreakingNews = null;
        for (News item : news) {
            if (item.getImportance() == NewsImportance.BREAKING) {
                lastBreakingNews = item; // вернуть последнюю добавленную срочную новость
            }
        }
        return lastBreakingNews;
    }

    //все breaking новости
    public List<News> getAllBreakingNews() throws ServiceException {
        // вытягиваем новости из класса-имитации базы данных
        List<News> news = NewsDatabase.getAllNews();
        for (News item : news) {
            if (item.getImportance() == NewsImportance.BREAKING) {
                breakingNewsList.add(item);
            }
        }
        return breakingNewsList;
    }

    //только одна top новость для отображения на главной
    public News getTopNews() throws ServiceException {
        News lastTopNews = null;
        List<News> news = NewsDatabase.getAllNews();
        for (News item : news) {
            if (item.getImportance() == NewsImportance.TOP) {
                lastTopNews = item; // вернуть последнюю добавленную топовую новость
            }
        }
        return lastTopNews;
    }

    //все top новости
    public List<News> getAllTopNews() throws ServiceException {
        List<News> news = NewsDatabase.getAllNews();
        for (News item : news) {
            if (item.getImportance() == NewsImportance.TOP) {
                topNewsList.add(item); // вернуть первую найденную топовую новость
            }
        }
        return topNewsList;
    }

    public List<News> getRegularNews() throws ServiceException {
        List<News> news = NewsDatabase.getAllNews();
        for (News item : news) {
            if (item.getImportance() == NewsImportance.REGULAR) {
                regularNewsList.add(item);
            }
        }
        return regularNewsList;
    }
}
