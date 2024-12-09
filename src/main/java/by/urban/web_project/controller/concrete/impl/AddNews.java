package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.bean.News;
import by.urban.web_project.bean.NewsImportance;
import by.urban.web_project.bean.UserRole;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import by.urban.web_project.utils.ImageUtils;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.List;

import static by.urban.web_project.controller.utils.UrlFormatterUtil.formatRedirectUrl;

public class AddNews implements Command {

    public AddNews() throws ServiceException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException, ServletException {
        News newNews = new News();
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        INewsService newsService = serviceFactory.getNewsService();

        Auth auth = (Auth) request.getSession(false).getAttribute("auth");
        UserRole role = UserRole.valueOf(((String) request.getSession().getAttribute("role")).toUpperCase());
        // если не в сессии
        if (auth == null) {
            System.out.println("Пользователь не залогинен и пытается открыть страницу добавления новостей");
            request.getSession().setAttribute("authError", "У Вас недостаточно прав для посещения этой страницы");
            response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
            return;
        }

        // если от другой роли
        if (!UserRole.AUTHOR.equals(auth.getRole())) {
            System.out.println("Пользователь пытается войти на страницу добавления новостей не своей роли");
            request.getSession().setAttribute("authError", "У Вас недостаточно прав для посещения этой страницы");
            response.sendRedirect("Controller?command=" + formatRedirectUrl(auth.getRole()));
            return;
        }

        try {
            // Получаем параметры из запроса, которые в запрос передавались через форму
            String newsImportanceStr = request.getParameter("newsImportance");
            NewsImportance newsImportance = NewsImportance.valueOf(newsImportanceStr.toUpperCase());
            String newsTitle = request.getParameter("newsTitle");
            String newsBrief = request.getParameter("newsBrief");
            String newsContent = request.getParameter("newsContent");
            String newsCategory = request.getParameter("newsCategory");
            // Получаем файл изображения через интерфейс Part
            Part newsPicPart = request.getPart("newsPic");

            // Сохранение изображения
            String fileName = null;
            ServletContext context = request.getServletContext();
            if (newsPicPart != null && newsPicPart.getSize() > 0) {
                fileName = ImageUtils.saveImage(newsPicPart, context);
            }

            // Формируем объект News
            newNews.setImportance(newsImportance);
            newNews.setTitle(newsTitle);
            newNews.setBrief(newsBrief);
            newNews.setContent(newsContent);
            newNews.setCategory(newsCategory);
            newNews.setImageUrl(fileName);

            int newNewsId = newsService.addNewsToDatabase(newNews);
            newsService.addAuthorToNews(newNewsId, auth.getId());
            //request.setAttribute("newsId", newNewsId);


            List<News> newsList = newsService.getNewsList();
            newsList.add(newNews);
            request.getSession().setAttribute("newsList", newsList);
            switch (newNews.getImportance()){
                case NewsImportance.BREAKING:
                    request.getSession().setAttribute("breakingNews", newNews);
                    break;
            }


            request.getSession().setAttribute("addNewsSuccess", "Новость успешно добавлена");
            // Перенаправляем на страницу с новостями автора
            response.sendRedirect("Controller?command=SHOW_ALL_AUTHOR_NEWS");

            // !!! не забыть проверить потом в джсп, выводятся ли эти ошибки
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

            request.getSession().setAttribute("newNews", newNews);
            request.getSession().setAttribute("addNewsError", "Неверный формат данных");
            request.getRequestDispatcher("/WEB-INF/jsp/add-news-form-page.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("newNews", newNews);
            request.getSession().setAttribute("errorMessage", "Ошибка при добавлении новости.");
            request.getRequestDispatcher("/WEB-INF/jsp/add-news-form-page.jsp").forward(request, response);
        }
    }
}
