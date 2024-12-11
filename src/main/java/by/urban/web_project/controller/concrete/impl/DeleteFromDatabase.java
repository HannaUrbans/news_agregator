package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.bean.UserRole;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.urban.web_project.controller.utils.AuthPresenceUtil.checkAuthPresence;
import static by.urban.web_project.controller.utils.RolePresenceUtil.checkRolePresence;

public class DeleteFromDatabase implements Command {
    private final IChangeProfileService updateTool;

    public DeleteFromDatabase() throws ServiceException {
        this.updateTool = ServiceFactory.getInstance().getChangeProfileService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        INewsService newsService = serviceFactory.getNewsService();

        Auth auth = (Auth) request.getSession(false).getAttribute("auth");
        // если не в сессии
        checkAuthPresence(request, response, auth);
        // если от другой роли
        checkRolePresence(request, response, UserRole.ADMIN);

        int newsId = Integer.parseInt(request.getParameter("newsId"));
        if (newsService.deleteNewsFromDatabase(newsId)) {
            request.getSession().setAttribute("deleteSuccessMessage", "Новость удалена");
        } else {
            request.getSession().setAttribute("deleteFailMessage", "При удалении новости произошла ошибка");
        }

        response.sendRedirect("Controller?command=SHOW_ALL_NEWS");
    }
}