package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.mockdb.NewsDatabase;
import by.urban.web_project.model.News;
import by.urban.web_project.model.NewsImportance;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import by.urban.web_project.utils.ImageUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class AddNews implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, MessagingException, ServiceException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        INewsService newsService = serviceFactory.getNewsService();

        try {
            // Получаем параметры из запроса, которые в запрос передавались через форму
            int newsId = Integer.parseInt(request.getParameter("newsId"));
            String newsImportanceStr = request.getParameter("newsImportance");
            NewsImportance newsImportance = NewsImportance.valueOf(newsImportanceStr.toUpperCase());
            String newsTitle = request.getParameter("newsTitle");
            String newsBrief = request.getParameter("newsBrief");
            String newsArticle = request.getParameter("newsArticle");

            // Получаем файл изображения через интерфейс Part
            Part newsPicPart = request.getPart("newsPic");

            String fileName = null;
            if (newsPicPart != null && newsPicPart.getSize() > 0) {
                // Сохраняем изображение и получаем его имя
                fileName = ImageUtils.saveImage(newsPicPart);
            }
            System.out.println("fileName " + fileName);

            // Получаем email пользователя из сессии
            String email = (String) request.getSession().getAttribute("email");

            // Формируем объект News
            News newNews = new News();
            newNews.setNewsId(newsId);
            newNews.setNewsAuthor(email);
            newNews.setImportance(newsImportance);
            newNews.setTitle(newsTitle);
            newNews.setBrief(newsBrief);
            newNews.setNewsText(newsArticle);

            // Формируем URL для изображения (путь относительно корня web-приложения)
            String imageUrl = null;
            if (fileName != null) {
                imageUrl = "/images/" + fileName; // Путь, по которому изображение будет доступно
            }
            newNews.setImageUrl(imageUrl);

            // Добавляем новость в соответствующую категорию
            switch (newsImportance) {
                case REGULAR:
                    newsService.addRegularNews(newNews);
                    break;
                case TOP:
                    newsService.addTopNews(newNews);
                    break;
                case BREAKING:
                    newsService.addBreakingNews(newNews);
                    break;
            }

            // Добавляем новость в заглушку базы данных
            NewsDatabase.addNews(newNews);

            // Получаем список новостей автора
            List<News> alteredAuthorNewsList = NewsDatabase.getNewsByAuthor(email);
            Collections.reverse(alteredAuthorNewsList);

            // Обновляем атрибут в сессии с новыми новостями автора
            request.getSession().setAttribute("authorNewsList", alteredAuthorNewsList);

            // Перенаправляем на страницу с новостями автора
            response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");

            // !!! не забыть проверить потом в джсп, выводятся ли эти ошибки
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Неверный формат данных");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Ошибка при добавлении новости.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }
}
