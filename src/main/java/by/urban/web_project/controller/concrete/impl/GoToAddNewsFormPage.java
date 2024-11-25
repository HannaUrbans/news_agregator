package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GoToAddNewsFormPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/add-news-form-page.jsp");
        dispatcher.forward(request, response);
    }
}
