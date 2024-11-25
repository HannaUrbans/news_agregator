package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GoToAuthentificationPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Проверка, если уже есть авторизованный пользователь
        if (request.getSession().getAttribute("author") != null || request.getSession().getAttribute("user") != null) {
            if (request.getSession().getAttribute("author") != null) {
                response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");
            } else {
                response.sendRedirect("Controller?command=GO_TO_USER_ACCOUNT_PAGE");
            }
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/AuthPage.jsp");
        dispatcher.forward(request, response);

    }
}
