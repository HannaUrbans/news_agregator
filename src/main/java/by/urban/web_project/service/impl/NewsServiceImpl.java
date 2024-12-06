package by.urban.web_project.service.impl;

import by.urban.web_project.bean.User;
import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.INewsDAO;
import by.urban.web_project.bean.News;
import by.urban.web_project.bean.NewsImportance;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceException;

import java.util.ArrayList;
import java.util.List;

//Former Logic Stub For Adding News To Main Page
public class NewsServiceImpl implements INewsService {
    private List<News> regularNewsList = new ArrayList<>();
    private List<News> topNewsList = new ArrayList<>();
    private List<News> breakingNewsList = new ArrayList<>();

    private final DAOFactory daoFactory;
    private final INewsDAO newsTool;

    public NewsServiceImpl() throws ServiceException {
        try {
            daoFactory = DAOFactory.getInstance();
            newsTool = daoFactory.getNewsDAO();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public int addNewsToDatabase(News news) throws ServiceException {
        try {
            return (newsTool.addNews(news));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean deleteNewsFromDatabase(int newsId) throws ServiceException {
        try {
            return newsTool.deleteNews(newsId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<News> getNewsByType(NewsImportance newsImportance) throws ServiceException {
        try {
            return newsTool.findNewsByType(newsImportance);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public News getNewsFromDatabaseById(int newsId) throws ServiceException {
        try {
            return newsTool.getNewsById(newsId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<News> getNewsList() throws ServiceException {
        try {
            return newsTool.getAllNews();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<News> getAuthorNewsList(int authorId) throws ServiceException {
        try {
            return newsTool.getAllNewsByAuthor(authorId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    public List<User> getAuthorByNewsId(int newsId) throws ServiceException{
        try {
            return newsTool.getNewsAuthorsByNewsId(newsId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean changeFieldData(int newsId, News news) throws ServiceException {
        try {
            return newsTool.changeNewsArticle(newsId, news);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean addAuthorToNews(int newsId, int authId) throws ServiceException {
        try {
            return newsTool.addInitialAuthor(newsId, authId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean addCoauthorToNews(int coauthorId, int newsId) throws ServiceException{
        try {
            return newsTool.addCoauthor(coauthorId, newsId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
