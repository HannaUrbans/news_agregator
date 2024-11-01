package by.urban.web_project.controller.concrete.implementation;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import by.urban.web_project.controller.concrete.Command;

public class ShowStub implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/Stub.jsp");
		dispatcher.forward(request, response);
	}
}
