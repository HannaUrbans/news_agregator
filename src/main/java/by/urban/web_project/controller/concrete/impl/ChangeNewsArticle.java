package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.model.News;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChangeNewsArticle implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String newNewsTitle = request.getParameter("newNewsTitle");
        String newNewsBrief = request.getParameter("newNewsBrief");
        String newNewsContent = request.getParameter("newNewsContent");
        String newNewsCategory = request.getParameter("newNewsCategory");

        //пришло из GoToChangeForm.java
        String newsIdString = (String) request.getSession().getAttribute("newsId");
        int newsId = Integer.valueOf(newsIdString);

        List<News> authorNewsList = (ArrayList<News>) request.getSession().getAttribute("authorNewsList");

        for (News news : authorNewsList) {
            if (news.getNewsId() == newsId) {
                if (newNewsTitle != null && !newNewsTitle.trim().isEmpty()) {
                    news.setTitle(newNewsTitle);
                }
                if (newNewsBrief != null && !newNewsBrief.trim().isEmpty()) {
                    news.setBrief(newNewsBrief);
                }
                if (newNewsContent != null && !newNewsContent.trim().isEmpty()) {
                    news.setContent(newNewsContent);
                }
                if (newNewsCategory != null && !newNewsCategory.trim().isEmpty()) {
                    news.setCategory(newNewsCategory);
                }


                request.getSession().setAttribute("authorNewsList", authorNewsList);
                request.getSession().setAttribute("changeArticleSuccess", "Статья успешно обновлена");
            } else {
                request.getSession().setAttribute("changeArticleError", "Все поля обязательны к заполнению");
            }
        }

        response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");

    }
}
