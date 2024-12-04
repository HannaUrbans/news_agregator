package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.bean.News;
import by.urban.web_project.bean.NewsImportance;
import by.urban.web_project.bean.UserRole;
import by.urban.web_project.service.ICheckService;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import by.urban.web_project.utils.ImageUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class AddNews implements Command {

    public AddNews() throws ServiceException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException, ServletException {

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ICheckService checkService = serviceFactory.getCheckService();
        INewsService newsService = serviceFactory.getNewsService();

        //проверяем, что в сессии автор и что сессия жива (неявно, но если request.getSession().getAttribute(sessionAttribute) равно null, то сессия не жива
        if (!checkService.checkIfRoleAuthorizedForAction(request, response, "author", UserRole.AUTHOR)) {
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

            String fileName = null;
            if (newsPicPart != null && newsPicPart.getSize() > 0) {
                // Сохраняем изображение и получаем его имя
                fileName = ImageUtils.saveImage(newsPicPart);
            }
            System.out.println("fileName " + fileName);

            // Формируем объект News
            News newNews = new News();
            newNews.setImportance(newsImportance);
            newNews.setTitle(newsTitle);
            newNews.setBrief(newsBrief);
            newNews.setContent(newsContent);
            newNews.setCategory(newsCategory);

            // Формируем URL для изображения (путь относительно корня web-приложения)
            String imageUrl = null;
            if (fileName != null) {
                imageUrl = "/images/" + fileName; // Путь, по которому изображение будет доступно
            }
            newNews.setImageUrl(imageUrl);


            int id = (int) request.getSession().getAttribute("id");

            List<News> alteredAuthorNewsList = newsService.getAuthorNewsList(id);
            Collections.reverse(alteredAuthorNewsList);

            // Обновляем атрибут в сессии с новыми новостями автора
            request.getSession().setAttribute("authorNewsList", alteredAuthorNewsList);

            // Перенаправляем на страницу с новостями автора
            response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");

            // !!! не забыть проверить потом в джсп, выводятся ли эти ошибки
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Неверный формат данных");
            response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Ошибка при добавлении новости.");
            response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");
        }
    }
}
