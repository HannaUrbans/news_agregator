package by.urban.web_project.controller.concrete.implementation;

import by.urban.web_project.logic.LogicStubForAuthorization;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletException;
import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletResponse;
import by.urban.web_project.controller.concrete.Command;

/**
 * 
 */

public class DoAuth implements Command {
	private final LogicStubForAuthorization logicForAuthorization = new LogicStubForAuthorization();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		if (logicForAuthorization.checkAuth(email, password)) {
			request.setAttribute("authSuccess", "Добро пожаловать в личный кабинет, " + email);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/UserAccountPage.jsp");
			dispatcher.forward(request, response);
		} else {
			request.setAttribute("authError", "Неправильный email или пароль");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/AuthPage.jsp");
			dispatcher.forward(request, response);
		}

	}
}
