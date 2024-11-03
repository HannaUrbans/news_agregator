package by.urban.web_project.controller.concrete.implementation;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.utils.SessionUtils;

public class LogOut implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionUtils.logCurrentUser(request);
		System.out.println("Текущий URL: " + request.getRequestURL().toString());
		
		request.getSession().invalidate();
		request.getSession().setAttribute("logoutSuccess", "Вы успешно вышли из системы");
		response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");

	}
}
