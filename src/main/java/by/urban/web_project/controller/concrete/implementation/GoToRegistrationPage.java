package by.urban.web_project.controller.concrete.implementation;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.utils.SessionUtils;
import jakarta.servlet.RequestDispatcher;

public class GoToRegistrationPage implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionUtils.logCurrentUser(request);
		System.out.println("Текущий URL: " + request.getRequestURL().toString());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/RegPage.jsp");
		dispatcher.forward(request, response);
	}
}
