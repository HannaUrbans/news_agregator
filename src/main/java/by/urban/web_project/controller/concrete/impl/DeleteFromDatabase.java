package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.model.UserRole;
import by.urban.web_project.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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

        //проверяем, что в сессии админ и что сессия жива (неявно, но если request.getSession().getAttribute(sessionAttribute) равно null, то сессия не жива
        if (!checkService.checkIfRoleAuthorizedForAction(request, response, "admin", UserRole.ADMIN)) {
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