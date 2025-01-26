package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.News;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


public class ShowAllNews implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private INewsService newsService;
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        try {
            newsService = serviceFactory.getNewsService();
            List<News> newsList = newsService.getNewsList();
            request.getSession().setAttribute("newsList", newsList);
        } catch (ServiceException e) {
            e.printStackTrace();
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/all-news-page.jsp");
        dispatcher.forward(request, response);

    }
}
