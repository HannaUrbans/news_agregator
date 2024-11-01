package by.urban.web_project.controller.concrete.implementation;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import by.urban.web_project.controller.concrete.Command;
import jakarta.servlet.RequestDispatcher;

public class GoToRegistrationPage implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/RegPage.jsp");
		dispatcher.forward(request, response);
	}
}
