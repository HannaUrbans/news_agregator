package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.mockdb.NewsDatabase;
import by.urban.web_project.model.News;
import by.urban.web_project.model.NewsImportance;
import by.urban.web_project.utils.ImageUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.urban.web_project.mockdb.NewsDatabase.getAllNews;

public class AddNews implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, MessagingException {
        int newsId = 13;

        String newsImportanceStr = request.getParameter("newsImportance");
        NewsImportance newsImportance = NewsImportance.REGULAR;
        try {
            newsImportance = NewsImportance.valueOf(newsImportanceStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("newsImportance", newsImportance);

        String newsTitle = request.getParameter("newsTitle");
        request.getSession().setAttribute("newsTitle", newsTitle);

        Part newsPicPart = request.getPart("newsPic");
        String directory = request.getServletContext().getRealPath("/images");
        String fileName = ImageUtils.saveImage(newsPicPart, directory);
        request.getSession().setAttribute("imageUrl", "/images/" + fileName);

        String newsBrief = request.getParameter("newsBrief");
        request.getSession().setAttribute("newsBrief", newsBrief);

        String newsArticle = request.getParameter("newsArticle");
        request.getSession().setAttribute("newsArticle", newsArticle);

        // Формируем объект News
        News newNews = new News();
        newNews.setNewsId(newsId++);
        newNews.setImportance(newsImportance);
        newNews.setTitle(newsTitle);
        newNews.setImageUrl("/images/" + fileName);
        newNews.setBrief(newsBrief);
        newNews.setNewsText(newsArticle);

        String email = (String) request.getSession().getAttribute("email");

        // Добавляем новость в заглушку БД
        NewsDatabase.addNews(newNews);
        //Добавляем новость в список новостей автора (пока заглушка БД)
        NewsDatabase.addNewsForAuthor(email, newNews);

        List<News> authorNewsList = NewsDatabase.getNewsByAuthor(email);
        request.getSession().setAttribute("newsAuthorsList", authorNewsList);

        response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");

    }
}
