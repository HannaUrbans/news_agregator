package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.utils.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LogOut implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionUtils.logCurrentVisitor(request);

        // Проверяем, существует ли сессия
        if (request.getSession(false) != null &&
                (request.getSession(false).getAttribute("user") != null || request.getSession(false).getAttribute("author") != null)) {
            request.getSession().invalidate();
            request.getSession().setAttribute("logoutSuccess", "Вы успешно вышли из системы");
        } else {
            request.getSession().setAttribute("logoutFail", "Вы не были зарегистрированы в системе");
        }

        response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
    }
}
