package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.bean.UserRole;
import by.urban.web_project.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.urban.web_project.controller.utils.UrlFormatterUtil.formatRedirectUrl;

public class DeleteFromDatabase implements Command {
    private final IChangeProfileService updateTool;

    public DeleteFromDatabase() throws ServiceException {
        this.updateTool = ServiceFactory.getInstance().getChangeProfileService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ICheckService checkService = serviceFactory.getCheckService();
        INewsService newsService = serviceFactory.getNewsService();

        Auth auth = (Auth) request.getSession(false).getAttribute("auth");
        // если не в сессии
        if (auth == null) {
            System.out.println("Пользователь не залогинен и пытается открыть страницу личного кабинета");
            request.getSession().setAttribute("authError", "У Вас недостаточно прав для посещения этой страницы");
            response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
            return;
        }

        // если от другой роли
        if (!UserRole.ADMIN.equals(auth.getRole())) {
            System.out.println("Пользователь пытается войти в личный кабинет не своей роли");
            request.getSession().setAttribute("authError", "У Вас недостаточно прав для посещения этой страницы");
            response.sendRedirect("Controller?command=" + formatRedirectUrl(auth.getRole()));
            return;
        }

        int newsId = Integer.parseInt(request.getParameter("newsId"));
        if (newsService.deleteNewsFromDatabase(newsId)) {
            request.getSession().setAttribute("deleteSuccessMessage", "Новость удалена");
        } else {
            request.getSession().setAttribute("deleteFailMessage", "При удалении новости произошла ошибка");
        }

      response.sendRedirect("Controller?command=SHOW_ALL_NEWS");
}
}