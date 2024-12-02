package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.model.News;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GoToNewsPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        INewsService newsService = serviceFactory.getNewsService();

        // получаем ID новости из параметров запроса
        String newsId = request.getParameter("newsId");
        // получаем новость по ID
        News news = newsService.getNewsFromDatabaseById(Integer.parseInt(newsId));

        if (news != null) {
            request.setAttribute("news", news);
        } else {
            request.setAttribute("errorMessage", "Новость не найдена.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/news-page.jsp");
        dispatcher.forward(request, response);
    }
}
