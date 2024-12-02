package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.model.News;
import by.urban.web_project.model.NewsImportance;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class GoToIndexPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        INewsService newsService = serviceFactory.getNewsService();

        try {
            News breakingNews = newsService.getNewsByType(NewsImportance.BREAKING).get(0);
            News topNews = newsService.getNewsByType(NewsImportance.TOP).get(0);
            List<News> regularNewsList = newsService.getNewsByType(NewsImportance.REGULAR);

            if (breakingNews != null) {
                request.setAttribute("breakingNews", breakingNews); // выводим последнюю добавленную срочную новость
            }
            if (topNews != null) {
                request.setAttribute("topNews", topNews); // выводим последнюю добавленную топовую новость
            }

            request.setAttribute("regularNews", regularNewsList);

        } catch (ServiceException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Ошибка при получении новостей.");
        }

        System.out.println("Номер сессии при открытии главной страницы: " + request.getSession().getId());
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main-index.jsp");
        dispatcher.forward(request, response);
    }
}