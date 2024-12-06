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
import java.util.Collections;
import java.util.List;


public class ShowAllAuthorNews implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ServiceException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        INewsService newsService = serviceFactory.getNewsService();

        int id = (int) request.getSession().getAttribute("id");
        List<News> newsAuthorList = newsService.getAuthorNewsList(id);
        //выводится в обратном порядке добавления в список
        //убедись, что этот список тут нужен + мб можно заменить на order by ... desc
        Collections.reverse(newsAuthorList);

        request.getSession().setAttribute("newsAuthorList", newsAuthorList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/author-news-page.jsp");
        dispatcher.forward(request, response);

    }
}
