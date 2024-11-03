package by.urban.web_project.controller.concrete.implementation;

import by.urban.web_project.logic.LogicStubForAuthorization;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletException;
import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.bean.User;
import by.urban.web_project.bean.UsersDatabase;
import by.urban.web_project.utils.SessionUtils;

/**
 * 
 */

public class DoAuth implements Command {
	private final LogicStubForAuthorization logicForAuthorization = new LogicStubForAuthorization();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		SessionUtils.logCurrentUser(request);

		System.out.println("Авторизация пользователя:" + email);
		SessionUtils.logCurrentUser(request);
		System.out.println("Текущий URL: " + request.getRequestURL().toString());

		if (logicForAuthorization.checkAuth(email, password)) {
			User user = UsersDatabase.getUserByEmail(email);
			request.getSession(true).setAttribute("user", user);
			request.getSession().setAttribute("authSuccess", "Добро пожаловать в личный кабинет, " + user.getName());
			response.sendRedirect("Controller?command=GO_TO_USER_ACCOUNT_PAGE");
			SessionUtils.logCurrentUser(request);
		} else {
			request.getSession().setAttribute("authError", "Неправильный email или пароль");
			response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
			SessionUtils.logCurrentUser(request);
		}

	}
}
