package by.urban.web_project.controller.concrete.implementation;

import by.urban.web_project.controller.concrete.Command;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

public class GoToAuthentificationPage implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/AuthPage.jsp");
		dispatcher.forward(request, response);
	}
}
