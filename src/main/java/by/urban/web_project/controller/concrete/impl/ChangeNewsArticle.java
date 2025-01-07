package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.bean.News;
import by.urban.web_project.bean.User;
import by.urban.web_project.bean.UserRole;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static by.urban.web_project.controller.utils.AuthPresenceUtil.checkAuthPresence;
import static by.urban.web_project.controller.utils.RolePresenceUtil.checkRolePresence;

public class ChangeNewsArticle implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ServiceException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        INewsService newsService = serviceFactory.getNewsService();

        Auth auth = (Auth) request.getSession().getAttribute("auth");
        checkAuthPresence(request, response, auth);
        checkRolePresence(request, response, UserRole.AUTHOR);

        //пришло из GoToChangeForm.java
        String newsIdString = (String) request.getSession().getAttribute("newsId");
        int newsId = Integer.valueOf(newsIdString);

        String newNewsTitle = request.getParameter("newNewsTitle");
        String newNewsBrief = request.getParameter("newNewsBrief");
        String newNewsContent = request.getParameter("newNewsContent");
        String newNewsCategory = request.getParameter("newNewsCategory");

        News news = newsService.getNewsFromDatabaseById(newsId);
        if (news == null) {
            request.getSession().setAttribute("changeArticleError", "Новость с указанным ID не найдена.");
            response.sendRedirect("Controller?command=SHOW_ALL_AUTHOR_NEWS");
            return;
        }

        news.updateFields(newNewsTitle, newNewsBrief, newNewsContent, newNewsCategory);

        //если текст, введенный в поле, слишком длинный, то ошибка возникает на уровне слоя дао, если ее не перехватить, то выскакивает 500 ошибка
        try {
        // проверка через слой дао, изменилось ли в базе данных поле/поля из новостей
        if (newsService.changeFieldData(newsId, news)) {
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
            request.getSession().setAttribute("changeArticleSuccess", "Статья успешно обновлена");
        }
        } catch (ServiceException e) {
            request.getSession().setAttribute("changeArticleError", "Ошибка при обновлении новости: " + e.getMessage());
        }

        response.sendRedirect("Controller?command=SHOW_ALL_AUTHOR_NEWS");
    }
}
