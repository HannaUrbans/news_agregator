package by.urban.web_project.service.impl;

import by.urban.web_project.model.News;
import by.urban.web_project.model.NewsImportance;
import by.urban.web_project.model.User;
import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.INewsDAO;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceException;

import java.util.List;

public class NewsServiceImpl implements INewsService {

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

    public List<User> getAuthorByNewsId(int newsId) throws ServiceException {
        try {
            return newsTool.getNewsAuthorsByNewsId(newsId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean changeFieldData(int newsId, News news) throws ServiceException {
        try {
            return newsTool.updateNewsArticle(newsId, news);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean addAuthorToNews(int newsId, int authId) throws ServiceException {
        try {
            return newsTool.addPrimaryAuthor(newsId, authId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean addCoauthorToNews(int coauthorId, int newsId) throws ServiceException {
        try {
            return newsTool.addCoauthor(coauthorId, newsId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
