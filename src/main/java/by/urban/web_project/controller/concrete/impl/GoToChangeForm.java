package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.bean.News;
import by.urban.web_project.bean.UserRole;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.urban.web_project.controller.utils.AuthPresenceUtil.checkAuthPresence;
import static by.urban.web_project.controller.utils.RolePresenceUtil.isAuthRoleValid;

public class GoToChangeForm implements Command {

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private INewsService newsService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Auth auth = (Auth) request.getSession(false).getAttribute("auth");
        // если не в сессии
        checkAuthPresence(request, response, auth);

        System.out.println("В сети находится: " + auth.toString());

        String formType = request.getParameter("formType");
        if (formType == null) {
            request.getSession().setAttribute("authError", "Не указан тип формы");
            response.sendRedirect("Controller?command=NO_SUCH_COMMAND");
            return;
        }

        // Проверка роли перед переходом на страницу
        if ("bio".equals(formType)) {
            if (!isAuthRoleValid(request, response, UserRole.AUTHOR)) {
                return;
            }
        }

        //newsId передавалось в URL, его нужно передать далее в ChangeNewsArticle.java
        String newsId = request.getParameter("newsId");
        try {
            if (newsId != null) {
                request.getSession().setAttribute("newsId", newsId);
                newsService = serviceFactory.getNewsService();
                News newsToEdit = newsService.getNewsFromDatabaseById(Integer.parseInt(newsId));
                if (newsToEdit != null) {
                    request.getSession().setAttribute("news", newsToEdit);
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(specifyPageAccordingToFormType(formType));
        dispatcher.forward(request, response);
    }

    /**
     * Метод для определения страницы для перехода в зависимости от типа формы
     *
     * @param formType - берется из скрытого поля формы на странице джсп (<input type="hidden" name="formType" value="account">)
     * @return стринговое значение адреса страницы, которое будет подставляться в request.getRequestDispatcher
     */
    private String specifyPageAccordingToFormType(String formType) {

        return switch (formType) {
            case "account" -> "/WEB-INF/jsp/change-user-data-pages/change-account.jsp";
            case "bio" -> "/WEB-INF/jsp/change-user-data-pages/change-bio-form.jsp";
            case "newsArticle" -> "/WEB-INF/jsp/change-news-article-form.jsp";
            default -> "";
        };
    }
}
