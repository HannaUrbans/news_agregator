package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.model.News;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceFactory;
import by.urban.web_project.utils.SessionUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class GoToIndexPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        INewsService newsService = serviceFactory.getNewsService();

        News breakingNews = newsService.getBreakingNews();
        News topNews = newsService.getTopNews();
        List<News> regularNewsList = newsService.getRegularNews();

        if (breakingNews != null) {
            request.setAttribute("breakingNews", breakingNews); // выводим последнюю добавленную срочную новость
        }
        if (topNews != null) {
            request.setAttribute("topNews", topNews); // выводим последнюю добавленную топовую новость
        }

        request.setAttribute("regularNews", regularNewsList);

        SessionUtils.logCurrentVisitor(request);
        System.out.println("Номер сессии при открытии главной страницы: " + request.getSession().getId());
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main_index.jsp");
        dispatcher.forward(request, response);
    }
}