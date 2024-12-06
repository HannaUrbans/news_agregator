package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.bean.News;
import by.urban.web_project.bean.User;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class ChangeNewsArticle implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ServiceException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        INewsService newsService = serviceFactory.getNewsService();

        //пришло из GoToChangeForm.java
        String newsIdString = (String) request.getSession().getAttribute("newsId");
        int newsId = Integer.valueOf(newsIdString);

        Auth auth = (Auth) request.getSession().getAttribute("auth");

        String newNewsTitle = request.getParameter("newNewsTitle");
        String newNewsBrief = request.getParameter("newNewsBrief");
        String newNewsContent = request.getParameter("newNewsContent");
        String newNewsCategory = request.getParameter("newNewsCategory");

        News news = newsService.getNewsFromDatabaseById(newsId);
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

            if (newsService.changeFieldData(newsId, news)) {
                System.out.println("Новость изменена");
            } else {
                System.out.println("Произошла ошибка при изменении новости");
            }

            //добавить автора если id, закрепленное за автором из новости, отличается от id из сессии
            // к тому моменту как мы изменяем новость, авторов может быть уже несколько
            List<User> newsAuthors = newsService.getAuthorByNewsId(newsId);
            System.out.println(newsAuthors.toString());
            for (User newsAuthor : newsAuthors) {
                if (newsAuthor.getId() != auth.getId()) {
                    if (newsService.addCoauthorToNews(auth.getId(), newsId)) {
                        System.out.println("Соавтор добавлен");
                    } else {
                        System.out.println("Произошла ошибка при добавлении соавтора");
                    }
                }
            }

            //request.getSession().setAttribute("authorNewsList", authorNewsList);
            request.getSession().setAttribute("changeArticleSuccess", "Статья успешно обновлена");
        } else {
            request.getSession().setAttribute("changeArticleError", "Все поля обязательны к заполнению");

        }

        response.sendRedirect("Controller?command=SHOW_ALL_AUTHOR_NEWS");

    }
}
