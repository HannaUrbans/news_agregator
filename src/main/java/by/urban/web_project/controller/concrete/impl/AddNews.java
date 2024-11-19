package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.mockdb.NewsDatabase;
import by.urban.web_project.model.News;
import by.urban.web_project.model.NewsImportance;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import by.urban.web_project.utils.ImageUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class AddNews implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, MessagingException, ServiceException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        INewsService newsService = serviceFactory.getNewsService();

        int newsId = Integer.parseInt(request.getParameter("newsId"));

        String newsImportanceStr = request.getParameter("newsImportance");
        System.out.println("Получено значение для NewsImportance: " + newsImportanceStr);

        NewsImportance newsImportance = null;  // Значение по умолчанию
        try {
            newsImportance = NewsImportance.valueOf(newsImportanceStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка преобразования в NewsImportance: " + newsImportanceStr);
            e.printStackTrace();
        }

        request.getSession().setAttribute("newsId", newsId);

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

        String email = (String) request.getSession().getAttribute("email");

        // Формируем объект News
        News newNews = new News();
        newNews.setNewsId(newsId);
        newNews.setNewsAuthor(email);
        newNews.setImportance(newsImportance);
        newNews.setTitle(newsTitle);
        newNews.setImageUrl("/images/" + fileName);
        newNews.setBrief(newsBrief);
        newNews.setNewsText(newsArticle);
        System.out.println(newNews.getImportance());
        if (newNews.getImportance().equals(NewsImportance.REGULAR)) {
            newsService.addRegularNews(newNews);
        } else if (newNews.getImportance().equals(NewsImportance.TOP)) {
            newsService.addTopNews(newNews);
        } else {
            newsService.addBreakingNews(newNews);
        }
        // Добавляем новость в заглушку БД
        NewsDatabase.addNews(newNews);

        List<News> alteredAuthorNewsList = NewsDatabase.getNewsByAuthor(email);

        Collections.reverse(alteredAuthorNewsList);
        System.out.println("alteredAuthorNewsList size: " + alteredAuthorNewsList.size());
        //обновляем атрибут в сессии
        request.getSession().setAttribute("authorNewsList", alteredAuthorNewsList);

        response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");

    }
}
