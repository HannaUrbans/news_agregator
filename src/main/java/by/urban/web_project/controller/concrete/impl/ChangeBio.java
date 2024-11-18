package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ChangeBio implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String newBio = request.getParameter("newBio");
        System.out.println("Новое био: " + newBio);

        if (newBio != null && !newBio.trim().isEmpty()) {
            request.getSession().setAttribute("newBio", newBio);
            request.getSession().setAttribute("changeBioSuccess", "Биография успешно обновлена");
        } else {
            request.getSession().setAttribute("changeBioError", "Вы не написали ничего в поле");
        }

        response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");

    }
}
