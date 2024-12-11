package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.bean.UserRole;
import by.urban.web_project.controller.concrete.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.urban.web_project.controller.utils.AuthPresenceUtil.checkAuthPresence;
import static by.urban.web_project.controller.utils.RolePresenceUtil.checkRolePresence;
import static by.urban.web_project.controller.utils.UrlFormatterUtil.formatRedirectUrl;

public class GoToAdminAccountPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Auth auth = (Auth) request.getSession(false).getAttribute("auth");
        // если не в сессии
        checkAuthPresence(request, response, auth);
        // если от другой роли
        checkRolePresence(request, response, UserRole.ADMIN);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/account-pages/admin-account-page.jsp");
        dispatcher.forward(request, response);
    }
}