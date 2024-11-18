package by.urban.web_project.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.controller.concrete.CommandProvider;

@WebServlet("/Controller")
// сервлет может обрабатывать запросы с типом контента multipart/form-data (используется для загрузки файлов через форму)
// такие данные могут быть разделены на части (текст и файлы)
@MultipartConfig
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final CommandProvider provider = new CommandProvider();

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
        try {
            doRequest(request, response);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
        try {
            doRequest(request, response);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

	private void doRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MessagingException {
		String userCommand = request.getParameter("command");
		System.out.println("Received command: " + userCommand);
		Command command = provider.takeCommand(userCommand);
		command.execute(request, response);
	}
}
