package by.urban.web_project.controller.concrete.implementation;

import by.urban.web_project.logic.LogicStubForRegistration;
import by.urban.web_project.logic.Check;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.utils.SessionUtils;

public class DoRegistration implements Command {
	private final LogicStubForRegistration logicForRegistration = new LogicStubForRegistration();
	private final Check passwordCheck = new Check();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nameForRegistration = request.getParameter("name");
		String emailForRegistration = request.getParameter("email");
		String passwordForRegistration = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		SessionUtils.logCurrentUser(request);
		System.out.println("Текущий URL: " + request.getRequestURL().toString());

		if (!passwordCheck.checkPassword(request, passwordForRegistration, confirmPassword)) {
			request.getSession().setAttribute("regError", "Пароли не совпадают");
			response.sendRedirect("Controller?command=GO_TO_REGISTRATION_PAGE");
			return;
		}

		if (logicForRegistration.checkReg(nameForRegistration, emailForRegistration, passwordForRegistration)) {
			request.getSession().setAttribute("regSuccess",
					nameForRegistration + ", поздравляем Вас с завершением регистрации!");
			response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
		} else {
			response.sendRedirect("Controller?command=GO_TO_REGISTRATION_PAGE");
		}

		SessionUtils.logCurrentUser(request);

	}
}
