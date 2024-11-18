package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.model.roles.Author;
import by.urban.web_project.model.roles.User;
import by.urban.web_project.service.IAuthorizationService;
import by.urban.web_project.service.ServiceFactory;
import by.urban.web_project.utils.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 *
 */

public class DoAuth implements Command {
    ServiceFactory serviceFactory = ServiceFactory.getInstance();

    IAuthorizationService logicForAuthorization = serviceFactory.getAuthorizationService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        SessionUtils.logCurrentVisitor(request);

        System.out.println("Авторизация пользователя:" + email);
        SessionUtils.logCurrentVisitor(request);

        Object authorizedObject = logicForAuthorization.checkAuth(email, password);
        if (authorizedObject != null) {
            if (authorizedObject instanceof Author) {
                Author author = (Author) authorizedObject;
                request.getSession(true).setAttribute("author", author);
                request.getSession(true).setAttribute("email", email);
                request.getSession().setAttribute("authSuccess", "Добро пожаловать в личный кабинет, " + author.getName());
                System.out.println("Номер сессии: " + request.getSession(true).getId());
                response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");
            } else if (authorizedObject instanceof User) {
                User user = (User) authorizedObject;
                request.getSession(true).setAttribute("user", user);
                request.getSession(true).setAttribute("email", email);
                request.getSession().setAttribute("authSuccess", "Добро пожаловать в личный кабинет, " + user.getName());
                System.out.println("Номер сессии: " + request.getSession(true).getId());
                response.sendRedirect("Controller?command=GO_TO_USER_ACCOUNT_PAGE");

            }
            SessionUtils.logCurrentVisitor(request);
        } else {
            request.getSession().setAttribute("authError", "Неправильный email или пароль");
            response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
            SessionUtils.logCurrentVisitor(request);

        }
    }
}
