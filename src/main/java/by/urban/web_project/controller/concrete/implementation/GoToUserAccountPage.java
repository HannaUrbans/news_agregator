package by.urban.web_project.controller.concrete.implementation;

import by.urban.web_project.controller.concrete.Command;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

public class GoToUserAccountPage implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/UserAccountPage.jsp");
		dispatcher.forward(request, response);
	}
}
