package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GoToChangeForm implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String formType = request.getParameter("formType");
        if (formType == null) {
            response.sendRedirect("Controller?command=NO_SUCH_COMMAND");
        }

        String page = "";
        switch (formType) {
            case "password":
                page = "/WEB-INF/jsp/changePasswordForm.jsp";
                break;
            case "name":
                page = "/WEB-INF/jsp/changeNameForm.jsp";
                break;
            case "bio":
            default:
                page = "/WEB-INF/jsp/changeBioForm.jsp";
                break;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
