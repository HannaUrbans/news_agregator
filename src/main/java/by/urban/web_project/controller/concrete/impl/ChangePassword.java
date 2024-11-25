package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.model.roles.Author;
import by.urban.web_project.model.roles.User;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ChangePassword implements Command {
    private final IChangeProfileService updateTool;

    public ChangePassword() throws ServiceException {
        this.updateTool = ServiceFactory.getInstance().getChangeProfileService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String newPassword = request.getParameter("newPassword");
        User user = null;
        Author author = null;

        if (newPassword != null && !newPassword.trim().isEmpty()) {
            user = (User) request.getSession().getAttribute("user");
            author = (Author) request.getSession().getAttribute("author");

            if (author != null) {
                try {
                    updateTool.updateAuthorPassword(author.getId(), newPassword);
                    author.setPassword(newPassword);
                    request.getSession().setAttribute("author", author);
                    request.getSession().setAttribute("changePasswordSuccess", "Пароль успешно обновлен");
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            } else if (user != null) {
                try {
                    updateTool.updateUserPassword(user.getId(), newPassword);
                    user.setPassword(newPassword);
                    request.getSession().setAttribute("user", user);
                    request.getSession().setAttribute("changePasswordSuccess", "Пароль успешно обновлен");
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            } else {
                request.getSession().setAttribute("changePasswordError", "Вы не задали новый пароль");
            }

            if (author != null) {
                response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");
            } else {
                response.sendRedirect("Controller?command=GO_TO_USER_ACCOUNT_PAGE");
            }

        }
    }
}
