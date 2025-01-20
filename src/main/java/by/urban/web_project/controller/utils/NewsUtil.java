package by.urban.web_project.controller.utils;

import by.urban.web_project.bean.News;
import by.urban.web_project.bean.NewsImportance;
import by.urban.web_project.bean.User;
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

public class NewsUtil {

    public static void addNewsToSession(HttpServletRequest request, News newNews, List<News> newsList){

       //добавляем обновленный список новостей (с учетом добавленной только что новости) в атрибуты
       request.getSession().setAttribute("newsList", newsList);

       //в зависимости от степени важности, добавляем еще и в соответствующий список
       switch (newNews.getImportance()){
           case NewsImportance.BREAKING:
               request.getSession().setAttribute("breakingNews", newNews);
               break;
           case NewsImportance.TOP:
               request.getSession().setAttribute("topNews", newNews);
               break;
       }
   }

    public static News createNewsFromForm(HttpServletRequest request) {
        News newNews = new News();

        // Формируем объект News параметрами из запроса, которые передавались через форму
        newNews.setImportance(NewsImportance.valueOf(request.getParameter("newsImportance").toUpperCase()));
        newNews.setTitle(request.getParameter("newsTitle"));
        newNews.setBrief(request.getParameter("newsBrief"));
        newNews.setContent(request.getParameter("newsContent"));
        newNews.setCategory(request.getParameter("newsCategory"));
        newNews.setImageUrl(createImageUrl(request));

        return newNews;
    }

    private static String createImageUrl(HttpServletRequest request) {
        String imageUrl = null;

        try {
            // Получаем файл изображения через интерфейс Part
            Part newsPicPart = request.getPart("newsPic");

            if (newsPicPart != null && newsPicPart.getSize() > 0) {
                // Сохранение изображения
                ServletContext context = request.getServletContext();
                imageUrl = ImageUtils.saveImage(newsPicPart, context);
            }

        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }

        return imageUrl;
    }

    public static void checkNewsExists(HttpServletRequest request, HttpServletResponse response, News news) throws IOException {
        if (news == null) {
            request.getSession().setAttribute("changeArticleError", "Новость с указанным ID не найдена.");
            response.sendRedirect("Controller?command=SHOW_ALL_AUTHOR_NEWS");
        }
    }

    public static void addCoauthorIfNeeded(int authorId, int newsId, INewsService newsService) throws ServiceException {
        List<User> newsAuthors = newsService.getAuthorByNewsId(newsId);
        for (User newsAuthor : newsAuthors) {
            if (newsAuthor.getId() != authorId) {
                newsService.addCoauthorToNews(authorId, newsId);
            }
        }
    }

}
