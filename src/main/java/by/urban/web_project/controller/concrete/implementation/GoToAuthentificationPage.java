package by.urban.web_project.controller.concrete.implementation;

import by.urban.web_project.controller.concrete.Command;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import by.urban.web_project.utils.SessionUtils;

public class GoToAuthentificationPage implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionUtils.logCurrentUser(request);
		System.out.println("Текущий URL: " + request.getRequestURL().toString());

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/AuthPage.jsp");
		dispatcher.forward(request, response);

	}
}
