package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.bean.News;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GoToChangeForm implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ServiceException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        INewsService newsService = serviceFactory.getNewsService();

        //проверяем, жива ли сессия
        if (request.getSession(false) == null){
            request.setAttribute("errorMessage", "Вы не авторизованы.");
            response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
        }

        String formType = request.getParameter("formType");
        if (formType == null) {
            response.sendRedirect("Controller?command=NO_SUCH_COMMAND");
        }

        String page = "";
        switch (formType) {
            case "account":
                page = "/WEB-INF/jsp/change-user-data-pages/change-account.jsp";
                break;
            case "bio":
                page = "/WEB-INF/jsp/change-user-data-pages/change-bio-form.jsp";
                break;
            case "newsArticle":
                page = "/WEB-INF/jsp/change-news-article-form.jsp";
                break;
        }

        //newsId передавалось в URL, его нужно передать далее в ChangeNewsArticle.java
        String newsId = request.getParameter("newsId");
        if (newsId != null) {
            request.getSession().setAttribute("newsId", newsId);

            News newsToEdit = newsService.getNewsFromDatabaseById(Integer.parseInt(newsId));
            if (newsToEdit != null) {
                request.getSession().setAttribute("news", newsToEdit);
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
