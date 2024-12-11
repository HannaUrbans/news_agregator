package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.controller.concrete.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.urban.web_project.controller.utils.AuthPresenceUtil.checkAuthPresence;
import static by.urban.web_project.controller.utils.UrlFormatterUtil.formatRedirectUrl;

public class GoToRegistrationPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Auth auth = (Auth) request.getSession(false).getAttribute("auth");

        // Проверка, если уже есть авторизованный пользователь в сессии
        if (request.getSession().getAttribute("auth") != null) {
            request.getSession().setAttribute("regRedirectFail", "Чтобы зарегистрировать нового пользователя, выйдите из личного кабинета");
            response.sendRedirect("Controller?command=" + formatRedirectUrl(auth.getRole()));
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/reg-page.jsp");
        dispatcher.forward(request, response);
    }
}
