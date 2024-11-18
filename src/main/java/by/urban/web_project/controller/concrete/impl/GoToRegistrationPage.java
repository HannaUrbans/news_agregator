package by.urban.web_project.controller.concrete.impl;

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
		// Проверка, если уже есть авторизованный пользователь
		if (request.getSession().getAttribute("author") != null || request.getSession().getAttribute("user") != null) {
			if (request.getSession().getAttribute("author") != null) {
				request.getSession().setAttribute("regRedirectFail", "Чтобы зарегистрировать нового пользователя, выйдите из личного кабинета");
				response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");
			} else {
				response.sendRedirect("Controller?command=GO_TO_USER_ACCOUNT_PAGE");
			}
			return;
		}

		SessionUtils.logCurrentVisitor(request);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/RegPage.jsp");
		dispatcher.forward(request, response);
	}
}
