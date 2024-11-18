package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.mockdb.NewsDatabase;
import by.urban.web_project.model.News;
import by.urban.web_project.utils.SessionUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GoToNewsPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // получаем ID новости из параметров запроса
        String newsId = request.getParameter("newsId");
        // получаем новость по ID
        News news = NewsDatabase.getNewsById(Integer.valueOf(newsId));

        if (news != null) {
            request.setAttribute("news", news);
        } else {
            request.setAttribute("errorMessage", "Новость не найдена.");
        }

        SessionUtils.logCurrentVisitor(request);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/NewsPage.jsp");
        dispatcher.forward(request, response);
    }
}
