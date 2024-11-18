package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.utils.SessionUtils;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

public class GoToUserAccountPage implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("user") == null) {
			response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
			return;
		}

		SessionUtils.logCurrentVisitor(request);
		System.out.println("Текущий URL: " + request.getRequestURL().toString());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/UserAccountPage.jsp");
		dispatcher.forward(request, response);
	}
}
