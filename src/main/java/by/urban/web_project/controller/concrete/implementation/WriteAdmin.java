package by.urban.web_project.controller.concrete.implementation;

import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.utils.EmailSending;

import java.io.IOException;


public class WriteAdmin implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String email = request.getParameter("email");
		String message = request.getParameter("message");
		// именно Part, потому что содержимое НЕ строка
		Part inputFile = request.getPart("inputFile");

		try {
			EmailSending.sendEmail(request.getServletContext(), email, message, inputFile);
			// устанавливаем оповещение об успехе
			request.setAttribute("successMessage", "Сообщение успешно отправлено.");
		} catch (Exception e) {
			// устанавливаем оповещение об ошибке
			request.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
		} finally {
			//тут нужно не на index, а на команду контроллера, тогда сессии не нужны
			RequestDispatcher dispatcher = request.getRequestDispatcher("Controller?command=GO_TO_INDEX_PAGE");
			dispatcher.forward(request, response);
		}
	}

	
}