package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.model.Auth;
import by.urban.web_project.model.News;
import by.urban.web_project.model.UserRole;
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

import static by.urban.web_project.controller.utils.AuthPresenceUtil.checkAuthPresence;
import static by.urban.web_project.controller.utils.RolePresenceUtil.isAuthRoleValid;


public class ShowAllAuthorNews implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Auth auth = (Auth) request.getSession(false).getAttribute("auth");

        // если не в сессии
        checkAuthPresence(request, response, auth);
        // если от другой роли
        if (!isAuthRoleValid(request, response, UserRole.AUTHOR)) {
            return;
        }

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            INewsService newsService = serviceFactory.getNewsService();

            int id = (int) request.getSession().getAttribute("id");
            List<News> newsAuthorList = newsService.getAuthorNewsList(id);

            //НЕ В СЕССИИ, А ПРОСТО В АТРИБУТАХ ПЕРЕДАЕМ
            request.setAttribute("newsAuthorList", newsAuthorList);
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при обработке запроса", e);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/author-news-page.jsp");
        dispatcher.forward(request, response);

    }
}
