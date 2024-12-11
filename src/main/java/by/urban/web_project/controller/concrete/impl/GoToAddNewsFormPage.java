package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.controller.concrete.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.urban.web_project.controller.utils.AuthPresenceUtil.checkAuthPresence;

public class GoToAddNewsFormPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Auth auth = (Auth) request.getSession(false).getAttribute("auth");
        checkAuthPresence(request, response, auth);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/add-news-form-page.jsp");
        dispatcher.forward(request, response);
    }
}
