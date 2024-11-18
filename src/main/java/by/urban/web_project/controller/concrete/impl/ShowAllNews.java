package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.mockdb.NewsDatabase;
import by.urban.web_project.model.News;
import by.urban.web_project.utils.ImageUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static by.urban.web_project.mockdb.NewsDatabase.getAllNews;

public class ShowAllNews implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, MessagingException {
        List<News> newsList = getAllNews();
        //выводится в обратном порядке добавления в список
        Collections.reverse(newsList);

        request.getSession().setAttribute("newsList", newsList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/AllNewsPage.jsp");
        dispatcher.forward(request, response);

    }
}
