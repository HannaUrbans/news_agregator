package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.model.News;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import jakarta.mail.MessagingException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class ShowAllNews implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ServiceException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        INewsService newsService = serviceFactory.getNewsService();

        List<News> newsList = newsService.getNewsList();
        //выводится в обратном порядке добавления в список
        //убедись, что этот список тут нужен + мб можно заменить на order by ... desc
        Collections.reverse(newsList);

        request.getSession().setAttribute("newsList", newsList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/all-news-page.jsp");
        dispatcher.forward(request, response);

    }
}
