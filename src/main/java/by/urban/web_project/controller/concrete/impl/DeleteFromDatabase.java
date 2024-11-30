package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.model.User;
import by.urban.web_project.model.UserRole;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.ICheckService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import by.urban.web_project.utils.ProfileFieldToChange;
import by.urban.web_project.utils.UpdateUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteFromDatabase implements Command {
    private final IChangeProfileService updateTool;

    public DeleteFromDatabase() throws ServiceException {
        this.updateTool = ServiceFactory.getInstance().getChangeProfileService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ICheckService checkService = serviceFactory.getCheckService();

        //проверяем, что в сессии админ и что сессия жива (неявно, но если request.getSession().getAttribute(sessionAttribute) равно null, то сессия не жива
        if (!checkService.checkIfRoleAuthorizedForAction(request, response, "admin", UserRole.ADMIN)) {
            return;
        }




        String newPassword = request.getParameter("newPassword");
        User user = (User) request.getSession().getAttribute("user");
        User author = (User) request.getSession().getAttribute("author");

        if (newPassword != null && !newPassword.trim().isEmpty()) {
            boolean updateSuccess = false;

            // Используем утилитный метод для обновления пароля
            if (author != null) {
                updateSuccess = UpdateUtils.updateProfileField(author, newPassword, ProfileFieldToChange.PASSWORD, updateTool); // Обновляем пароль
            } else if (user != null) {
                updateSuccess = UpdateUtils.updateProfileField(user, newPassword, ProfileFieldToChange.PASSWORD, updateTool); // Обновляем пароль
            }

            // Обработка результата
            if (updateSuccess) {
                request.getSession().setAttribute("changePasswordSuccess", "Пароль успешно обновлен");
                request.getSession().setAttribute("newPassword", newPassword);
                if (author != null) {
                    request.getSession().setAttribute("author", author);
                    response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");
                } else {
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect("Controller?command=GO_TO_USER_ACCOUNT_PAGE");
                }
            } else {
                request.getSession().setAttribute("changePasswordError", "Произошла ошибка при обновлении пароля");
                response.sendRedirect("Controller?command=GO_TO_USER_ACCOUNT_PAGE");
            }
        } else {
            request.getSession().setAttribute("changePasswordError", "Вы не задали новый пароль");
            response.sendRedirect("Controller?command=GO_TO_USER_ACCOUNT_PAGE");
        }
    }
}