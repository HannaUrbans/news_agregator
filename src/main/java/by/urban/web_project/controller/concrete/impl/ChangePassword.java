package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.model.roles.Author;
import by.urban.web_project.model.roles.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ChangePassword implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String newPassword = request.getParameter("newPassword");
        User user = null;
        Author author = null;
        System.out.println(newPassword);
        if (newPassword != null && !newPassword.trim().isEmpty()) {

            user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                author = (Author) request.getSession().getAttribute("author");
            }
            System.out.println("ID текущей сессии со страницы ChangePassword: " + request.getSession().getId());
            if (user != null) {
                user.setPassword(newPassword);
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("changePasswordSuccess", "Пароль успешно обновлен");
            }
            else if (author != null) {
                author.setPassword(newPassword);
                request.getSession().setAttribute("author", author);  // Сохраняем обновленного автора в сессию
                request.getSession().setAttribute("changePasswordSuccess", "Пароль успешно обновлен");
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
