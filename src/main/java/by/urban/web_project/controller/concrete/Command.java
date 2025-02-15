package by.urban.web_project.controller.concrete;

import java.io.IOException;

import by.urban.web_project.service.ServiceException;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Command {
	void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
