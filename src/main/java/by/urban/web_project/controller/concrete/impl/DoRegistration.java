package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.service.ICheckService;
import by.urban.web_project.service.IRegistrationService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import by.urban.web_project.utils.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DoRegistration implements Command {
    ServiceFactory serviceFactory = ServiceFactory.getInstance();

    IRegistrationService logicForRegistration = serviceFactory.getRegistrationService();
    ICheckService check = serviceFactory.getCheckService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nameForRegistration = request.getParameter("name");
            String emailForRegistration = request.getParameter("email");
            String authorKey = request.getParameter("authorKey");
            String passwordForRegistration = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            SessionUtils.logCurrentVisitor(request);

            if (logicForRegistration.checkIfEmailExistsInDB(request, emailForRegistration)) {
                request.getSession().setAttribute("emailDuplicate", "Пользователь с таким e-mail уже существует");
                response.sendRedirect("Controller?command=GO_TO_REGISTRATION_PAGE");
                return;
            }

            if (!check.checkPassword(request, passwordForRegistration, confirmPassword)) {
                request.getSession().setAttribute("regError", "Пароли не совпадают");
                response.sendRedirect("Controller?command=GO_TO_REGISTRATION_PAGE");
                return;
            }

            if (authorKey != null && !authorKey.trim().isEmpty()) {
                if (!check.checkAuthorKey(request, authorKey)) {
                    request.getSession().setAttribute("invalidAuthorKey", "Вы ввели неверный ключ автора");
                    response.sendRedirect("Controller?command=GO_TO_REGISTRATION_PAGE");
                    return;
                }
            }

            if (logicForRegistration.checkReg(nameForRegistration, emailForRegistration, passwordForRegistration)) {
                request.getSession().setAttribute("regSuccess", nameForRegistration + ", поздравляем Вас с завершением регистрации!");
                response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
            } else {
                response.sendRedirect("Controller?command=GO_TO_REGISTRATION_PAGE");
            }

            SessionUtils.logCurrentVisitor(request);

        } catch (ServiceException e) {
            e.printStackTrace();
            request.getSession().setAttribute("regError", "Произошла ошибка при регистрации. Попробуйте позже.");
            response.sendRedirect("Controller?command=GO_TO_REGISTRATION_PAGE");
        }
    }
}