package by.urban.web_project.controller.concrete.implementation;

import by.urban.web_project.logic.LogicStubForRegistration;
import by.urban.web_project.logic.Check;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import by.urban.web_project.controller.concrete.Command;

public class DoRegistration implements Command {
	private final LogicStubForRegistration logicForRegistration = new LogicStubForRegistration();
	private final Check passwordCheck = new Check();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nameForRegistration = request.getParameter("name");
		String emailForRegistration = request.getParameter("email");
		String passwordForRegistration = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");

		if (!passwordCheck.checkPassword(request, passwordForRegistration, confirmPassword)) {
			request.setAttribute("regError", "Пароли не совпадают");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/RegPage.jsp");
			dispatcher.forward(request, response);
		}

		if (logicForRegistration.checkReg(nameForRegistration, emailForRegistration, passwordForRegistration)) {
			request.setAttribute("regSuccess", nameForRegistration + ", поздравляем Вас с завершением регистрации!");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/AuthPage.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/RegPage.jsp");
			dispatcher.forward(request, response);
		}
	}
}
