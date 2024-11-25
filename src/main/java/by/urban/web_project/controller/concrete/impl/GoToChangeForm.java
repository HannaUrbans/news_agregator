package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.mockdb.NewsDatabase;
import by.urban.web_project.model.News;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GoToChangeForm implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String formType = request.getParameter("formType");
        if (formType == null) {
            response.sendRedirect("Controller?command=NO_SUCH_COMMAND");
        }

        String page = "";
        switch (formType) {
            case "password":
                page = "/WEB-INF/jsp/change-password-form.jsp";
                break;
            case "name":
                page = "/WEB-INF/jsp/change-name-form.jsp";
                break;
            case "bio":
                page = "/WEB-INF/jsp/change-bio-form.jsp";
                break;
            case "newsArticle":
                page = "/WEB-INF/jsp/change-news-article-form.jsp";
                break;
        }

        //newsId передавалось в URL, его нужно передать далее в ChangeNewsArticle.java
        String newsId = request.getParameter("newsId");
        if (newsId != null) {
            request.getSession().setAttribute("newsId", newsId);

            News newsToEdit = NewsDatabase.getNewsById(Integer.parseInt(newsId));
            if (newsToEdit != null) {
                request.getSession().setAttribute("news", newsToEdit);
            }
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
