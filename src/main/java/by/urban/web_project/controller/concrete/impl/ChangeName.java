package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.model.roles.Author;
import by.urban.web_project.model.roles.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ChangeName implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String newName = request.getParameter("newName");
        User user = null;
        Author author = null;
        System.out.println("Новое имя: " + newName);
        if (newName != null && !newName.trim().isEmpty()) {

            user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                author = (Author) request.getSession().getAttribute("author");
            }
            System.out.println("ID текущей сессии со страницы ChangeName: " + request.getSession().getId());
            if (user != null) {
                user.setName(newName);
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("changeNameSuccess", "Имя успешно обновлено");
            } else if (author != null) {
                author.setName(newName);
                request.getSession().setAttribute("author", author);
                request.getSession().setAttribute("changeNameSuccess", "Имя успешно обновлено");
            }
        } else {
            request.getSession().setAttribute("changeNameError", "Вы не задали новое имя");
        }

        if (author != null) {
            response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");
        } else {
            response.sendRedirect("Controller?command=GO_TO_USER_ACCOUNT_PAGE");
        }
    }
}
