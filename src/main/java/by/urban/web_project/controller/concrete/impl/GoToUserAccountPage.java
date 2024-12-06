package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.bean.UserRole;
import by.urban.web_project.controller.concrete.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.urban.web_project.controller.utils.UrlFormatterUtil.formatRedirectUrl;

public class GoToUserAccountPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Auth auth = (Auth) request.getSession(false).getAttribute("auth");
        // если не в сессии
        if (auth == null) {
            System.out.println("Пользователь не залогинен и пытается открыть страницу личного кабинета");
            request.getSession().setAttribute("authError", "У Вас недостаточно прав для посещения этой страницы");
            response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
            return;
        }

        // если от другой роли
        if (!UserRole.USER.equals(auth.getRole())) {
            System.out.println("Пользователь пытается войти в личный кабинет не своей роли");
            request.getSession().setAttribute("authError", "У Вас недостаточно прав для посещения этой страницы");
            response.sendRedirect("Controller?command=" + formatRedirectUrl(auth.getRole()));
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/account-pages/user-account-page.jsp");
        dispatcher.forward(request, response);
    }
}